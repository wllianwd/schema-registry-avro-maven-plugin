package com.wd.plugins.scc.client;

import java.io.IOException;

public interface SchemaRegistryService
{

    SubjectResponseDTO getSubject(final String subject) throws InterruptedException, IOException;

}
