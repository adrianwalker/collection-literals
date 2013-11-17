package org.adrianwalker.collectionliterals;

import java.util.ArrayList;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes({"org.adrianwalker.collectionliterals.ArrayList"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public final class ArrayListProcessor extends AbstractCollectionProcessor<ArrayList<Object>> {

  @Override
  public Class getAnnotationClass() {
    return org.adrianwalker.collectionliterals.ArrayList.class;
  }

  @Override
  public Class getCollectionClass() {
    return java.util.ArrayList.class;
  }

  @Override
  public String getCollectionClassName() {
    return getCollectionClass().getName();
  }

  @Override
  public String getCollectionMethodName() {
    return "add";
  }
}
