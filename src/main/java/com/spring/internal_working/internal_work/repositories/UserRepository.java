package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    @EntityGraph(attributePaths = {"tags","addresses"})
//   Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "addresses")
    @Query("select u from User u")
    List<User> findAllWithAddresses();

    boolean existsByEmail(String email);

    // @Query("select u.id as id, u.email as email from User u where u.profile.loyaltyPoints > :loyaltyPoints order by u.email")
   // List<UserSummary> findByLoyaltyPoints(@Param("loyaltyPoints") int loyaltyPoints);
}
