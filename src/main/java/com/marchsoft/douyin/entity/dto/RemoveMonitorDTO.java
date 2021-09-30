package com.marchsoft.douyin.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/24 10:37
 * @description
 */
@Data
public class RemoveMonitorDTO implements Serializable {

    private Integer machineId;

    private Long roomId;
}
