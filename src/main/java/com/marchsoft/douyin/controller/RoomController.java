package com.marchsoft.douyin.controller;


import com.google.common.util.concurrent.RateLimiter;
import com.marchsoft.douyin.entity.Room;
import com.marchsoft.douyin.entity.User;
import com.marchsoft.douyin.exception.BadRequestException;
import com.marchsoft.douyin.service.IRoomService;
import com.marchsoft.douyin.service.impl.RoomServiceImpl;
import com.marchsoft.douyin.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    private RateLimiter rateLimiter = RateLimiter.create(100);

    @PostMapping
    public ResponseEntity<Object> postData(@RequestBody Room room) {
        if (ObjectUtils.isEmpty(room)) {
            throw new BadRequestException("room为空");
        }
        if (room.getUserId() == null || room.getUserId().equals(0)) {
            throw new BadRequestException("room的主播id为0");
        }
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)) {
            return new ResponseEntity<>("限流，被拒绝", HttpStatus.EXPECTATION_FAILED);
        }
        roomService.saveRoom(room);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getData(@RequestParam Long roomId) {
        if (ObjectUtils.isEmpty(roomId)) {
            throw new BadRequestException("roomId为空");
        }
        return new ResponseEntity<>(roomService.getRoom(roomId), HttpStatus.OK);
    }


}
