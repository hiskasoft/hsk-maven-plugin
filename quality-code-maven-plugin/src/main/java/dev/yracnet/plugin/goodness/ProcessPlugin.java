package dev.yracnet.plugin.goodness;

import java.io.File;
import java.net.URL;
import java.util.Map;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.repository.ComponentDependency;
import org.twdata.maven.mojoexecutor.MojoExecutor;

public abstract class ProcessPlugin {

	private final ProcessContext context;
	private final boolean skip;
	private final String name;
	private final String config;

	public ProcessPlugin(String name, boolean skip, ProcessContext context) {
		this.context = context;
		this.skip = skip;
		this.name = name;
		this.config = name.toLowerCase() + ".xml";
	}

	public String getNameProcess() {
		return name;
	}

	public String getConfigProcess() {
		return config;
	}

	public ProcessContext getContext() {
		return context;
	}

	public boolean isSkip() {
		return skip;
	}

	public Log getLog() {
		return context.getLog();
	}

	public Map getPluginContext() {
		return context.getPluginContext();
	}

	public PluginDescriptor getPluginDescriptor() {
		return context.getPluginDescriptor();
	}

	public URL getComponentResource(String name) {
		return getClass().getResource(name);
	}

	public File getMavenProjectFile(String name) {
		return new File(context.getMavenProject().getBasedir() + "/" + name);
	}

	public String getPathFromMavenProjectOrComponent(String fileProject, String fileComponent) {
		File file = getMavenProjectFile(fileProject);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		URL url = getComponentResource(fileComponent);
		if (url == null) {
			return null;
		}
		return url.toExternalForm();
	}

	public Plugin getPluginFromComponentDependency(String groupId, String artifactId) {
		ComponentDependency dependency = context.getComponentDependency(groupId, artifactId);
		if (dependency != null) {
			Plugin plugin = new Plugin();
			plugin.setGroupId(dependency.getGroupId());
			plugin.setArtifactId(dependency.getArtifactId());
			plugin.setVersion(dependency.getVersion());
			return plugin;
		}
		return null;
	}

	public MojoExecutor.ExecutionEnvironment currentExecutionEnvironment() {
		return context.getCurrentExecutionEnvironment();
	}

	public void assertPlugin(Plugin plugin, String groupId, String artifactId, String tag) throws MojoExecutionException {
		if (plugin == null) {
			throw new MojoExecutionException("No se ha encontrado el plugin '" + groupId + ":" + artifactId + "' dentro de " + tag);
		}
	}

	public void assertLocalResource(URL url, String file) throws MojoExecutionException {
		if (url == null) {
			throw new MojoExecutionException("No se ha encontrado el archivo '" + file + "'");
		}
	}

	public MavenProject getMavenProject() {
		return context.getMavenProject();
	}

	public String getMavenProjectArtifactId() {
		return context.getMavenProject().getArtifactId();
	}

	public String getMavenProjectGroupId() {
		return context.getMavenProject().getGroupId();
	}

	public void header() {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("START PROCESS: " + name + " FOR: " + getMavenProjectArtifactId());
	}

	public void footer() {
		getLog().info("END PROCESS: " + name);
		getLog().info("------------------------------------------------------------------------");
	}

	public abstract void execute() throws MojoExecutionException;
}
