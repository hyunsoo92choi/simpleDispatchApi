package com.hschoi.homework.app.user.dto;

import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hschoi.common.code.UserType;
import com.hschoi.homework.app.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * com.hschoi.homework.app.user_UserDto.java
 * </pre>
 * @date : 2019. 7. 11.
 * @author : hychoi
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
private Long id;
	
	@NotNull
    @Size(min = 5, max = 20)
	private String email;
	
	@NotNull
    @Size(min = 5, max = 20)
	private String password;
	
	@Enumerated(EnumType.STRING)
    private UserType userType;
	
	public UserDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
	
	public UserDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public UserDto(String email, String password, UserType userType) {
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	public User toEntity(PasswordEncoder passwordEncoder) {
		return new User(email, passwordEncoder.encode(password), userType);
	}
}
