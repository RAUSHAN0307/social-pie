package com.rk.social_media.repo;

import com.rk.social_media.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    // why i have written this because there is no any method in the
    // jpa repo for find by email so you can also make your own method
    public User findByEmail(String email);

    // this is for the search user we will use query here because
    // user can search by first name last name etc

    @Query("select u from User u where u.firstName LIKE %:query% OR u.lastName LIKE %:query% or u.email like %:query%")
    public List<User> searchUser(@Param("query") String query);
}
