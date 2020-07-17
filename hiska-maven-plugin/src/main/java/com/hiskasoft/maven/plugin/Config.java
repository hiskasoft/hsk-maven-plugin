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

public interface Config {

    public String getEncoding();

    public String getLineEnding();

    public String getJavaVersion();

    public boolean isSkipPom();

    public boolean isSkipCss();

    public boolean isSkipHtml();

    public boolean isSkipJava();

    public boolean isSkipJs();

    public boolean isSkipJson();

    public boolean isSkipXml();

    public File getBasedir();

    public File getWebappDir();

    public File getJavaDir();

    public File getResourcesDir();

    public File getTempDir();
}
