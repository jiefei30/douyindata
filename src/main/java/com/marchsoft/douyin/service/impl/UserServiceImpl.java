package com.marchsoft.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marchsoft.douyin.entity.User;
import com.marchsoft.douyin.entity.dto.BigBrotherDTO;
import com.marchsoft.douyin.entity.dto.PageDTO;
import com.marchsoft.douyin.entity.dto.SecUserDTO;
import com.marchsoft.douyin.entity.dto.UserScorDTO;
import com.marchsoft.douyin.mapper.UserMapper;
import com.marchsoft.douyin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marchsoft.douyin.util.DateUtil;
import com.marchsoft.douyin.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public List<UserScorDTO> getScor(Long userId) {
        return userMapper.getScor(userId);
    }

    @Override
    public List<SecUserDTO> getUnUpdateBigBrother() {
        return userMapper.getUnUpdateBigBrother();
    }

    @Override
    public PageDTO<BigBrotherDTO> getBigBrother(int number, String startTime, String endTime, Page page) {
        IPage<BigBrotherDTO> bigBrotherDTOIPage = userMapper.getBigBrother(number, startTime, endTime, page);
        PageDTO<BigBrotherDTO> result = new PageDTO<>();
        result.setData(bigBrotherDTOIPage.getRecords());
        result.setSize(bigBrotherDTOIPage.getSize());
        result.setTotal(bigBrotherDTOIPage.getTotal());
        return result;
    }

    @Override
    public void download(Page page, int number, int streamer, String startTime, String endTime, HttpServletResponse response) throws IOException {
        int min = 0;
        if (streamer == 0) {
            min = number;
        }else {
            min = 2;
        }
        IPage<BigBrotherDTO> bigBrotherDTOIPage = userMapper.getBigBrother(min, startTime, endTime, page);
        List<Map<String, Object>> list = new ArrayList<>();
        int i = 1;
        for (BigBrotherDTO bb : bigBrotherDTOIPage.getRecords()) {
            int streamers = 0;
            if (streamer == 1) {
                streamers = userMapper.getStreamerByBigBroId(bb.getId(), startTime, endTime);
                if (streamers < number) {
                    continue;
                }
            }
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("排名", i);
            map.put("ID", String.valueOf(bb.getId()));
            map.put("抖音号", bb.getDisplayId());
            map.put("昵称", bb.getNickname());
            map.put("性别", "男");
            map.put("生日", bb.getBirthday() == null ? "":bb.getBirthday().split(" ")[0]);
            map.put("省份", bb.getProvince());
            map.put("城市", bb.getCity());
            map.put("等级", bb.getLevel());
            map.put("是否私密", bb.getSecret()?"是":"否");
            if (streamer == 1) {
                map.put("打赏主播数", streamers);
            }else {
                map.put("送礼直播间数", bb.getRoomNumber());
            }
            map.put("粉丝数", bb.getFollowerCount());
            map.put("总音浪数", bb.getTotalScor());
            map.put("单场最高音浪", bb.getMaxScor());
            map.put("最后一次打赏时间", bb.getLastPayTime() == null?
                    "":DateUtil.localDateTimeFormatyMdHms(bb.getLastPayTime()));
            list.add(map);
            i++;
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public boolean updateLastPayTime(Long userId, LocalDateTime time) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getLastPayTime, time).eq(User::getId, userId);
        return update(wrapper);
    }
}
