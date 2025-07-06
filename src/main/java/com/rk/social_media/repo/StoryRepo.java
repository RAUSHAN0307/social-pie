package com.rk.social_media.repo;

import com.rk.social_media.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoryRepo extends JpaRepository<Story , Integer> {

    List<Story> findByUserId(Integer id);

    List<Story> findByTimeStampsBeforeAndIsDeletedFalse(LocalDateTime time);
}
