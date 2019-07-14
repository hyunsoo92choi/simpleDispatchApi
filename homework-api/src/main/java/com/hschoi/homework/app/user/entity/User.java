package com.hschoi.homework.app.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hschoi.common.code.UserType;
import com.hschoi.common.exception.CustomException;
import com.hschoi.homework.app.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.hschoi.common.code.HttpStatusType.PASSWORD_CONFIRM_NOT_MATCHING;
/**
 * <pre>
 * com.hschoi.homework.app.user.entity_User.java
 * </pre>
 * 
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Entity
@Table(name = "USER")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Size(max=50)
	@Column(nullable = false, unique = true)
	private String email;
	@Size(max=100)
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Size(max=20)
	private UserType userType;
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@LastModifiedDate
	@Column(name = "update_at", nullable = false, updatable = false)
	private LocalDateTime updatedAt = LocalDateTime.now();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, UserType userType) {
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public UserDto toUserDto() {
		return new UserDto(email, password, userType);
	}

	public boolean matchPassword(String inputPassword, PasswordEncoder passwordEncoder) {

		if (!passwordEncoder.matches(inputPassword, password)) {
            throw new CustomException(PASSWORD_CONFIRM_NOT_MATCHING);
		}
		return true;
	}
}
