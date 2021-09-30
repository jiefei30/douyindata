package com.marchsoft.douyin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.marchsoft.douyin.entity.dto.GetMonitorDTO;
import com.marchsoft.douyin.entity.dto.RemoveMonitorDTO;
import com.marchsoft.douyin.entity.dto.SmallRoomDTO;
import com.marchsoft.douyin.service.IMonitorService;
import com.marchsoft.douyin.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Wangmingcan
 * @date 2021/8/24 9:44
 * @description
 */
@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class MonitorServiceImpl implements IMonitorService {

    private static final String MONITOR_PREFIX = "monitor_";
    private final RedisUtils redisUtils;

    @Override
    public void add(List<SmallRoomDTO> rooms) {
        for (SmallRoomDTO room : rooms) {
            if (ObjectUtil.isNotNull(room) && room.getRoomId() != null) {
                if (redisUtils.set(monitorPrefixBuild(0) + room.getRoomId(),
                        room.getUserId(), 60*60*24, TimeUnit.SECONDS)) {
                    log.info("向redis中新插入监控房间:" + room.getRoomId());
                }
            }
        }
    }

    @Override
    public void remove(RemoveMonitorDTO monitor) {
        redisUtils.del(monitorPrefixBuild(monitor.getMachineId())+monitor.getRoomId());
    }

    @Override
    public synchronized List<SmallRoomDTO> get(GetMonitorDTO monitor) {
        if (redisUtils.hasKey(monitorPrefixBuild(0)+"null")) {
            redisUtils.del(monitorPrefixBuild(0)+"null");
        }
        String pattern = monitorPrefixBuild(0) + "*";
        List<String> keys = redisUtils.scanLimit(pattern, monitor.getCount());
        List<SmallRoomDTO> result = new ArrayList<>();
        for (String key : keys) {
            Long roomId = Long.valueOf(key.split("_")[2]);
            result.add(new SmallRoomDTO(roomId, (Long)redisUtils.get(key)));
            redisUtils.renameKey(key, monitorPrefixBuild(monitor.getMachineId())+roomId);
        }
        return result;
    }

    @Override
    public Map<String, Integer> monitoring() {
        Map<String, Integer> map = new HashMap<>();
        //房间总量
        String pattern1 = MONITOR_PREFIX + "*";
        //未被监控数量
        String pattern2 = monitorPrefixBuild(0) + "*";
        int count1 = redisUtils.scanCount(pattern1);
        int count2 = redisUtils.scanCount(pattern2);
        map.put("房间总数量", count1);
        map.put("未被监控数量", count2);
        map.put("正在监控数量", count1 - count2);
        return map;
    }

    private String monitorPrefixBuild(int machineId) {
        return MONITOR_PREFIX + machineId + "_";
    }
}
