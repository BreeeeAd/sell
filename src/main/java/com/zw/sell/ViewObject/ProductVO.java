package com.zw.sell.ViewObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

// make sure only provide the data which front-end needed
// keep security

@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("clothes")
    private List<ProductInfoVO> productInfoVOS;

}
