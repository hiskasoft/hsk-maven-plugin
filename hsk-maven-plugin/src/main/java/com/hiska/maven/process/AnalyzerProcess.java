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
package com.hiska.maven.process;

import com.hiska.maven.plugin.ProcessContext;
import com.hiska.maven.plugin.ProcessPlugin;
import java.io.File;
import java.io.IOException;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public class AnalyzerProcess extends ProcessPlugin {
	private static final String GROUP_ID = "org.apache.maven.plugins";
	private static final String ARTIFACT_ID = "maven-pmd-plugin";
	private static final String GOALS[] = {"pmd", "cpd", "check", "cpd-check"};
	private static final String MINIMUM_TOKENS = "150";
	private static final String SOURCE_ENCODING = "UTF-8";
	private static final String JAVA_VERSION = "1.8";
	private static final String EXCLUDE_CONFIG = "config/exclude.properties";

	public AnalyzerProcess(boolean skip, boolean create, ProcessContext context) {
		super("ANALYZER", skip, create, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		Plugin analyzerPlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(analyzerPlugin, GROUP_ID, ARTIFACT_ID, "<dependency>");
		String excludeFile = EXCLUDE_CONFIG;
		try {
			File file = getMavenProjectFile(EXCLUDE_CONFIG);
			excludeFile = file.getAbsolutePath();
			if (file.isDirectory()) {
				file.delete();
				file.getParentFile().mkdirs();
				file.createNewFile();
			} else if (file.exists() == false) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Error at create '" + excludeFile + "' file", e);
		}
		for (String goal : GOALS) {
			getLog().debug("->ANALYZER GOAL: " + goal);
			Xpp3Dom configuration = configuration();
			if (!goal.contains("check")) {
				configuration.addChild(element(name("linkXRef"), "false").toDom());
				configuration.addChild(element(name("sourceEncoding"), SOURCE_ENCODING).toDom());
			} else if ("pmd".equals(goal)) {
				configuration.addChild(element(name("targetJdk"), JAVA_VERSION).toDom());
			} else if ("cpd".equals(goal)) {
				configuration.addChild(element(name("minimumTokens"), MINIMUM_TOKENS).toDom());
			}
			configuration.addChild(element(name("excludeFromFailureFile"), excludeFile).toDom());
			executeMojo(analyzerPlugin, goal(goal), configuration, executionEnvironment());
		}
	}
}
