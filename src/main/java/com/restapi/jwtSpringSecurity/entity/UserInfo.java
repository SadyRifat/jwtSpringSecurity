package com.restapi.jwtSpringSecurity.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(	name = "user", schema = "jwt_spring_security_db",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email")
		})
public class UserInfo implements UserDetails {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "username", length = 20, nullable = false)
	private String username;

	@Email
	@Column(name = "email", length = 200, nullable = false)
	private String email;

	@Column(name = "password", length = 200, nullable = false)
	private String password;

	@Column(name = "full_name", length = 200)
	private String fullName;

	@Column(name = "enabled")
	private boolean enabled = false;
	@Column(name = "account_expired_status")
	private boolean accountNonExpired = true;
	@Column(name = "account_locked_status")
	private boolean accountNonLocked = true;
	@Column(name = "credentials_expired_status")
	private boolean credentialsNonExpired = true;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}
}
