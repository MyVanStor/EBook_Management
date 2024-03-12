package com.example.EBook_Management_BE.common.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
public class User extends BaseEntity implements UserDetails, OAuth2User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;

	@Column(name = "fullname", length = 100)
	String fullname;

	@Column(name = "password", length = 20, nullable = false)
	String password;

	@Column(name = "link_avatar", length = 255, unique = true)
	String linkAvatar;

	@Column(name = "phone_number", length = 10, unique = true)
	String phoneNumber;

	@Column(name = "gender", columnDefinition = "TINYINT(1)")
	short gender;

	@Column(name = "budget", columnDefinition = "FLOAT", nullable = false)
	double budget;
	
	@Column(name = "date_of_birth", columnDefinition = "DATE")
	Date dateOfBirth;

	@Column(name = "facebook_account_id", length = 100, unique = true)
	int facebookAccountId;

	@Column(name = "google_account_id", length = 100, unique = true)
	int googleAccountId;
	
	@Column(name = "is_active", columnDefinition = "TINYINT(1)")
	short isActive;
	
	@OneToMany(mappedBy = "user")
	Set<UserBook> userBooks;
	
	@OneToMany(mappedBy = "user")
	Set<Rating> ratings;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Comment> comments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<Follow> follows;
	
	@OneToMany(mappedBy = "user")
	Set<Order> orders;
	
	@OneToMany(mappedBy = "user")
	Set<SocialAccount> socialAccounts;
	
	@OneToMany(mappedBy = "user")
	Set<Token> tokens;
	
	@JsonIgnore
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

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<String, Object>();
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}
}
