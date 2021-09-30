package com.marchsoft.douyin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/24 9:49
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmallRoomDTO implements Serializable {

    private Long roomId;

    private Long userId;
}
