package com.tokaku.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClassRoom {
    private int roomId;
    private int size;
    private int type;
}
