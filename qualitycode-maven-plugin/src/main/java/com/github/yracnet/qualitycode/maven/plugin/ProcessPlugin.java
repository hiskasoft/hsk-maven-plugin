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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.twdata.maven.mojoexecutor.MojoExecutor;

public abstract class ProcessPlugin {
	public static final String PATH_CONFIG = "/META-INF/";
	private final ProcessContext context;
	private final boolean skip;
	private final boolean create;
	private final String name;
	private final Log log;

	public ProcessPlugin(String name, boolean skip, boolean create, ProcessContext context) {
		this.name = name.toUpperCase();
		this.skip = skip;
		this.create = create;
		this.context = context;
		this.log = context.getLog();
	}

	public boolean isSkip() {
		return skip;
	}

	public boolean isCreate() {
		return create;
	}

	public Log getLog() {
		return log;
	}

	public URL getComponentResource(String name) {
		return getClass().getResource(name);
	}

	public File getMavenProjectFile(String name) {
		return new File(context.getProject().getBasedir() + "/" + name);
	}

	public Plugin getPluginFromComponentDependency(String groupId, String artifactId) {
		Artifact artifact = context.getArtifactFromComponentDependency(groupId + ":" + artifactId);
		if (artifact != null) {
			Plugin plugin = new Plugin();
			plugin.setGroupId(artifact.getGroupId());
			plugin.setArtifactId(artifact.getArtifactId());
			plugin.setVersion(artifact.getVersion());
			return plugin;
		}
		return null;
	}

	public MojoExecutor.ExecutionEnvironment executionEnvironment() {
		return context.getExecutionEnvironment();
	}

	public void assertPlugin(Plugin plugin, String groupId, String artifactId, String tag) throws MojoExecutionException {
		if (plugin == null) {
			throw new MojoExecutionException("No se ha encontrado el plugin '" + groupId + ":" + artifactId + "' dentro de " + tag);
		}
	}

	public void space() {
		log.info("------------------------------------------------------------------------");
	}

	public void header() {
		space();
		log.info("START PROCESS: " + name + " FOR: " + context.getProjectArtifactId());
		space();
	}

	public void footer() {
		log.info("END PROCESS: " + name);
		space();
	}

	public String processDefaultConfig(String name) throws MojoExecutionException {
		String path = PATH_CONFIG + name;
		URL url = getComponentResource(path);
		if (url == null) {
			throw new MojoExecutionException("Not found: '" + path + "' file config");
		}
		if (isCreate()) {
			try {
				try (InputStream in = getComponentResource(path).openStream()) {
					File file = getMavenProjectFile(name);
					file.getParentFile().mkdirs();
					Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				log.info("Create " + name + " file config");
			} catch (IOException e) {
				log.error("Error when create " + name + " file config", e);
			}
		}
		return url.toExternalForm();
	}

	public void executeProcess() throws MojoExecutionException {
		header();
		if (skip == false) {
			execute();
		}
		footer();
	}

	public abstract void execute() throws MojoExecutionException;
}
