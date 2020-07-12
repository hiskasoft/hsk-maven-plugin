package com.hiskasoft.maven.internal;

import com.hiskasoft.maven.plugin.AbstractProcess;
import java.io.File;
import org.apache.maven.model.Plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;
import com.hiskasoft.maven.plugin.Config;
import com.hiskasoft.maven.plugin.MavenContext;
import com.hiskasoft.maven.process.Logger;

public class FormatJavaProcess extends AbstractProcess {
    private static final String GROUP_ID = "net.revelc.code.formatter";
    private static final String ARTIFACT_ID = "formatter-maven-plugin";
    private static final String GOAL = "format";
    private static final String XML_CONFIG = "config/formatter.xml";
    

    public FormatJavaProcess(boolean skip, Config config, MavenContext element, Logger logger) {
        super("FORMAT JAVA", skip, config, element, logger);
    }

    @Override
    public void executeInternal() throws Exception {
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
                        element(name("lineEnding"),  config.getLineEnding()),
                        element(name("encoding"), config.getEncoding()),
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
