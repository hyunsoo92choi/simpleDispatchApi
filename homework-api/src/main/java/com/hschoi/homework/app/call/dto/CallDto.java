package com.hschoi.homework.app.call.dto;

import java.util.Date;

import com.hschoi.common.code.CallStatusType;
import com.hschoi.homework.app.call.entity.Call;
import com.hschoi.homework.app.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * com.hschoi.homework.app.call_CallDto.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallDto {
	
	private Long id;
    private User passenger;
    private User driver;
    private String address;
    private CallStatusType callStatusType;
    private Date requestedAt;
    private Date assignedAt;
    private Date completeAt;
    private Date cancledAt;
    
}
