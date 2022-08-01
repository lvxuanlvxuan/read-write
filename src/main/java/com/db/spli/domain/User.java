package com.db.spli.domain;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 住址
     */
    private String address;

    /**
     * 权重，大者优先
     */
    private Integer weight;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

}