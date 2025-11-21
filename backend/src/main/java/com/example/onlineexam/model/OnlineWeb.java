package com.example.onlineexam.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OnlineWeb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnlineWeb {

    @Id
    @Column(name = "W_ID", length = 255)
    private String id;

    @Column(name = "WName")
    private String name;
}
