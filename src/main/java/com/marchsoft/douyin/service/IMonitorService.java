package com.marchsoft.douyin.service;

import com.marchsoft.douyin.entity.dto.GetMonitorDTO;
import com.marchsoft.douyin.entity.dto.RemoveMonitorDTO;
import com.marchsoft.douyin.entity.dto.SmallRoomDTO;

import java.util.List;
import java.util.Map;

/**
 *  监控接口
 */
public interface IMonitorService {

    /**
     * 向监控池中添加房间
     * @param rooms
     */
    void add(List<SmallRoomDTO> rooms);

    /**
     * 从监控池中移除房间
     */
    void remove(RemoveMonitorDTO monitor);

    /**
     * 从监控池中获取房间
     * @return
     */
    List<SmallRoomDTO> get(GetMonitorDTO monitor);

    /**
     * 正在监控的房间个数
     */
    Map<String, Integer> monitoring();
}
