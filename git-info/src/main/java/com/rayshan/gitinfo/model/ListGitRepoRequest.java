package com.rayshan.gitinfo.model;

import com.rayshan.gitinfo.validation.OrderEnum;
import com.rayshan.gitinfo.validation.SortByEnum;
import com.rayshan.gitinfo.validation.ValidEnumValue;
import lombok.Data;

@Data
public class ListGitRepoRequest {

    @ValidEnumValue(enumClass = SortByEnum.class)
    private String sortBy;

    @ValidEnumValue(enumClass = OrderEnum.class)
    private String order;

    private String limit;
}
