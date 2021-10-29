package com.crazynerve.chatter.ipblockerservice.repositories;

import com.crazynerve.chatter.ipblockerservice.entities.BlackListedIPAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BlackListIPAddressRepository extends JpaRepository<BlackListedIPAddressEntity, String>
{
    Optional<BlackListedIPAddressEntity> findByIpAddress(String ipAddress);
}
