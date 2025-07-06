package com.rk.social_media.utility;

import com.rk.social_media.dto.PostDto;
import com.rk.social_media.dto.ReelsDto;
import com.rk.social_media.dto.UserDto;
import com.rk.social_media.dto.UserProfileDto;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.Reels;
import com.rk.social_media.entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserMapper {
    public static UserDto convertToDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePic(),
                user.getGender(),
                user.getEmail()
        );
    }
    public static UserProfileDto toUserProfile(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setGender(user.getGender());
        dto.setProfilePic(user.getProfilePic());

        List<PostDto> postDtos = new ArrayList<>();
        if (user.getPosts() != null) {
            for (Post post : user.getPosts()) {
                postDtos.add(PostMapper.toDto(post)); // use already mapped safe DTOs
            }
        }

        List<ReelsDto> reelDtos = new ArrayList<>();
        if (user.getReels() != null) {
            for (Reels reel : user.getReels()) {
                if (!reel.getIsDeleted()) {
                    ReelsDto dtoReel = ReelsMapper.toDto(reel);
                    reelDtos.add(dtoReel);
                }
            }
        }

        dto.setPosts(postDtos);
        dto.setReels(reelDtos);
        return dto;
    }




}
