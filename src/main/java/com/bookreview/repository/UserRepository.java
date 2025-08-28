package com.bookreview.repository;

import com.bookreview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface UserRepository extends JpaRepository<User, Long> {
    Logger log = LoggerFactory.getLogger(UserRepository.class);
    // For custom queries, add log statements in the implementation if needed.
}
