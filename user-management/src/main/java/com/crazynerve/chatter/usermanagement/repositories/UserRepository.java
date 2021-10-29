package com.crazynerve.chatter.usermanagement.repositories;

import com.crazynerve.chatter.usermanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, String>
{
    Optional<UserEntity> findByEmailAddress(String emailAddress);
}
