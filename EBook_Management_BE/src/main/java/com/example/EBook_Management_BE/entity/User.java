package com.example.EBook_Management_BE.entity;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.EBook_Management_BE.listeners.UserListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@EntityListeners(UserListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fullname", length = 100)
	private String fullname;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "link_avatar", length = 255, unique = true)
	private String linkAvatar;

	@Column(name = "phone_number", length = 10, unique = true)
	private String phoneNumber;

	@Column(name = "gender")
	private short gender;

	@Column(name = "budget", columnDefinition = "FLOAT", nullable = false)
	private double budget;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "is_active")
	private short isActive;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<UserBook> userBooks;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Rating> ratings;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Follow> follows;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Token> tokens;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<ReadingHistory> readingHistories;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRole().getName().toUpperCase()));

		return authorityList;
	}

	@Override
	public String getUsername() {
		return phoneNumber;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
