package org.cibseven.bpm.extension.reactor.bus;


import org.cibseven.bpm.engine.ProcessEngineException;
import org.cibseven.bpm.extension.reactor.projectreactor.support.ErrorHandler;

/**
 * Singleton instance of errorHandler that applies when execution of listeners fails.
 */
public enum UncaughtErrorHandler implements ErrorHandler {
  INSTANCE;

  @Override
  public void accept(final Throwable throwable) {
    if (throwable instanceof ProcessEngineException) {
      throw (ProcessEngineException) throwable;
    } else if (throwable instanceof RuntimeException) {
      throw (RuntimeException) throwable;
    }
    THROW_RUNTIME_EXCEPTION.accept(throwable);
  }
}
