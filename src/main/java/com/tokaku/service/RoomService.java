package com.tokaku.service;

import com.tokaku.pojo.Room;

import java.util.List;

public interface RoomService {
    int addRoom(Room room);

    int updateRoom(Room room);

    List<Room> selectRoomList();

    int deleteRoom(String roomId);
}
