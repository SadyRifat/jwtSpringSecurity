package com.restapi.jwtSpringSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_profile", schema = "credential")
public class UserProfile {
    @Id
    @Column(name = "user_id", length = 100, nullable = false)
    String userID;

    @Column(name = "first_name", length = 30, nullable = false)
    String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    String lastName;

    @Column(name = "contact", length = 20)
    String contact;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "gender", length = 15)
    String gender;

    @Column(name = "address", length = 100)
    String address;

}
