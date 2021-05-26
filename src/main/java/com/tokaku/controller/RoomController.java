package com.tokaku.controller;

import com.tokaku.pojo.Room;
import com.tokaku.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class RoomController {
    RoomService roomService;

    @Autowired
    public void RoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping("room")
    public String room(Model model) {
        List<Room> roomList = roomService.selectRoomList();
        model.addAttribute("roomList", roomList);
        return "room/room";
    }

    @PostMapping("/room/saveRoom")
    public String saveRoom(Room room, HttpServletResponse response) {
        if (roomService.addRoom(room) == 1)
            return "redirect:/room";
        else {
            try {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script type='text/javascript'>alert('教室重复！！');window.history.back()</script>");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/room";
        }
    }

    //删除课程
    @GetMapping("/room/delete/{roomId}")
    public String deleteCourse(@PathVariable("roomId") String roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/room";
    }

}
