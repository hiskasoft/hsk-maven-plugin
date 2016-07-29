package dev.yracnet.plugin.goodness;

import java.util.HashMap;
import java.util.Map;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.repository.ComponentDependency;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

public class ProcessContext {

	private static Map<String, ComponentDependency> componentDependency = null;

	private void initComponentDependency() {
		if (componentDependency == null) {
			componentDependency = new HashMap<>();
			PluginDescriptor pluginDescriptor = getPluginDescriptor();
			pluginDescriptor.getDependencies().forEach(dependency -> {
				getLog().debug("--->PLUGIN DEPENDENCY: " + dependency);
				componentDependency.put(dependency.getGroupId() + ":" + dependency.getArtifactId(), dependency);
			});
		}
	}

	private final AbstractMojo caller;
	private final MavenProject mavenProject;
	private final MavenSession mavenSession;
	private final BuildPluginManager pluginManager;

	public ProcessContext(AbstractMojo caller, MavenProject mavenProject, MavenSession mavenSession, BuildPluginManager pluginManager) {
		this.caller = caller;
		this.mavenProject = mavenProject;
		this.mavenSession = mavenSession;
		this.pluginManager = pluginManager;
		initComponentDependency();
	}

	public boolean isPOM() {
		String type = mavenProject.getPackaging();
		return "pom".equalsIgnoreCase(type);
	}

	public Log getLog() {
		return caller.getLog();
	}

	public Map getPluginContext() {
		return caller.getPluginContext();
	}

	public PluginDescriptor getPluginDescriptor() {
		return (PluginDescriptor) getPluginContext().get("pluginDescriptor");
	}

	public MavenProject getMavenProject() {
		return mavenProject;
	}

	public MavenSession getMavenSession() {
		return mavenSession;
	}

	public BuildPluginManager getPluginManager() {
		return pluginManager;
	}

	public MojoExecutor.ExecutionEnvironment getCurrentExecutionEnvironment() {
		return executionEnvironment(mavenProject, mavenSession, pluginManager);
	}

	public ComponentDependency getComponentDependency(String groupId, String artifactId) {
		String key = groupId + ":" + artifactId;
		getLog().debug("--->PLUGIN GET: " + key + " in " + componentDependency.keySet());
		return componentDependency.get(key);
	}

}
