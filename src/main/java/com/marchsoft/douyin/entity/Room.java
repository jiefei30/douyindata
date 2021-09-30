package com.marchsoft.douyin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 直播间id
     */
    @TableId(value = "room_id")
    private Long roomId;

    /**
     * 直播间名称
     */
    private String title;

    /**
     * 用户抖音号
     */
    private String displayId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 观看人数
     */
    private Integer userCount;

    /**
     * 直播人加密id
     */
    private String secUid;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 0失效，1正常
     */
    private Boolean status;

    /**
     * 该直播间的用户
     */
    @TableField(exist = false)
    private List<User> users;
}
