package com.marchsoft.douyin.service;

import com.marchsoft.douyin.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
public interface IRoomService extends IService<Room> {

    /**
     *
     * @param room
     */
    void saveRoom(Room room);

    /**
     *
     * @param roomId
     * @return
     */
    Room getRoom(Long roomId);

    /**
     * 根据时间查询新增房间个数
     */
    int countByTime(LocalDateTime startTime, LocalDateTime endTime);

}
