package com.zw.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zw.sell.entity.OrderDetail;
import com.zw.sell.enums.OrderStatusEnums;
import com.zw.sell.enums.PayStatusEnums;
import com.zw.sell.utils.EnumUtil;
import com.zw.sell.utils.serializer.DateToLongSerializer;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.math.BigDecimal;
import java.util.*;

@Data
@EntityListeners(AuditingEntityListener.class)
//@JsonInclude(JsonInclude.Include.NON_NULL)
// if the Object contain any null value, it will not show up by using @JsonInclude
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal buyerAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    public OrderDTO(String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid) {
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
    }

    public OrderDTO() {

    }

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnums.class);
    }

    @JsonIgnore
    public PayStatusEnums getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnums.class);

    }

}
