# Quality Code Maven Plugin

You can control of java code from one plugin that embebed other plugin with pre configurations.

The **qualitycode-maven-plugin** encapsule the plugin:
 1 mojo-executor
   Executor of other plugin in one plugin
 2 formatter-maven-plugin
   Formater source code for JS/JAVA/HTML/XML
 3 license-maven-plugin
   Add Header Licence for copyrigth 
 4 maven-pmd-plugin
   Check of quality code

### Configuration

Include the qualitycode-maven-plugin in your project and compile your project 
### pom.xml
```
<project>
 ...
 <build>
 ...
  <plugins>
  ...
   <plugin>
    <groupId>com.github.yracnet.maven</groupId>
    <artifactId>qualitycode-maven-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <executions>
     <execution>
      <phase>process-resources</phase>
      <goals>
       <goal>process</goal>       
      </goals>
     </execution>
    </executions>
   </plugin>
   ...
  </plugins>
  ...
 </build>
 ...
</project>
```
You can enabled or disabled the process control with the flag:
  skipFormat   (default: false)
  skipLicence  (default: false)
  skipAnalyzer (default: false)
You can create de file config into the directory /config with the flag:
  createFormat  (default: false)
  createLicence (default: false)


### Contact

If you have any question, send a email to yracnet@gmail.com.
