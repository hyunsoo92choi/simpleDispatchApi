package com.hschoi.homework.app.call.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Query("SELECT 	A " 						+
		   "FROM   	Call 	   	A " 			+
		   "JOIN    FETCH 		A.passenger"	 )
    List<Call> findAllBy(Pageable pageable);
}
