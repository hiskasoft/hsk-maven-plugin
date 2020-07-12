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
import java.util.Map;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import com.hiskasoft.maven.process.Context;

public class MavenContext implements Context{

    private final MavenProject project;
    private final PluginDescriptor pluginDescriptor;
    private final Map<String, Artifact> artifactMap;
    private final MojoExecutor.ExecutionEnvironment executionEnvironment;

    public MavenContext(MavenProject project, PluginDescriptor pluginDescriptor, MojoExecutor.ExecutionEnvironment environment) {
        this.project = project;
        this.pluginDescriptor = pluginDescriptor;
        this.artifactMap = pluginDescriptor.getArtifactMap();
        this.executionEnvironment = environment;
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

    public String getProjectArtifactId() {
        return project.getArtifactId();
    }

    @Override
    public File getBasedir() {
        return project.getBasedir();
    }

    @Override
    public String getGroupId() {
        return project.getGroupId();
    }

    @Override
    public String getArtifactId() {
        return project.getArtifactId();
    }

    @Override
    public String getVersion() {
        return project.getVersion();
    }

    @Override
    public String getPackaging() {
        return project.getPackaging();
    }
}
