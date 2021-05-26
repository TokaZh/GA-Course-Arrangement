package com.tokaku.service.impl;

import com.tokaku.mapper.RoomMapper;
import com.tokaku.pojo.Room;
import com.tokaku.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    RoomMapper roomMapper;

    @Autowired
    public void RoomMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @Override
    public int addRoom(Room room) {
        if (roomMapper.selectRoomById(room.getRoomId()) == null) {
            roomMapper.addRoom(room);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateRoom(Room room) {
        return roomMapper.updateRoom(room);
    }

    @Override
    public List<Room> selectRoomList() {
        return roomMapper.selectRoomList();
    }

    @Override
    public int deleteRoom(String roomId) {
        return roomMapper.deleteRoom(roomId);
    }
}
