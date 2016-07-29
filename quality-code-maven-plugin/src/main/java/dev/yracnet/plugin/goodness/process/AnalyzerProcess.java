package dev.yracnet.plugin.goodness.process;

import dev.yracnet.plugin.goodness.ProcessContext;
import dev.yracnet.plugin.goodness.ProcessPlugin;
import java.io.File;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;

public class AnalyzerProcess extends ProcessPlugin {

	private static final String GROUP_ID = "org.apache.maven.plugins";
	private static final String ARTIFACT_ID = "maven-pmd-plugin";

	private static final String GOALS[] = {"pmd", "cpd", "check", "cpd-check"};
	private static final String MINIMUM_TOKENS = "150";
	private static final String SOURCE_ENCODING = "UTF-8";
	private static final String JAVA_VERSION = "1.8";
	private static final String EXCLUDE_CONFIG = "exclude.properties";

	public AnalyzerProcess(boolean skip, ProcessContext context) {
		super("ANALYZER", skip, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		header();
		Plugin analyzerPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(analyzerPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
		File file = getMavenProjectFile(EXCLUDE_CONFIG);
		for (String goal : GOALS) {
			getLog().debug("->ANALYZER GOAL: " + goal);
			Xpp3Dom configuration = configuration();
			if (!goal.contains("check")) {
				configuration.addChild(element(name("linkXRef"), "false").toDom());
				configuration.addChild(element(name("sourceEncoding"), SOURCE_ENCODING).toDom());
			} else if ("pmd".equals(goal)) {
				configuration.addChild(element(name("targetJdk"), JAVA_VERSION).toDom());
			} else if ("cpd".equals(goal)) {
				configuration.addChild(element(name("minimumTokens"), MINIMUM_TOKENS).toDom());
			}
			if (file.exists() && file.isFile()) {
				getLog().info("->Exclude file: " + file.getAbsolutePath());
				configuration.addChild(element(name("excludeFromFailureFile"), file.getAbsolutePath()).toDom());
			}
			executeMojo(
											analyzerPlugin,
											goal(goal),
											configuration,
											currentExecutionEnvironment()
			);
		}
		footer();
	}

}
