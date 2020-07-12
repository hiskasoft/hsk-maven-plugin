/**
 * Projecto BASE:
 * https://github.com/yracnet/qualitycode-maven-plugin
 *
 */
package com.hiskasoft.maven.process;

import com.hiskasoft.maven.plugin.Config;
import com.hiskasoft.maven.plugin.Context;
import com.hiskasoft.maven.plugin.Process;
import static org.dom4j.io.OutputFormat.createPrettyPrint;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.dom4j.io.OutputFormat;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public class FormatXmlProcess extends Process {

    private static final String GROUP_ID = "au.com.acegi";
    private static final String ARTIFACT_ID = "xml-format-maven-plugin";
    private static final String GOAL = "xml-format";

    public FormatXmlProcess(boolean skip, Context context, Config config) {
        super("FORMAT XML", skip, context, config);
    }

    @Override
    public boolean isSkip() {
        return super.isSkip() || config.isSkipXml();
    }

    @Override
    public void execute() throws MojoExecutionException {
        
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
