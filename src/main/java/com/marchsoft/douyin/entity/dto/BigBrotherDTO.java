package com.marchsoft.douyin.entity.dto;

import com.marchsoft.douyin.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Wangmingcan
 * @date 2021/8/30 16:29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigBrotherDTO extends User implements Serializable {

    private Integer roomNumber;

    private Integer totalScor;

    private Integer maxScor;
}
