package org.adrianwalker.collectionliterals;

import java.util.LinkedList;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedAnnotationTypes({"org.adrianwalker.collectionliterals.LinkedList"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public final class LinkedListProcessor extends AbstractCollectionProcessor<LinkedList<Object>> {

  @Override
  public Class getAnnotationClass() {
    return org.adrianwalker.collectionliterals.LinkedList.class;
  }

  @Override
  public Class getCollectionClass() {
    return java.util.LinkedList.class;
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
