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

public class LicenseProcess extends ProcessPlugin {

	private static final String GROUP_ID = "com.mycila";
	private static final String ARTIFACT_ID = "license-maven-plugin";
	private static final String GOAL = "format";
	private static final String LICENCE_TEXT = "config/licence.txt";

	public LicenseProcess(boolean skip, boolean create, ProcessContext context) {
		super("LICENSE", skip, create, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		header();
		Plugin licensePlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(licensePlugin, GROUP_ID, ARTIFACT_ID, "<pluginManagement>");
		String licenceFile;
		File file = getMavenProjectFile(LICENCE_TEXT);
		if (file.exists() && file.isFile()) {
			licenceFile = file.getAbsolutePath();
		} else {
			licenceFile = processDefaultConfig(LICENCE_TEXT, true);
		}
		executeMojo(
										licensePlugin,
										goal(GOAL),
										configuration(
																		element(name("header"), licenceFile),
																		element(name("strictCheck"), "false"),
																		element(name("includes"),
																										element(name("include"), "src/main/java/**/*.java"),
																										element(name("include"), "src/main/webapp/*.html"),
																										element(name("include"), "src/main/webapp/view/*.html"),
																										element(name("include"), "src/main/webapp/ctrl/**/*.js")
																		),
																		element(name("excludes"),
																										element(name("exclude"), "src/main/webapp/part/**/*.*"),
																										element(name("exclude"), "src/main/webapp/include/**/*.*"),
																										element(name("exclude"), "src/main/webapp/fragment/**/*.*")
																		)
										),
										currentExecutionEnvironment()
		);
		footer();
	}

}
