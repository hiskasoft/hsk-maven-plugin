/*
 * Copyright 2020 yracnet.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiskasoft.maven.plugin;

import com.hiskasoft.maven.process.Logger;
import com.hiskasoft.maven.process.Process;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author yracnet
 */
public class MavenLogger implements Logger {

    private final Log log;
    private final String prefix = "|   ";
    private final String top = "+---";

    public MavenLogger(Log log) {
        this.log = log;
    }

    public MavenLogger(AbstractMojo mojo) {
        this.log = mojo.getLog();
    }

    @Override
    public void info(String msg) {
        log.info(prefix + msg);
    }

    @Override
    public void warn(String msg) {
        log.warn(prefix + msg);
    }

    @Override
    public void error(String msg) {
        log.error(prefix + msg);
    }

    @Override
    public void error(Throwable e) {
        log.error(e);
    }

    @Override
    public void error(String msg, Throwable e) {
        log.error(prefix + msg);
        log.error(e);
    }

    @Override
    public void debug(String msg) {
        log.debug(prefix + msg);
    }

    @Override
    public void debug(Throwable e) {
        log.debug(e);
    }

    @Override
    public void start(Process e) {
        log.info(top + "START " + e.getName() + "[" + e.getOrder() + "]");
    }

    @Override
    public void end(Process e) {
        log.info(top + "END " + e.getName());
    }

    @Override
    public void error(Process e, Throwable ex) {
        log.warn(prefix + "ERROR " + e.getName() + " Cause: " + ex.getMessage());
        log.warn(ex);
    }

}
