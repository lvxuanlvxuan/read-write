package com.db.spli.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名称
     */
    @JsonProperty("user_name")
    private String userName;

    /**
     * 住址
     */
    private String address;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}