package com.hiskasoft.maven.process;

/**
 *
 * @author Willyams Yujra
 */
public interface Process {

    String getName();

    int getOrder();

    void execute() throws Exception;
}
