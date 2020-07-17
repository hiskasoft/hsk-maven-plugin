/**
 * Projecto BASE:
 * https://github.com/yracnet/qualitycode-maven-plugin
 *
 */
package com.hiskasoft.maven.internal;

import com.hiskasoft.maven.plugin.MavenContext;
import com.hiskasoft.maven.plugin.AbstractProcess;
import static org.dom4j.io.OutputFormat.createPrettyPrint;
import org.apache.maven.model.Plugin;
import org.dom4j.io.OutputFormat;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;
import com.hiskasoft.maven.plugin.Config;
import com.hiskasoft.maven.process.Logger;

public class FormatPomProcess extends AbstractProcess {

    private static final String GROUP_ID = "org.codehaus.mojo";
    private static final String ARTIFACT_ID = "tidy-maven-plugin";
    private static final String GOAL = "pom";

    public FormatPomProcess(boolean skip, Config config, MavenContext element, Logger logger) {
        super("FORMAT POM", skip, config, element, logger);
    }

    @Override
    public boolean isSkip() {
        return super.isSkip() || config.isSkipPom();
    }

    @Override
    public void executeInternal() throws Exception {
        OutputFormat fmt = createPrettyPrint();
        fmt.setXHTML(true);
        Plugin formatterPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
        assertPlugin(formatterPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
        executeMojo(
                formatterPlugin,
                goal(GOAL),
                configuration(),
                executionEnvironment());
    }
}
