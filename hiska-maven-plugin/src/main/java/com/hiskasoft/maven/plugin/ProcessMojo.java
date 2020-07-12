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

import com.hiskasoft.maven.internal.AnalyzerProcess;
import com.hiskasoft.maven.internal.FormatProcess;
import com.hiskasoft.maven.internal.FormatXmlProcess;
import com.hiskasoft.maven.internal.LicenseProcess;
import com.hiskasoft.maven.process.Logger;
import java.util.Date;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

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

    @Parameter(defaultValue = "UTF-8", property = "project.build.sourceEncoding")
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
    private boolean skipFormat;
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
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (isPackagingPOM() || skip) {
            return;
        }
        Logger logger = new MavenLogger(getLog());
        MavenContext context = new MavenContext(project,
                getPluginDescriptor(),
                getCurrentExecutionEnvironment());

        getLog().info("PROCESS PLUGIN AT " + new Date() + " IN " + execution.getExecutionId() + " - " + execution.getLifecyclePhase());
        space();

        AbstractProcess processPlugin[] = new AbstractProcess[]{
            new FormatProcess(skipFormat, this, context, logger),
            new FormatXmlProcess(skipFormat, this, context, logger),
            new LicenseProcess(skipLicence, this, context, logger),
            new AnalyzerProcess(skipAnalyzer, this, context, logger)
        };
        for (AbstractProcess process : processPlugin) {
            try {
                process.execute();
            } catch (Exception e) {
                logger.error(process, e);
            }
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
