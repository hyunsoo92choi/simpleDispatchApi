package com.hschoi.homework.app.call.dto;

import java.util.Date;

import com.hschoi.common.code.CallStatusType;
import com.hschoi.common.code.UserType;
import com.hschoi.homework.app.call.entity.Call;

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
@NoArgsConstructor
public class CallDto {
	
	private Long id;
//	private UserType userType;

    private String address;
    private CallStatusType callStatusType;
    private Date requestedAt;
    private Date assignedAt;
    private Date completeAt;
    private Date cancledAt;
    
    public CallDto(Long id, String address, CallStatusType callStatusType
    		, Date requestedAt, Date assignedAt, Date completeAt, Date cancledAt) {
        this.id = id;
        this.address = address;
        this.callStatusType = callStatusType;
        this.requestedAt = requestedAt;
        this.assignedAt = assignedAt;
        this.completeAt = completeAt;
        this.cancledAt = cancledAt;
    }
    
    public CallDto(Long id) {
        this.id = id;
    }
    
    public CallDto(Call call) {
        
    	this.id = call.getId();
//    	this.userType = call.
//        this.passenger = call.getPassenger();
//        this.driver = call.getDriver();
        this.address = call.getAddress();
        this.requestedAt = call.getRequestedAt();
        this.assignedAt = call.getAssignedAt();
        this.completeAt = call.getCompleteAt();
        this.cancledAt = call.getCancledAt();
    }
    
}
