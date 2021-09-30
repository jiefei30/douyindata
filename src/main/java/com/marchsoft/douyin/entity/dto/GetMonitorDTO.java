package com.marchsoft.douyin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/24 9:53
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMonitorDTO implements Serializable {

    private Integer machineId;

    private Integer count;
}
