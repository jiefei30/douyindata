package com.marchsoft.douyin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.marchsoft.douyin.entity.dto.GetMonitorDTO;
import com.marchsoft.douyin.entity.dto.RemoveMonitorDTO;
import com.marchsoft.douyin.entity.dto.SmallRoomDTO;
import com.marchsoft.douyin.exception.BadRequestException;
import com.marchsoft.douyin.service.IMonitorService;
import com.marchsoft.douyin.service.IRoomService;
import com.marchsoft.douyin.util.DateUtil;
import com.marchsoft.douyin.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Wangmingcan
 * @date 2021/8/24 9:41
 * @description
 */
@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final IMonitorService monitorService;
    private final IRoomService roomService;
    private final RedisUtils redisUtils;

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody List<SmallRoomDTO> rooms) {
        if (ObjectUtils.isEmpty(rooms)) {
            throw new BadRequestException("rooms为空");
        }
        monitorService.add(rooms);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody RemoveMonitorDTO monitor) {
        if (ObjectUtils.isEmpty(monitor)) {
            throw new BadRequestException("monitor为空");
        }
        monitorService.remove(monitor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam Integer machineId,
                                      @RequestParam Integer count) {
        if (ObjectUtil.isNull(machineId) || ObjectUtil.isNull(count)) {
            throw new BadRequestException("machineId或count为空");
        }
        return new ResponseEntity<>(monitorService.get(new GetMonitorDTO(machineId, count)), HttpStatus.OK);
    }

    @GetMapping("/ing")
    public ResponseEntity<Object> ing() {
        return new ResponseEntity<>(monitorService.monitoring(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Object> count(@RequestParam(value = "startTime", defaultValue = "2021-09-01 00:00:00") String startTime,
                                        @RequestParam(value = "endTime", defaultValue = "2022-09-01 00:00:00") String endTime) {
        LocalDateTime start = DateUtil.parseLocalDateTimeFormatyMdHms(startTime);
        LocalDateTime end = DateUtil.parseLocalDateTimeFormatyMdHms(endTime);
        return new ResponseEntity<>(roomService.countByTime(start, end), HttpStatus.OK);
    }

}
