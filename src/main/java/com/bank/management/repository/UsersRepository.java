package com.bank.management.repository;

import com.bank.management.entity.Users;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Boolean existsByDni(String dni);

}
