package com.marchsoft.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marchsoft.douyin.entity.Room;
import com.marchsoft.douyin.entity.User;
import com.marchsoft.douyin.mapper.RoomMapper;
import com.marchsoft.douyin.mapper.UserMapper;
import com.marchsoft.douyin.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marchsoft.douyin.service.IUserService;
import com.marchsoft.douyin.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    public static final String ROOM_SPACE = "room::id:";
    private final RedisUtils redisUtils;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoom(Room room) {
        rabbitTemplate.convertAndSend("room", room);
    }

    @Override
    public Room getRoom(Long roomId) {
        if (redisUtils.hasKey(ROOM_SPACE + roomId)) {
            return (Room) redisUtils.get(ROOM_SPACE + roomId);
        }
        return null;
    }

    @Override
    public int countByTime(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Room::getCreateTime, startTime, endTime);
        return count(queryWrapper);
    }
}
