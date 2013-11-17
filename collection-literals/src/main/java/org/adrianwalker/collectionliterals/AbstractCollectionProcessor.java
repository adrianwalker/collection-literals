package org.adrianwalker.collectionliterals;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class AbstractCollectionProcessor<C> extends AbstractProcessor {

  private static final String THIS = "this";
  private JavacElements elementUtils;
  private TreeMaker maker;
  private Trees trees;
  private Names names;

  @Override
  public void init(final ProcessingEnvironment procEnv) {
    super.init(procEnv);
    JavacProcessingEnvironment javacProcessingEnv = (JavacProcessingEnvironment) procEnv;
    Context context = javacProcessingEnv.getContext();
    this.elementUtils = javacProcessingEnv.getElementUtils();
    this.maker = TreeMaker.instance(context);
    this.trees = Trees.instance(procEnv);
    this.names = Names.instance(context);
  }

  public JavacElements getElementUtils() {
    return elementUtils;
  }

  public TreeMaker getMaker() {
    return maker;
  }

  public Trees getTrees() {
    return trees;
  }

  public Names getNames() {
    return names;
  }

  @Override
  public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

    Set<? extends Element> fields = roundEnv.getElementsAnnotatedWith(getAnnotationClass());

    for (Element field : fields) {
      String docComment = elementUtils.getDocComment(field);

      if (null == docComment) {
        continue;
      }

      processField(field, parseJson(docComment));
    }

    return true;
  }

  private C parseJson(final String json) throws ProcessorException {

    ObjectMapper mapper = new ObjectMapper();

    C collection;
    try {
      collection = (C) mapper.readValue(json, getCollectionClass());
    } catch (final Throwable t) {
      throw new ProcessorException(t);
    }

    return collection;
  }

  private void processField(final Element field, final C collection) {

    JCTree fieldTree = elementUtils.getTree(field);
    JCTree.JCVariableDecl fieldVariable = (JCTree.JCVariableDecl) fieldTree;
    Symbol.VarSymbol fieldSym = fieldVariable.sym;
    boolean isStatic = fieldSym.isStatic();
    JCTree.JCExpression fieldEx;

    if (!isStatic) {
      fieldEx = maker.Ident(names.fromString(THIS));
      fieldEx = maker.Select(fieldEx, fieldSym.name);
    } else {
      fieldEx = maker.QualIdent(fieldSym);
    }

    Symbol.ClassSymbol collectionClass = elementUtils.getTypeElement(getCollectionClassName());
    JCTree.JCExpression collectionEx = maker.QualIdent(collectionClass);

    JCTree.JCNewClass newCollection = maker.NewClass(
            null,
            List.<JCTree.JCExpression>nil(),
            collectionEx,
            List.<JCTree.JCExpression>nil(), null);

    JCTree.JCAssign assign = maker.Assign(fieldEx, newCollection);
    JCTree.JCStatement assignStatement = maker.Exec(assign);

    JCExpression collectionMethodEx = maker.Select(fieldEx, names.fromString(getCollectionMethodName()));

    ListBuffer blockBuffer = new ListBuffer();
    blockBuffer.add(assignStatement);
    blockBuffer.appendList(processCollection(collection, collectionMethodEx));

    long flags = Flags.BLOCK;
    if (isStatic) {
      flags |= Flags.STATIC;
    }

    JCTree.JCBlock block = maker.Block(flags, blockBuffer.toList());

    TypeElement type = (TypeElement) field.getEnclosingElement();
    ClassTree cls = trees.getTree(type);

    JCTree.JCClassDecl cd = (JCTree.JCClassDecl) cls;

    cd.defs = cd.defs.append(block);
  }

  public List processCollection(final C collection, final JCTree.JCExpression collectionMethodEx) {
    ListBuffer collectionMethodBuffer = new ListBuffer();

    for (Object element : (Collection) collection) {

      ListBuffer addBuffer = new ListBuffer();
      addBuffer.add(getMaker().Literal(element));

      JCMethodInvocation addInv = getMaker().Apply(
              List.<JCTree.JCExpression>nil(),
              collectionMethodEx,
              addBuffer.toList());
      JCTree.JCStatement addStatement = getMaker().Exec(addInv);

      collectionMethodBuffer.add(addStatement);
    }

    return collectionMethodBuffer.toList();
  }

  public abstract Class getAnnotationClass();

  public abstract String getCollectionClassName();

  public abstract Class getCollectionClass();

  public abstract String getCollectionMethodName();
}
