# dyprocessing

直播间数据采集持久化。

通过 `post /api/room`接口向数据库中持久化直播间的数据，例如（模拟数据）：

```json
{
	"roomId": "6999411332156422",
	"title": "大冬瓜",
	"displayId": "385213245621",
	"userId": "29953215642312",
	"userCount": "192",
	"secUid": "MS4wLjXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
	"users": [{
		"id": "74712312432",
		"secUid": "MS4wLjAXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
		"nickname": "常乐🌺️🌺️🌺️",
		"gender": "男",
		"level": "13",
		"displayId": "812312423",
		"scor": "123"
	}, {
		"id": "450123456789123",
		"secUid": "MS4wLjABAXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
		"nickname": "🌈月",
		"gender": "男",
		"level": "22",
		"displayId": "123456aa",
		"scor": "598"
	}]
}
```

之后便可通过 `get /api/user/big`接口查询打赏次数超过2个直播间/主播的用户信息，或者通过`get /api/user/download`导出**execl**。（可通过参数调整个数与时间限制。默认根据用户**打赏等级倒序**排列）

默认的持久化策略为直播间销毁时将直播间与用户数据通过`post /api/room`发送过来，该接口通过**令牌桶**进行了限流，每秒最大**QPS**为100。

- 直播间监控策略使用`redis`存储临时直播间

- 通过`rabbitmq`多个消费者处理耗时的持久化业务

- `zipkin`监控耗时对接口做进一步优化

- 并使用`shardingJDBC`做了**读写分离**（需要首先做**主从复制**）

- 随着数据量的增多，之后避免不了**分库分表**，数据量的增大肯定会导致查询分析接口处理变慢（目前总数据量在300w左右时，查询接口处理时间要好几分钟），之后会将三张表冗余为一张宽表，结合`clickhouse`加快查询分析速度

- 由于接口请求次数（主要看监控数量）和持久化时产生日志很多，之后将会采用 `flume + kafka + ELK `做一个**日志监控平台**

  

查询sql:

```java
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
```

导出方法：
```java
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
```

**该后端程序仅为采集持久化直播间用户公开信息，进一步为学习大数据技术栈储备数据，不用于任何商业途径**