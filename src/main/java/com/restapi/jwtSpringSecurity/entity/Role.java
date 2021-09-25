package com.restapi.jwtSpringSecurity.entity;

import com.restapi.jwtSpringSecurity.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role", schema = "credential")
public class Role {
	@Id
	@Column(name = "id")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private ERole name;
}
