package com.hiskasoft.maven.process;

import java.io.File;

public interface Context {

    public File getBasedir();

    public String getGroupId();

    public String getArtifactId();

    public String getVersion();

    public String getPackaging();

}
