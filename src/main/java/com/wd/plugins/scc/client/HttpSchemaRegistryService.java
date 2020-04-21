package com.wd.plugins.scc.client;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;
import org.apache.maven.plugin.logging.Log;

public class HttpSchemaRegistryService implements SchemaRegistryService
{

    private static final String URL_TEMPLATE_SUBJECT_VERSIONS = "%s/subjects/%s/versions";

    private static final String URL_TEMPLATE_SUBJECT_DETAILS = "%s/subjects/%s/versions/%s";

    private final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

    private Log log;

    private String schemaRegistryUrl;


    public HttpSchemaRegistryService(final String schemaRegistryUrl, final Log log)
    {
        this.schemaRegistryUrl = schemaRegistryUrl;
        this.log = log;
    }


    public SubjectResponseDTO getSubject(final String subject) throws InterruptedException, IOException
    {
        return getSubjectDetails(
            subject,
            getLatestSubjectVersion(subject)
        );
    }


    private Integer getLatestSubjectVersion(final String subject) throws InterruptedException, IOException
    {
        final String url = String.format(
            URL_TEMPLATE_SUBJECT_VERSIONS,
            schemaRegistryUrl,
            subject
        );
        getLog().info("Retrieving latest version from subject [" + subject + "] from [" + url + "]");
        final String response = executeGET(url).body();
        return Stream.of(new Gson().fromJson(response, Integer[].class))
            .mapToInt(v -> v)
            .max()
            .orElseThrow(RuntimeException::new);
    }


    private SubjectResponseDTO getSubjectDetails(final String subject, final Integer version) throws InterruptedException, IOException
    {
        final String url = String.format(
            URL_TEMPLATE_SUBJECT_DETAILS,
            schemaRegistryUrl,
            subject,
            version.toString()
        );
        getLog().info("Retrieving schema from subject [" + subject + "] from [" + url + "]");
        return new Gson().fromJson(executeGET(url).body(), SubjectResponseDTO.class);
    }


    private HttpResponse<String> executeGET(final String url) throws InterruptedException, IOException
    {
        final HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url))
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }


    private Log getLog()
    {
        return this.log;
    }

}
