package com.info.prescription.repository;

import com.info.prescription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

//    void delete(Optional<User> user);
}
