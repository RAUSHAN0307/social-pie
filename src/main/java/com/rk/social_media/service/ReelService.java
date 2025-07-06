package com.rk.social_media.service;

import com.rk.social_media.Exception.ReelException;
import com.rk.social_media.Exception.UserException;
import com.rk.social_media.entity.Reels;
import com.rk.social_media.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReelService {
    public Reels createReels(String title , MultipartFile file, User user) throws IOException;

    public List<Reels> findAllReels();

    public List<Reels> findUsersReel(Integer userId) throws ReelException, UserException;

    Reels likeReel(Integer reelId, Integer userId) throws ReelException, UserException;

    String deleteReel(Integer reelId, Integer userId) throws ReelException;
}
