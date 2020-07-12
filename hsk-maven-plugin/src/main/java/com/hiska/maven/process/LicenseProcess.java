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
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public class LicenseProcess extends ProcessPlugin {

	private static final String GROUP_ID = "com.mycila";
	private static final String ARTIFACT_ID = "license-maven-plugin";
	private static final String GOAL = "format";
	private static final String LICENCE_TEXT = "config/licence.txt";

	public LicenseProcess(boolean skip, boolean create, ProcessContext context) {
		super("LICENSE", skip, create, context);
	}

	@Override
	public void execute() throws MojoExecutionException {
		Plugin licensePlugin = getPluginFromComponentDependency(GROUP_ID, ARTIFACT_ID);
		assertPlugin(licensePlugin, GROUP_ID, ARTIFACT_ID, "<pluginManagement>");
		String licenceFile;
		File file = getMavenProjectFile(LICENCE_TEXT);
		if (file.exists() && file.isFile()) {
			licenceFile = file.getAbsolutePath();
		} else {
			licenceFile = processDefaultConfig(LICENCE_TEXT);
		}
		executeMojo(
										licensePlugin,
										goal(GOAL),
										configuration(
																		element(name("header"), licenceFile),
																		element(name("strictCheck"), "true"),
																		element(name("includes"),
																										element(name("include"), "src/main/java/**/*.java"),
																										element(name("include"), "src/main/webapp/*.html"),
																										element(name("include"), "src/main/webapp/view/*.html"),
																										element(name("include"), "src/main/webapp/ctrl/**/*.js")),
																		element(name("excludes"),
																										element(name("exclude"), "src/main/webapp/part/**/*.*"),
																										element(name("exclude"), "src/main/webapp/include/**/*.*"),
																										element(name("exclude"), "src/main/webapp/fragment/**/*.*"))),
										executionEnvironment());
	}
}
