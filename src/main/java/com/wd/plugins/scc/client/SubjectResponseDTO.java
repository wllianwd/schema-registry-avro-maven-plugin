package com.wd.plugins.scc.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponseDTO
{

    private Long id;

    private String subject;

    private Integer version;

    private String schema;

}
