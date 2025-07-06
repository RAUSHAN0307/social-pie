package com.rk.social_media.repo;

import com.rk.social_media.entity.Reels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelsRepo extends JpaRepository<Reels , Integer> {

    List<Reels> findByUserId(Integer userId);

    List<Reels> findByIsDeletedFalse();

}
