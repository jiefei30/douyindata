package com.marchsoft.douyin.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marchsoft.douyin.entity.User;
import com.marchsoft.douyin.entity.dto.BigBrotherDTO;
import com.marchsoft.douyin.entity.dto.PageDTO;
import com.marchsoft.douyin.exception.BadRequestException;
import com.marchsoft.douyin.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<Object> getRoom(@RequestParam Long userId) {
        if (ObjectUtil.isNull(userId)) {
            throw new BadRequestException("userId为空");
        }
        return new ResponseEntity<>(userService.getScor(userId), HttpStatus.OK);
    }

    @GetMapping("/update")
    public ResponseEntity<Object> getUnUpdateBigBrother() {
        return new ResponseEntity<>(userService.getUnUpdateBigBrother(), HttpStatus.OK);
    }

    @GetMapping("/big")
    public ResponseEntity<Object> getBigBrother(@RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "100") int size,
                                                @RequestParam(value = "number", defaultValue = "2") int number,
                                                @RequestParam(value = "startTime", defaultValue = "2021-09-01 00:00:00") String startTime,
                                                @RequestParam(value = "endTime", defaultValue = "2022-09-01 00:00:00") String endTime) {
        System.out.println(startTime + " " );
        Page<BigBrotherDTO> iPage = new Page<>(page, size);
        return new ResponseEntity<>(userService.getBigBrother(number, startTime, endTime, iPage), HttpStatus.OK);
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "100") int size,
                         @RequestParam(value = "number", defaultValue = "2") int number,
                         @RequestParam(value = "streamer", defaultValue = "0") int streamer,
                         @RequestParam(value = "startTime", defaultValue = "2021-09-01 00:00:00") String startTime,
                         @RequestParam(value = "endTime", defaultValue = "2022-09-01 00:00:00") String endTime) throws Exception {
        Page<BigBrotherDTO> iPage = new Page<>(page, size);
        number = number < 2? 2:number;
        userService.download(iPage, number, streamer,startTime, endTime, response);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody List<User> users) {
        for (User user : users) {
            if (user.getBirthday().equals("")) {
                user.setBirthday(null);
            }
        }
        userService.updateBatchById(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
