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

public class FormatXmlProcess extends AbstractProcess {

    private static final String GROUP_ID = "au.com.acegi";
    private static final String ARTIFACT_ID = "xml-format-maven-plugin";
    private static final String GOAL = "xml-format";

    public FormatXmlProcess(boolean skip, Config config, MavenContext element, Logger logger) {
        super("FORMAT XML", skip, config, element, logger);
    }

    @Override
    public boolean isSkip() {
        return super.isSkip() || config.isSkipXml();
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
                configuration(
                        element(name("lineEnding"), config.getLineEnding()),
                        element(name("encoding"), config.getEncoding()),
                        element(name("indentSize"), "4"),
                        element(name("includes"),
                                element(name("include"), "**/*.xml"),
                                element(name("include"), "**/*.xsd"),
                                element(name("include"), "**/*.xsl"),
                                element(name("include"), "**/*.xslt"),
                                element(name("include"), "**/*.jpa"),
                                element(name("include"), "**/*.jspx"))),
                executionEnvironment());
    }
}
