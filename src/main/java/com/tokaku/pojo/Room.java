package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Room {
    private String roomId;
    private int type;
}
