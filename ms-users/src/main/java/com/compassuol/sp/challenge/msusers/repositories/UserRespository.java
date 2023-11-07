package com.compassuol.sp.challenge.msusers.repositories;

import com.compassuol.sp.challenge.msusers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User, Long> {
}
