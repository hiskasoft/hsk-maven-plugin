package com.github.yracnet.qualitycode.maven.plugin.process;

import com.github.yracnet.qualitycode.maven.plugin.ProcessContext;
import com.github.yracnet.qualitycode.maven.plugin.ProcessPlugin;
import java.io.File;
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
	private static final String ENCODING = "UTF-8";
	private static final String ENDLINE = "CRLF";
	private static final String XML_CONFIG = "config/formatter.xml";

	public FormatProcess(boolean skip, boolean create, ProcessContext context) {
		super("Format", skip, create, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		header();
		Plugin formatterPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(formatterPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
		String configFile;
		File file = getMavenProjectFile(XML_CONFIG);
		if (file.exists() && file.isFile()) {
			configFile = file.getAbsolutePath();
		} else {
			configFile = processDefaultConfig(XML_CONFIG, true);
		}
		executeMojo(
										formatterPlugin,
										goal(GOAL),
										configuration(
																		element(name("lineEnding"), ENDLINE),
																		element(name("encoding"), ENCODING),
																		element(name("configFile"), configFile)
										),
										currentExecutionEnvironment()
		);
		footer();
	}
}
