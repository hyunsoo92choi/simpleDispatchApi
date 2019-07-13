package com.hschoi.homework.app.call.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.hschoi.common.code.CallStatusType;
import com.hschoi.homework.app.call.dto.CallDto;
import com.hschoi.homework.app.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * com.hschoi.homework.app.call.entity_Call.java
 * </pre>
 * 
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Entity
@Table(name = "CALL")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Call {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "passenger_id", nullable = false)
	private User passenger;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private User driver;

	private String address;

	@Enumerated(EnumType.STRING)
	private CallStatusType callStatusType;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "requested_at", updatable = false)
	private Date requestedAt;
	
	@Column(name = "assigned_at", updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date assignedAt;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "completed_at", updatable = false)
	private Date completeAt;
	
	@Column(name = "cancled_at", updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date cancledAt;
	
	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@LastModifiedDate
	@Column(name = "update_at", nullable = false)
	private LocalDateTime updatedAt = LocalDateTime.now();
	
	public CallDto toCallDto() {
		return new CallDto(id, address,callStatusType, requestedAt, assignedAt, completeAt, cancledAt);
	}
	
	public CallDto toCallDto(Call call) {
    
	if (call == null) {
        return null;
    }

    return CallDto.builder()
    		.id(call.getId())
//    		.passenger(call.getPassenger())
//    		.driver(call.getDriver())
    		.address(call.getAddress())
    		.callStatusType(call.getCallStatusType())
    		.requestedAt(call.getRequestedAt())
    		.assignedAt(call.getAssignedAt())
    		.completeAt(call.getCompleteAt())
    		.cancledAt(call.getCancledAt())        		
    		.build();
}
}
