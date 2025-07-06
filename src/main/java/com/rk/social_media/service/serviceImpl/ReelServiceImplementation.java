package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.Exception.ReelException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Reels;
import com.rk.social_media.entity.User;
import com.rk.social_media.repo.ReelsRepo;
import com.rk.social_media.service.NotificationService;
import com.rk.social_media.service.ReelService;
import com.rk.social_media.service.UploadToCloudService;
import com.rk.social_media.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReelServiceImplementation implements ReelService {

    @Autowired
    ReelsRepo reelsRepo;

    @Autowired
    UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UploadToCloudService uploadToCloudService;
    @Override
    public Reels createReels(String title , MultipartFile file, User user) throws IOException {

        String reelUrl = null;
        if(file != null && !file.isEmpty()){
            reelUrl = uploadToCloudService.uploadVideo(file);
        }
        Reels createReel = new Reels();
        createReel.setTitle(title);
        createReel.setUser(user);
        createReel.setVideo(reelUrl);

        createReel.setLiked(new ArrayList<>()); // Initialize empty like list
        createReel.setDeleted(false); // Not deleted on creation

        return reelsRepo.save(createReel);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelsRepo.findByIsDeletedFalse();
    }


    @Override
    public List<Reels> findUsersReel(Integer userId) throws ReelException, UserException {
        userService.findUserById(userId); // for checkin if user not present then throw the exception
        return reelsRepo.findByUserId(userId);
    }

    @Override
    public Reels likeReel(Integer reelId, Integer userId) throws ReelException, UserException {
        Reels reel = reelsRepo.findById(reelId)
                .orElseThrow(() -> new ReelException("Reel not found"));

        User user = userService.findUserById(userId);
        boolean isLiked = false;
        if (reel.getLiked().contains(user)) {
            reel.getLiked().remove(user); // unlike
        } else {
            reel.getLiked().add(user);
            isLiked = true;
        }

        if(isLiked && !reel.getUser().getId().equals(userId)){
            String message = user.getFirstName() + " liked your reel";
            notificationService.sendNotification(
                    user,
                    reel.getUser(),
                    message,
                    "LIKE_REEL",
                    reel.getId()
            );
        }


        return reelsRepo.save(reel);
    }

    @Override
    public String deleteReel(Integer reelId, Integer userId) throws ReelException {
        Reels reel = reelsRepo.findById(reelId)
                .orElseThrow(() -> new ReelException("Reel not found"));

        if (!reel.getUser().getId().equals(userId)) {
            throw new ReelException("Unauthorized to delete this reel");
        }

        reel.setDeleted(true);
        reelsRepo.save(reel);

        return "Reel deleted successfully";
    }


}
