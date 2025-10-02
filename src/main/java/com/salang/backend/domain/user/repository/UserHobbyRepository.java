package com.salang.backend.domain.user.repository;

import com.salang.backend.domain.user.entity.UserHobby;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHobbyRepository extends JpaRepository<UserHobby, Long> {

    List<UserHobby> findByUserId(Long userId);

}
