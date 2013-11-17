package org.adrianwalker.collectionliterals;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes({"org.adrianwalker.collectionliterals.HashMap"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public final class HashMapProcessor extends AbstractCollectionProcessor<HashMap<String, Object>> {

  @Override
  public List processCollection(final HashMap<String, Object> collection, final JCTree.JCExpression collectionMethodEx) {
    ListBuffer collectionMethodBuffer = new ListBuffer();

    for (Entry<String, Object> entry : collection.entrySet()) {

      ListBuffer putBuffer = new ListBuffer();
      putBuffer.add(getMaker().Literal(entry.getKey()));
      putBuffer.add(getMaker().Literal(entry.getValue()));

      JCMethodInvocation putInv = getMaker().Apply(
              List.<JCTree.JCExpression>nil(),
              collectionMethodEx,
              putBuffer.toList());
      JCTree.JCStatement putStatement = getMaker().Exec(putInv);

      collectionMethodBuffer.add(putStatement);
    }

    return collectionMethodBuffer.toList();
  }

  @Override
  public Class getAnnotationClass() {
    return org.adrianwalker.collectionliterals.HashMap.class;
  }

  @Override
  public Class getCollectionClass() {
    return java.util.HashMap.class;
  }

  @Override
  public String getCollectionClassName() {
    return getCollectionClass().getName();
  }

  @Override
  public String getCollectionMethodName() {
    return "put";
  }
}
