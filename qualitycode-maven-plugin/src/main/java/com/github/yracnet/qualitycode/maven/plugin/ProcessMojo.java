/**
 * Copyright © ${project.inceptionYear} ${owner} (${email})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yracnet.qualitycode.maven.plugin;

import com.github.yracnet.qualitycode.maven.plugin.process.AnalyzerProcess;
import com.github.yracnet.qualitycode.maven.plugin.process.FormatProcess;
import com.github.yracnet.qualitycode.maven.plugin.process.LicenseProcess;
import java.util.Date;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

@Mojo(name = "process", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ProcessMojo extends AbstractMojo {
	@Component
	private MavenProject project;
	@Component
	private MavenSession session;
	@Component
	private BuildPluginManager pluginManager;
	@Component
	private MojoExecution execution;
	@Parameter(defaultValue = "false")
	private boolean skipFormat;
	@Parameter(defaultValue = "false")
	private boolean createFormat;
	@Parameter(defaultValue = "false")
	private boolean skipLicence;
	@Parameter(defaultValue = "false")
	private boolean createLicence;
	@Parameter(defaultValue = "false")
	private boolean skipAnalyzer;

	@Override
	public void execute() throws MojoExecutionException {
		if (isPackagingPOM()) {
			return;
		}
		ProcessContext context = new ProcessContext(project, getPluginDescriptor(), getCurrentExecutionEnvironment(), getLog());
		getLog().info("PROCESS PLUGIN AT " + new Date() + " IN " + execution.getExecutionId() + " - " + execution.getLifecyclePhase());
		space();
		ProcessPlugin processPlugin[] = new ProcessPlugin[]{new FormatProcess(skipFormat, createFormat, context), new LicenseProcess(skipLicence, createLicence, context),
				new AnalyzerProcess(skipAnalyzer, false, context)};
		for (ProcessPlugin process : processPlugin) {
			process.executeProcess();
		}
		space();
	}

	public void space() {
		getLog().info("========================================================================");
	}

	public PluginDescriptor getPluginDescriptor() {
		return (PluginDescriptor) getPluginContext().get("pluginDescriptor");
	}

	public MojoExecutor.ExecutionEnvironment getCurrentExecutionEnvironment() {
		return executionEnvironment(project, session, pluginManager);
	}

	public boolean isPackagingPOM() {
		String type = project.getPackaging();
		return "pom".equalsIgnoreCase(type);
	}
}
