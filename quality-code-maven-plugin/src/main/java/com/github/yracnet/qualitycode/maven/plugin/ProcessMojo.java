package com.github.yracnet.qualitycode.maven.plugin;

import com.github.yracnet.qualitycode.maven.plugin.process.AnalyzerProcess;
import com.github.yracnet.qualitycode.maven.plugin.process.FormatProcess;
import com.github.yracnet.qualitycode.maven.plugin.process.LicenseProcess;
import java.util.Date;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "process", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ProcessMojo extends AbstractMojo {

	@Component
	private MavenProject mavenProject;

	@Component
	private MavenSession mavenSession;

	@Component
	private BuildPluginManager pluginManager;

	@Parameter(defaultValue = "false")
	private boolean skipFormat;
	@Parameter(defaultValue = "true")
	private boolean createFormat;
	@Parameter(defaultValue = "false")
	private boolean skipLicence;
	@Parameter(defaultValue = "true")
	private boolean createLicence;
	@Parameter(defaultValue = "false")
	private boolean skipAnalyzer;

	public void space() {
		getLog().info("========================================================================");
	}

	@Override
	public void execute() throws MojoExecutionException {
		ProcessContext context = new ProcessContext(this, mavenProject, mavenSession, pluginManager);
		if (context.isPOM() == true) {
			return;
		}
		getLog().info("PROCESS PLUGIN AT " + new Date());
		space();
		ProcessPlugin processPlugin[] = new ProcessPlugin[]{
			new FormatProcess(skipFormat, createFormat, context),
			new LicenseProcess(skipLicence, createLicence, context),
			new AnalyzerProcess(skipAnalyzer, false, context)
		};
		for (ProcessPlugin process : processPlugin) {
			if (process.isSkip() == false) {
				process.execute();
			} else {
				process.header();
			}
		}
		space();
	}
}
