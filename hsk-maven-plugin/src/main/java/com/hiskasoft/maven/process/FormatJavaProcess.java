package com.hiskasoft.maven.process;

import com.hiskasoft.maven.plugin.ProcessContext;
import com.hiskasoft.maven.plugin.ProcessPlugin;
import java.io.File;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public class FormatJavaProcess extends ProcessPlugin {
    private static final String GROUP_ID = "net.revelc.code.formatter";
    private static final String ARTIFACT_ID = "formatter-maven-plugin";
    private static final String GOAL = "format";
    private static final String ENCODING = "UTF-8";
    private static final String ENDLINE = "CRLF";
    private static final String XML_CONFIG = "config/formatter.xml";

    public FormatJavaProcess(boolean skip, ProcessContext context) {
        super("FORMAT JAVA", skip, context);
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
        executeMojo(formatterPlugin,
                goal(GOAL),
                configuration(
                        element(name("lineEnding"), ENDLINE),
                        element(name("encoding"), ENCODING),
                        element(name("configFile"), configFile),
                        element(name("directories"),
                                element(name("directory"), "${basedir}/src/main/java"),
                                element(name("directory"), "${basedir}/src/test/java")
                        )
                ),
                executionEnvironment()
        );
    }
}
