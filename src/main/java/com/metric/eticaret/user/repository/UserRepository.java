package com.metric.eticaret.user.repository;


import com.metric.eticaret.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User existsByUsername(String username);

    User existsByEmail(String email);
}
