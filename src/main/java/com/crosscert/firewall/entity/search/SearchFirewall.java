package com.crosscert.firewall.entity.search;

import io.swagger.annotations.ApiModelProperty;

public class SearchFirewall {

    @ApiModelProperty("pageSize")
    private int size;

    @ApiModelProperty("page 수")
    private int page;

    @ApiModelProperty("sort")
    private String sort;

    @ApiModelProperty("id")
    private Long id;
}

