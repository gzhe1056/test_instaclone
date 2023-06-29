package com.instacloneapi.instacloneapi.model;

import com.instacloneapi.instacloneapi.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String image;
    private String caption;
    private String location;
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column=@Column(name="user_id")),
            @AttributeOverride(name = "email", column = @Column(name="user_email"))
    })
    private UserDto userDto;

    @OneToMany
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Comment> comments = new ArrayList<Comment>();

    @JdbcTypeCode(SqlTypes.JSON)
//    @Embedded
    @ElementCollection
    @JoinTable(name = "likedByUsers", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers = new HashSet<>();
    
}
