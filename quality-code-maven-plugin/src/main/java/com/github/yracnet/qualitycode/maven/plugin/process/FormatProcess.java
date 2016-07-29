package com.github.yracnet.qualitycode.maven.plugin.process;

import com.github.yracnet.qualitycode.maven.plugin.ProcessContext;
import com.github.yracnet.qualitycode.maven.plugin.ProcessPlugin;
import java.io.File;
import java.net.URL;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;

public class FormatProcess extends ProcessPlugin {

	private static final String GROUP_ID = "net.revelc.code";
	private static final String ARTIFACT_ID = "formatter-maven-plugin";
	private static final String GOAL = "format";
	private static final String SOURCE_ENCODING = "UTF-8";
	private static final String XML_CONFIG = "formatter.xml";

	public FormatProcess(boolean skip, ProcessContext context) {
		super("FORMAT", skip, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		header();
		Plugin formatterPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(formatterPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
		String configFile = null;
		File file = getMavenProjectFile(XML_CONFIG);
		if (file.exists() && file.isFile()) {
			configFile = file.getAbsolutePath();
		} else {
			URL url = getComponentResource(XML_CONFIG);
			configFile = url.toExternalForm();
		}
		executeMojo(
										formatterPlugin,
										goal(GOAL),
										configuration(
																		element(name("lineEnding"), "CRLF"),
																		element(name("encoding"), "UTF-8"),
																		element(name("configFile"), configFile)
										),
										currentExecutionEnvironment()
		);
		footer();
	}
}
