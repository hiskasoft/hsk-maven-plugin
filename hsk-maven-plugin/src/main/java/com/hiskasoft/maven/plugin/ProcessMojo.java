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

import com.hiskasoft.maven.process.AnalyzerProcess;
import com.hiskasoft.maven.process.FormatJavaProcess;
import com.hiskasoft.maven.process.FormatXmlProcess;
import com.hiskasoft.maven.process.LicenseProcess;
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
@lombok.Getter
public class ProcessMojo extends AbstractMojo implements Config {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;
    @Component
    private BuildPluginManager pluginManager;
    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution execution;

    @Parameter(defaultValue = "false")
    private boolean skip;

    @Parameter(defaultValue = "UTF-8")
    private String encoding;
    @Parameter(defaultValue = "CRLF")
    private String lineEnding;
    @Parameter(defaultValue = "1.8", property = "maven.compiler.source")
    private String javaVersion;

    @Parameter(defaultValue = "false")
    private boolean skipLicence;
    @Parameter(defaultValue = "false")
    private boolean skipAnalyzer;

    @Parameter(defaultValue = "false")
    private boolean skipCss;
    @Parameter(defaultValue = "false")
    private boolean skipHtml;
    @Parameter(defaultValue = "false")
    private boolean skipJava;
    @Parameter(defaultValue = "false")
    private boolean skipJs;
    @Parameter(defaultValue = "false")
    private boolean skipJson;
    @Parameter(defaultValue = "false")
    private boolean skipXml;

    @Override
    public void execute() throws MojoExecutionException {
        if (isPackagingPOM() || skip) {
            return;
        }

        Context context = new Context(project,
                getPluginDescriptor(),
                getCurrentExecutionEnvironment(),
                getLog());

        getLog().info("PROCESS PLUGIN AT " + new Date() + " IN " + execution.getExecutionId() + " - " + execution.getLifecyclePhase());
        space();
        Process processPlugin[] = new Process[]{
            new FormatJavaProcess(skipJava, context, this),
            new FormatXmlProcess(skipXml, context, this),
            new LicenseProcess(skipLicence, context, this),
            new AnalyzerProcess(skipAnalyzer, context, this)
        };
        for (Process process : processPlugin) {
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
