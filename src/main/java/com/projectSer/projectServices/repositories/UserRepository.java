package com.projectSer.projectServices.repositories;

import com.projectSer.projectServices.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<user, Integer> {
    user findByEmail(String email);
}
