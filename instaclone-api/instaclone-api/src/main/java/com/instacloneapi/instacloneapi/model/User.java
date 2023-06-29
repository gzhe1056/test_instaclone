package com.instacloneapi.instacloneapi.model;

import com.instacloneapi.instacloneapi.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;
    private String password;

    @JdbcTypeCode(SqlTypes.JSON)
//    @Embedded
    @ElementCollection
    private Set<UserDto> follower = new HashSet<>();

//    @Embedded
    @ElementCollection
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<UserDto> following = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Story> stories = new ArrayList<>();

    @ManyToMany
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Post> savedPost = new HashSet<>();

}
