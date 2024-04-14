package com.example.EBook_Management_BE.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;

	@Column(name = "fullname", length = 100)
	String fullname;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "link_avatar", length = 255, unique = true)
	String linkAvatar;

	@Column(name = "phone_number", length = 10, unique = true)
	String phoneNumber;

	@Column(name = "gender")
	short gender;

	@Column(name = "budget", columnDefinition = "FLOAT", nullable = false)
	double budget;
	
	@Column(name = "date_of_birth", columnDefinition = "DATE")
	Date dateOfBirth;

	@Column(name = "facebook_account_id", length = 100, unique = true)
	int facebookAccountId;

	@Column(name = "google_account_id", length = 100, unique = true)
	int googleAccountId;
	
	@Column(name = "is_active")
	short isActive;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<UserBook> userBooks;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Rating> ratings;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Follow> follows;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<SocialAccount> socialAccounts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Token> tokens;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<ReadingHistory> readingHistories;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Painter> painters;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Author> authors;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;

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
