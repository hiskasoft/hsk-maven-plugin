package com.hiskasoft.maven.process;

/**
 *
 * @author Willyams Yujra
 */
public interface Logger {

    void info(String msg);

    void warn(String msg);

    void error(String msg);

    void error(Throwable ex);

    void error(String msg, Throwable ex);

    void error(Process e, Throwable ex);

    void debug(String msg);

    void debug(Throwable ex);

    void start(Process e);

    void end(Process e);
}
