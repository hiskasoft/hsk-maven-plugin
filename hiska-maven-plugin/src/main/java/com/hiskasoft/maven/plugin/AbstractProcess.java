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

import com.hiskasoft.maven.process.Logger;
import java.io.File;
import java.net.URL;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Plugin;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import com.hiskasoft.maven.process.Process;
import org.apache.maven.project.MavenProject;

@lombok.Getter
public abstract class AbstractProcess implements Process {

    protected static final String PATH_CONFIG = "/META-INF/";
    protected final MavenContext context;
    protected final Config config;
    protected final boolean skip;
    protected final String name;
    protected final Logger logger;

    public AbstractProcess(String name, boolean skip, Config config, MavenContext context, Logger logger) {
        this.name = name.toUpperCase();
        this.skip = skip;
        this.context = context;
        this.config = config;
        this.logger = logger;
    }

    public URL getComponentResource(String name) {
        return getClass().getResource(name);
    }

    public File getMavenProjectFile(String name) {
        return getMavenProjectFile(context.getProject() , name);
    }
    
    public File getMavenProjectFile( MavenProject project, String name){
        File file = new File(project.getBasedir() , name);
        project = project.getParent();
        if(!file.exists() && project != null){
            file = getMavenProjectFile(project, name);
        }
        return file;
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
            throws Exception {
        if (plugin == null) {
            throw new Exception("No se ha encontrado el plugin '" + groupId + ":" + artifactId + "' dentro de " + tag);
        }
    }

    public String processDefaultConfig(String name) throws Exception {
        String path = PATH_CONFIG + name;
        URL url = getComponentResource(path);
        if (url == null) {
            throw new Exception("Not found: '" + path + "' file config");
        }
        return url.toExternalForm();
    }

    @Override
    public void execute() throws Exception {
        logger.start(this);
        if (isSkip() == false) {
            executeInternal();
        } else {
            logger.start(this);
            logger.info("SKIP");
        }
        logger.end(this);
        //logger.space();
    }

    public abstract void executeInternal() throws Exception;

    @Override
    public int getOrder() {
        return 100;
    }
}
