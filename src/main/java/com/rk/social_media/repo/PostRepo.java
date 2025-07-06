package com.rk.social_media.repo;

import com.rk.social_media.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    // aise post ko select jiska user ka id is user id se match kar raha ho
    @Query("select p from Post p where p.user.id =:id")
    List<Post> findPostByUserId(Integer id);
}
