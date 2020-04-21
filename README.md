# Schema Registry Avro Maven Plugin

This maven plugin will download the avro schemas from the Schema Registry and create a local copy.

To use it, add it in your pom build, like below:

```
<build>
    <plugins>
        <plugin>
            <groupId>com.wd.plugins.schemaregistry.avro</groupId>
            <artifactId>schema-registry-avro-maven-plugin</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <goals>
                        <goal>download-avro</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <schemaRegistryUrl>https://your-schema-registry-url</schemaRegistryUrl>
                <subjects>
                    <subject>subject1</subject>
                    <subject>subject2</subject>
                </subjects>
                <!-- optional, detault is "target/avro/" -->
                <targetDirectory>target/avro/</targetDirectory>
            </configuration>
        </plugin>
    </plugins>
<build>
```

The plugin will use always the latest version from a given schema.

To see what are the available subjects from your schema registry you can use the [REST Api](https://docs.confluent.io/current/schema-registry/develop/api.html):

```
curl 'https://your-schema-registry-url/subjects'
```

This plugin will just download the avros.

To generate the java objects, please use the official [avro maven plugin](https://avro.apache.org/docs/current/gettingstartedjava.html).