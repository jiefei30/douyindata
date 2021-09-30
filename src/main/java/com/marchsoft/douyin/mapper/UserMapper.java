package com.marchsoft.douyin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marchsoft.douyin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.marchsoft.douyin.entity.dto.BigBrotherDTO;
import com.marchsoft.douyin.entity.dto.PageDTO;
import com.marchsoft.douyin.entity.dto.SecUserDTO;
import com.marchsoft.douyin.entity.dto.UserScorDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wangmingcan
 * @since 2021-08-23
 */
public interface UserMapper extends BaseMapper<User> {

    @Result(column = "room_id", property = "roomId")
    @Select(" SELECT room_id,scor " +
            " FROM rooms_users " +
            " WHERE user_id = #{userId}")
    List<UserScorDTO> getScor(Long userId);

    @Select("SELECT u.id,u.sec_uid " +
            " FROM `rooms_users` AS ru " +
            " LEFT JOIN `user` AS u " +
            " ON  ru.user_id = u.id " +
            " WHERE u.total_favorited = 0 " +
            " GROUP BY ru.user_id " +
            " HAVING count(ru.user_id) > 1 " +
            " ORDER BY u.`level` DESC " +
            " LIMIT 1000")
    List<SecUserDTO> getUnUpdateBigBrother();

    @Select("SELECT u.id,u.display_id,u.nickname,u.gender,u.`level`,u.follower_count, u.birthday, u.province, u.city, u.last_pay_time, u.is_secret," +
            "COUNT(ru.user_id) AS room_number," +
            "SUM(ru.scor) AS total_scor," +
            "MAX(ru.scor) AS max_scor " +
            " FROM `rooms_users` AS ru " +
            " LEFT JOIN `user` AS u " +
            " ON  ru.user_id = u.id " +
            " WHERE ru.update_time BETWEEN #{start} AND #{end} " +
            " GROUP BY ru.user_id " +
            " HAVING count(ru.user_id) >= #{number} " +
            " ORDER BY u.`level` DESC")
    @Results({
            @Result(column = "sec_uid", property = "secUid"),
            @Result(column = "display_id", property = "displayId"),
            @Result(column = "school_name", property = "schoolName"),
            @Result(column = "follower_count", property = "followerCount"),
            @Result(column = "room_number", property = "roomNumber"),
            @Result(column = "total_scor", property = "totalScor"),
            @Result(column = "max_scor", property = "maxScor"),
            @Result(column = "is_secret", property = "secret"),
            @Result(column = "last_pay_time", property = "lastPayTime")
    })
    IPage<BigBrotherDTO> getBigBrother(@Param("number") int number,
                                       @Param("start")String start,
                                       @Param("end")String end, Page page);

    @Select("SELECT COUNT(1) " +
            "FROM " +
            "(SElECT COUNT(1) " +
            "FROM room " +
            "WHERE room_id " +
            "IN " +
            "(SELECT room_id " +
            "FROM rooms_users " +
            "WHERE user_id = #{userId} " +
            "AND update_time BETWEEN #{start} AND #{end}) " +
            "GROUP BY user_id) gg")
    Integer getStreamerByBigBroId(@Param("userId") Long userId,
                                  @Param("start")String start,
                                  @Param("end")String end);
}
