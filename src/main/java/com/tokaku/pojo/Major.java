package com.tokaku.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Major {
    private String majorId;//2位长
    private String majorName;
//    int DepartmentId;
}
