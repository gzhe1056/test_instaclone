package com.instacloneapi.instacloneapi.model;

import com.instacloneapi.instacloneapi.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email", column = @Column(name = "user_email"))
    })
    private UserDto userDto;

    private String content;

//    @Embedded
    @ElementCollection
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<UserDto> likedByUsers = new HashSet<UserDto>();

    private LocalDateTime createdAt;
}