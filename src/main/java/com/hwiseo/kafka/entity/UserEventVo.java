package com.hwiseo.kafka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserEventVo {

    @Id @GeneratedValue
    private Long id;
    private String timestamp;
    private String userAgent;
    private String colorName;
    private String userName;

    public UserEventVo() {

    }

    public UserEventVo(String timestamp, String userAgent, String colorName, String userName) {
        this.timestamp = timestamp;
        this.userAgent = userAgent;
        this.colorName = colorName;
        this.userName = userName;
    }

    public UserEventVo(Long id, String timestamp, String userAgent, String colorName, String userName) {
        this.id = id;
        this.timestamp = timestamp;
        this.userAgent = userAgent;
        this.colorName = colorName;
        this.userName = userName;
    }
}
