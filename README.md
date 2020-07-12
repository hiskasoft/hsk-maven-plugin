# HSK Maven Plugin

You can control of java code from one plugin that embebed other plugin with pre configurations.

The **hiska-maven-plugin** encapsule the plugin:
 1 mojo-executor
   Executor of other plugin in one plugin
 2. formatter-maven-plugin
   Formater source code for JS/JAVA/HTML/XML
 3. license-maven-plugin
   Add Header Licence for copyrigth 
 4. maven-pmd-plugin
   Check of quality code

### Configuration

Include the hiska-maven-plugin in your project and compile your project 
### pom.xml
```
<project>
 ...
 <build>
 ...
  <plugins>
  ...
   <plugin>
    <groupId>com.hiskasoft.maven</groupId>
    <artifactId>hiska-maven-plugin</artifactId>
    <version>${last-version}</version>
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
### Contact

If you have any question, send a email to wyujra@hiskasoft.com.
