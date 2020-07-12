/**
 * Copyright © ${project.inceptionYear} ${owner} (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiskasoft.maven.process;

import com.hiskasoft.maven.plugin.ProcessContext;
import com.hiskasoft.maven.plugin.ProcessPlugin;
import java.io.File;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public class FormatProcess extends ProcessPlugin {
    private static final String GROUP_ID = "net.revelc.code.formatter";
    private static final String ARTIFACT_ID = "formatter-maven-plugin";
    private static final String GOAL = "format";
    private static final String ENCODING = "UTF-8";
    private static final String ENDLINE = "CRLF";
    private static final String XML_CONFIG = "config/formatter.xml";

    public FormatProcess(boolean skip, boolean create, ProcessContext context) {
        super("FORMAT", skip, create, context);
    }

    @Override
    public void execute() throws MojoExecutionException {
        Plugin formatterPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
        assertPlugin(formatterPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
        String configFile;
        File file = getMavenProjectFile(XML_CONFIG);
        if (file.exists() && file.isFile()) {
            configFile = file.getAbsolutePath();
        } else {
            configFile = processDefaultConfig(XML_CONFIG);
        }
        executeMojo(formatterPlugin, //
                goal(GOAL),//
                configuration(//
                        element(name("lineEnding"), ENDLINE),//
                        element(name("encoding"), ENCODING),//
                        element(name("configFile"), configFile)//
                ),//
                executionEnvironment()
        );
    }
}
