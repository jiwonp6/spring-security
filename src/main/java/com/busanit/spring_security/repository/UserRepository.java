package com.busanit.spring_security.repository;

import com.busanit.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자이름으로 User를 찾는 쿼리 메서드
    User findByUsername(String username);
}
