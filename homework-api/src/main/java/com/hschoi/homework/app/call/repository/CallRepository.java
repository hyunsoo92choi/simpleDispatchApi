package com.hschoi.homework.app.call.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hschoi.homework.app.call.entity.Call;

/**
 * <pre>
 * com.hschoi.homework.app.call.repository_CallRepository.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Repository(value = "CallRepository")
public interface CallRepository extends JpaRepository<Call, Long> {

}
