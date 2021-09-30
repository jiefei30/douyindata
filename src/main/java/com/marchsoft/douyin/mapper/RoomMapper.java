package com.marchsoft.douyin.mapper;

import com.marchsoft.douyin.entity.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto
 * @since 2021-08-23
 */
public interface RoomMapper extends BaseMapper<Room> {

    @Insert("INSERT INTO rooms_users(room_id,user_id,scor,create_time,update_time) " +
            " VALUE(#{roomId}, #{userId}, #{scor}, #{time}, #{time}) " +
            "ON DUPLICATE KEY UPDATE scor=VALUES(scor), update_time=VALUES(update_time)")
    void saveOrUpdateRU(Long roomId, Long userId, Integer scor, LocalDateTime time);

    @Insert("INSERT INTO rooms_users(room_id,user_id,scor,create_time,update_time) " +
            " VALUE(#{roomId}, #{userId}, #{scor}, #{time}, #{time})")
    void saveRU(Long roomId, Long userId, Integer scor, LocalDateTime time);

    @Select("SELECT scor " +
            " FROM rooms_users " +
            " WHERE room_id = #{roomId} " +
            " AND user_id = #{userId}")
    Integer getScorRU(Long roomId, Long userId);
}
