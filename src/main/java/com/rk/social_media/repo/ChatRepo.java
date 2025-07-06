package com.rk.social_media.repo;

import com.rk.social_media.entity.Chat;
import com.rk.social_media.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {

    List <Chat> findByUsersId(Integer userId);

    @Query("select c from Chat c where :user member of c.users AND :reqUser member of c.users")
    Chat findChatByUserId(@Param("user") User user, @Param("reqUser") User reqUser);

}
