package com.rk.social_media.utility;

import com.rk.social_media.dto.PostDto;
import com.rk.social_media.entity.Post;
import com.rk.social_media.entity.User;
import com.rk.social_media.response.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    public static PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setCaption(post.getCaption());
        dto.setImage(post.getImage());
        dto.setVideo(post.getVideo());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUser(toUserDto(post.getUser()));

        List<UserResponseDto> likedDtos = new ArrayList<>();
        if (post.getLiked() != null) {
            for (User user : post.getLiked()) {
                likedDtos.add(toUserDto(user));
            }
        }
        dto.setLiked(likedDtos);

        return dto;
    }

    public static UserResponseDto toUserDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
