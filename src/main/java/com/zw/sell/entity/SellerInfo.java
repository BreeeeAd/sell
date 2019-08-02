package com.zw.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@EntityListeners(AuditingEntityListener.class)
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

}
