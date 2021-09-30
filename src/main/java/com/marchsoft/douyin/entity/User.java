package com.marchsoft.douyin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 加密id
     */
    private String secUid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别（全部是男）
     */
    private String gender;

    /**
     * 礼物等级
     */
    private Integer level;

    /**
     * 抖音号
     */
    private String displayId;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 学校
     */
    private String schoolName;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 粉丝数量
     */
    private Long followerCount;

    /**
     * 关注数量
     */
    private Long followingCount;

    /**
     * 分析数量
     */
    private Long forwardCount;

    /**
     * 发布抖音数
     */
    private Long awemeCount;

    /**
     * 总获得点赞数
     */
    private Long totalFavorited;

    /**
     * 1私密、0公开
     */
    @TableField(value = "is_secret")
    private Boolean secret;

    /**
     * 最后一次打赏时间
     */
    private LocalDateTime lastPayTime;

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
     * 保留字段
     */
    private Boolean status;

    /**
     * 用户本直播间刷了多少礼物
     */
    @TableField(exist = false)
    private Integer scor;

}
