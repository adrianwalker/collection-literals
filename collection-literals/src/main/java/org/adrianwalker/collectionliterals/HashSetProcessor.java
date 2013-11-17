package org.adrianwalker.collectionliterals;

import java.util.HashSet;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes({"org.adrianwalker.collectionliterals.HashSet"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public final class HashSetProcessor extends AbstractCollectionProcessor<HashSet<Object>> {

  @Override
  public Class getAnnotationClass() {
    return org.adrianwalker.collectionliterals.HashSet.class;
  }

  @Override
  public Class getCollectionClass() {
    return java.util.HashSet.class;
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
