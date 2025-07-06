package com.rk.social_media.utility;

import com.rk.social_media.dto.ReelsDto;
import com.rk.social_media.entity.Reels;
import com.rk.social_media.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ReelsMapper {

    public static ReelsDto toDto(Reels reel) {
        ReelsDto dto = new ReelsDto();
        dto.setId(reel.getId());
        dto.setTitle(reel.getTitle());
        dto.setVideo(reel.getVideo());
        dto.setUserId(reel.getUser().getId());
        dto.setLikeCount(reel.getLiked() != null ? reel.getLiked().size() : 0);
        return dto;
    }

}
