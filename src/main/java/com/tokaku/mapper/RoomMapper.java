package com.tokaku.mapper;

import com.tokaku.pojo.ClassRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {
    int addRoom();

    int updateRoom();

    int deleteRoom();

    List<ClassRoom> selectRoomList();
}
