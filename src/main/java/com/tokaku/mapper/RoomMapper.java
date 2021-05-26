package com.tokaku.mapper;

import com.tokaku.pojo.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {
    int addRoom(Room room);

    int updateRoom(Room room);

    List<Room> selectRoomList();

    Room selectRoomById(String roomId);

    int deleteRoom(String roomId);
}
