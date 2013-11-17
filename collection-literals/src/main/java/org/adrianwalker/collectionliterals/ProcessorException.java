package org.adrianwalker.collectionliterals;

/**
 *
 * @author adwalker
 */
public class ProcessorException extends RuntimeException {

  public ProcessorException() {
    super();
  }

  public ProcessorException(String message) {
    super(message);
  }

  public ProcessorException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProcessorException(Throwable cause) {
    super(cause);
  }
}
