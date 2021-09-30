package com.marchsoft.douyin.entity.dto;

import com.marchsoft.douyin.entity.Room;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/23 15:15
 * @description
 */
@Data
public class UserScorDTO implements Serializable {

    private Long roomId;

    private Integer scor;
}
