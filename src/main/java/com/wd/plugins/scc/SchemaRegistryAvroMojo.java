package com.wd.plugins.scc;

import com.wd.plugins.scc.client.HttpSchemaRegistryService;
import com.wd.plugins.scc.client.SchemaRegistryService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "download-avro", defaultPhase = LifecyclePhase.VALIDATE)
public class SchemaRegistryAvroMojo extends AbstractMojo
{

    @Parameter(property = "schemaRegistryUrl")
    private String schemaRegistryUrl;

    @Parameter(property = "subjects")
    private String[] subjects;

    @Parameter(property = "targetDirectory", defaultValue = "target/avro/")
    private String targetDirectory;


    @Override
    public void execute()
    {
        final SchemaRegistryService schemaRegistryService = new HttpSchemaRegistryService(schemaRegistryUrl, getLog());
        for (String subject : subjects)
        {
            try
            {
                if (new File(targetDirectory).mkdirs())
                {
                    getLog().info("Base directory " + targetDirectory + " created successfully");
                }
                final Path subjectAvscFilePath = Paths.get(targetDirectory.concat(subject).concat(".avsc"));
                Files.writeString(subjectAvscFilePath, schemaRegistryService.getSubject(subject).getSchema());
            }
            catch (InterruptedException | IOException ex)
            {
                getLog().error(ex);
            }
        }
    }

}
