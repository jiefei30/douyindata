package com.marchsoft.douyin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/30 16:04
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecUserDTO implements Serializable {

    private Long id;

    private String secUid;
}
