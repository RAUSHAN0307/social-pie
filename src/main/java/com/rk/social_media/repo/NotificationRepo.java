package com.rk.social_media.repo;

import com.rk.social_media.entity.Notification;
import com.rk.social_media.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :userId ORDER BY n.timestamp DESC")
    List<Notification> getNotificationsForUser(@Param("userId") Integer userId);

    @Modifying
    @Query("DELETE FROM Notification n WHERE n.sender = :sender AND n.recipient = :recipient AND n.type = :type AND n.referenceId = :referenceId")
    void removeExistingNotification(@Param("sender") User sender,
                                    @Param("recipient") User recipient,
                                    @Param("type") String type,
                                    @Param("referenceId") Integer referenceId);

}
// update or delete query should be written with modifying