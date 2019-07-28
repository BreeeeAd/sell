package com.zw.sell.entity;

import com.zw.sell.enums.OrderStatusEnums;
import com.zw.sell.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_master")
@Data
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class OrderMaster {

    @Id
    /*
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     it can be applied only on auto increment column(which is int)
    */
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal buyerAmount;

    private Integer orderStatus = OrderStatusEnums.NEW.getCode();

    private Integer payStatus = PayStatusEnums.WAIT.getCode();

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;


}
