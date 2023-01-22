package com.nk.agri.store.controller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity     // POJO -> Represent the table in the DB using the JPA
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_password", length = 4)
    private String password;
    private String gender;
    @Column(length = 500)
    private String about;

    @Column(name = "user_image_name")
    private String imageName;

}
