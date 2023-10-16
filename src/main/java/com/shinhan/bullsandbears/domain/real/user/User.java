package com.shinhan.bullsandbears.domain.real.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String email;
    private String name;
    private LocalDateTime createdAt;
}
