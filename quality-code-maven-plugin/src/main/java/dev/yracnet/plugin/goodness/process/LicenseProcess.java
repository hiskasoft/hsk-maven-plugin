package dev.yracnet.plugin.goodness.process;

import dev.yracnet.plugin.goodness.ProcessContext;
import dev.yracnet.plugin.goodness.ProcessPlugin;
import java.net.URL;
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
	private static final String LICENCE_TEXT = "/META-INF/licence.txt";

	public LicenseProcess(boolean skip, ProcessContext context) {
		super("LICENSE", skip, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		header();
		Plugin licensePlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(licensePlugin, GROUP_ID, ARTIFACT_ID, "<pluginManagement>");
		URL url = getComponentResource(LICENCE_TEXT);
		assertLocalResource(url, LICENCE_TEXT);
		executeMojo(
										licensePlugin,
										goal(GOAL),
										configuration(
																		element(name("header"), url.toExternalForm()),
																		element(name("strictCheck"), "true"),
																		element(name("includes"),
																										element(name("include"), "src/main/java/**/*.java"),
																										element(name("include"), "src/main/webapp/*.html"),
																										element(name("include"), "src/main/webapp/view/*.html"),
																										element(name("include"), "src/main/webapp/ctrl/**/*.js")
																		)
										),
										currentExecutionEnvironment()
		);
		footer();
	}

}
