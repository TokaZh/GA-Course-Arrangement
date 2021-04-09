package com.tokaku.studemo.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Teacher extends User {
    private String tno;
    private String tname;
    private String password;
    private String manager;
    private String cid;

    @Override
    public String getName() {
        return tname;
    }
}
