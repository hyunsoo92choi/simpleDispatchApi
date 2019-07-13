package com.hschoi.homework.app.call.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * com.hschoi.homework.app.call.dto_RequestDto.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String address;
}
