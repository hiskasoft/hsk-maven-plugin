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
package com.hiskasoft.maven.plugin;

import java.io.File;
import java.net.URL;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.twdata.maven.mojoexecutor.MojoExecutor;

public abstract class Process {

    protected static final String PATH_CONFIG = "/META-INF/";
    protected final Context context;
    protected final Config config;
    protected final boolean skip;
    protected final String name;
    protected final Log log;

    public Process(String name, boolean skip, Context context, Config config) {
        this.name = name.toUpperCase();
        this.skip = skip;
        this.context = context;
        this.config = config;
        this.log = context.getLog();
    }

    public boolean isSkip() {
        return skip;
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

    public void assertPlugin(Plugin plugin, String groupId, String artifactId, String tag)
            throws MojoExecutionException {
        if (plugin == null) {
            throw new MojoExecutionException("No se ha encontrado el plugin '" + groupId + ":" + artifactId + "' dentro de " + tag);
        }
    }

    public void space() {
        log.info("------------------------------------------------------------------------");
    }

    public void skip() {
        log.info("SKIP PROCESS: " + name + " FOR: " + context.getProjectArtifactId());
    }

    public void header() {
        log.info("START PROCESS: " + name + " FOR: " + context.getProjectArtifactId());
    }

    public void footer() {
        log.info("END PROCESS: " + name);
    }

    public String processDefaultConfig(String name) throws MojoExecutionException {
        String path = PATH_CONFIG + name;
        URL url = getComponentResource(path);
        if (url == null) {
            throw new MojoExecutionException("Not found: '" + path + "' file config");
        }
        return url.toExternalForm();
    }

    public void executeProcess() throws MojoExecutionException {
        space();
        if (skip == false) {
            header();
            space();
            execute();
            footer();
        } else {
            skip();
        }
        space();
    }

    public abstract void execute() throws MojoExecutionException;
}
