package com.example.DoAnAngular.Repository;


import com.example.DoAnAngular.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     User findByUserName(String username);
     boolean existsByUserName(String username);
}
