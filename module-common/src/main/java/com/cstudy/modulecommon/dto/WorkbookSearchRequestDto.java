package com.cstudy.modulecommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkbookSearchRequestDto {
    private String title;
    private String description;
    private String titleDesc;
}
