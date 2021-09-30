package com.marchsoft.douyin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marchsoft.douyin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marchsoft.douyin.entity.dto.BigBrotherDTO;
import com.marchsoft.douyin.entity.dto.PageDTO;
import com.marchsoft.douyin.entity.dto.SecUserDTO;
import com.marchsoft.douyin.entity.dto.UserScorDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
public interface IUserService extends IService<User> {

    List<UserScorDTO> getScor(Long userId);

    List<SecUserDTO> getUnUpdateBigBrother();

    PageDTO<BigBrotherDTO> getBigBrother(int number, String startTime, String endTime, Page page);

    void download(Page page, int number, int streamer, String startTime, String endTime, HttpServletResponse response) throws IOException;

    boolean updateLastPayTime(Long userId, LocalDateTime time);
}
