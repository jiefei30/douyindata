package com.marchsoft.douyin.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wangmingcan
 * @date 2021/8/30 17:05
 * @description
 */
@Data
public class PageDTO<T> implements Serializable {

    private List<T> data;

    private Long total;

    private Long size;
}
