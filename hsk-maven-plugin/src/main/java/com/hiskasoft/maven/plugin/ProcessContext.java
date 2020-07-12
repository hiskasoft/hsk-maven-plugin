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

import java.util.Map;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;

public class ProcessContext {

    private final MavenProject project;
    private final PluginDescriptor pluginDescriptor;
    private final Map<String, Artifact> artifactMap;
    private final MojoExecutor.ExecutionEnvironment executionEnvironment;
    private final Log log;

    public ProcessContext(MavenProject project, PluginDescriptor pluginDescriptor, MojoExecutor.ExecutionEnvironment environment, Log log) {
        this.project = project;
        this.pluginDescriptor = pluginDescriptor;
        this.artifactMap = pluginDescriptor.getArtifactMap();
        this.executionEnvironment = environment;
        this.log = log;
    }

    public PluginDescriptor getPluginDescriptor() {
        return pluginDescriptor;
    }

    public MojoExecutor.ExecutionEnvironment getExecutionEnvironment() {
        return executionEnvironment;
    }

    public Artifact getArtifactFromComponentDependency(String name) {
        return artifactMap.get(name);
    }

    public MavenProject getProject() {
        return project;
    }

    public Map<String, Artifact> getArtifactMap() {
        return artifactMap;
    }

    public Log getLog() {
        return log;
    }

    public String getProjectArtifactId() {
        return project.getArtifactId();
    }
}
