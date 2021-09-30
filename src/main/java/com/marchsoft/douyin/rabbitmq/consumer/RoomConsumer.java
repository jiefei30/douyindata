package com.marchsoft.douyin.rabbitmq.consumer;

import com.marchsoft.douyin.entity.Room;
import com.marchsoft.douyin.entity.User;
import com.marchsoft.douyin.mapper.RoomMapper;
import com.marchsoft.douyin.service.IRoomService;
import com.marchsoft.douyin.service.IUserService;
import com.marchsoft.douyin.service.impl.RoomServiceImpl;
import com.marchsoft.douyin.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Wangmingcan
 * @date 2021/9/28 12:04
 * @description
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queuesToDeclare = @Queue("room"))
public class RoomConsumer {

    private final IRoomService roomService;
    private final IUserService userService;
    private final RoomMapper roomMapper;
    private final RedisUtils redisUtils;

    /**
     * 消费者1
     */
    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive1(Room room){
            save(room, "1");
    }

    /**
     * 消费者2
     */
    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive2(Room room){
        save(room, "2");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive3(Room room){
        save(room, "3");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive4(Room room){
        save(room, "4");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive5(Room room){
        save(room, "5");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive6(Room room){
        save(room, "6");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive7(Room room){
        save(room, "7");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive8(Room room){
        save(room, "8");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive9(Room room){
        save(room, "9");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive10(Room room){
        save(room, "10");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive11(Room room){
        save(room, "11");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive12(Room room){
        save(room, "12");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive13(Room room){
        save(room, "13");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive14(Room room){
        save(room, "14");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive15(Room room){
        save(room, "15");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive16(Room room){
        save(room, "16");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive17(Room room){
        save(room, "17");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive18(Room room){
        save(room, "18");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive19(Room room){
        save(room, "19");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive20(Room room){
        save(room, "20");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive21(Room room){
        save(room, "21");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive22(Room room){
        save(room, "22");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive23(Room room){
        save(room, "23");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive24(Room room){
        save(room, "24");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive25(Room room){
        save(room, "25");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive26(Room room){
        save(room, "26");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive27(Room room){
        save(room, "27");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive28(Room room){
        save(room, "28");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive29(Room room){
        save(room, "29");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive30(Room room){
        save(room, "30");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive31(Room room){
        save(room, "31");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive32(Room room){
        save(room, "32");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive33(Room room){
        save(room, "33");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive34(Room room){
        save(room, "34");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive35(Room room){
        save(room, "35");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive36(Room room){
        save(room, "36");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive37(Room room){
        save(room, "37");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive38(Room room){
        save(room, "38");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive39(Room room){
        save(room, "39");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive40(Room room){
        save(room, "40");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive41(Room room){
        save(room, "41");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive42(Room room){
        save(room, "42");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive43(Room room){
        save(room, "43");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive44(Room room){
        save(room, "44");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive45(Room room){
        save(room, "45");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive46(Room room){
        save(room, "46");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive47(Room room){
        save(room, "47");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive48(Room room){
        save(room, "48");
    }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive49(Room room) { save(room, "49"); }

    @RabbitListener(queuesToDeclare = @Queue("room"))
    public void receive50(Room room){
        save(room, "50");
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Room room, String consumer) {
        try{
            if (!redisUtils.set(RoomServiceImpl.ROOM_SPACE + room.getRoomId(),
                    room, 60*60, TimeUnit.SECONDS)) {
                log.error("roomId = " + room.getRoomId() + "插入缓存失败");
            }
            List<User> users = room.getUsers();
            if (!roomService.saveOrUpdate(room)) {
                log.error("roomId = " + room.getRoomId() + "插入/更新数据库失败");
            }
            if (!userService.saveOrUpdateBatch(users)) {
                log.error("roomId = " + room.getRoomId() + "的观众数据插入/更新失败");
            }
            LocalDateTime time = LocalDateTime.now();
            // TODO 第一版
//            for (User user : users) {
//                roomMapper.saveOrUpdateRU(room.getRoomId(), user.getId(), user.getScor(), time);
//            }
            //TODO 第二次修改
//          for (User user : users) {
//              Integer scor = roomMapper.getScorRU(room.getRoomId(), user.getId());
//              if (scor != null && user.getScor() != null) {
//                  //打赏没有变化
//                  if (scor.intValue() == user.getScor().intValue()) {
//                      continue;
//                  }
//              }
//              roomMapper.saveOrUpdateRU(room.getRoomId(), user.getId(), user.getScor(), time);
//              userService.updateLastPayTime(user.getId(), time);
//          }
            // TODO 第三次修改
            for (User user : users) {
                roomMapper.saveRU(room.getRoomId(), user.getId(), user.getScor(), time);
                userService.updateLastPayTime(user.getId(), time);
            }
            log.info("【RabbitMQ】消费者" +consumer+"：roomId = " + room.getRoomId() + "持久化结束");
        }catch (Exception e) {
            log.error("【RabbitMQ】消费通知消息异常，Message : " +
                    room.toString() + "，ERROR : " + e.getMessage()+ " " +e.toString());
        }
    }

}
