package com.example.library.user.entity;

import com.example.library.common.entity.TimeStamped;
import com.example.library.common.entity.UserRoleEnum;
import com.example.library.rental.entity.Rental;
import com.example.library.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Setter
    @Column(name = "user_rental_able")
    private Boolean userRentalAble;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Rental> rentalList  = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum role, Boolean userRentalAble) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.userRentalAble = userRentalAble;
    }

    public void update(UserRequestDto userRequestDto) {
        this.email = userRequestDto.getEmail();
        this.userRentalAble = userRequestDto.getUserRentalAble();
    }
}
