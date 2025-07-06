package com.rk.social_media.utility;

import com.rk.social_media.dto.StoryDto;
import com.rk.social_media.entity.Story;
import com.rk.social_media.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StoryMapper {

    public static StoryDto toDto(Story story) {
        StoryDto dto = new StoryDto();
        dto.setId(story.getId());
        dto.setImage(story.getImage());
        dto.setVideo(story.getVideo());
        dto.setCaptions(story.getCaptions());
        dto.setTimeStamps(story.getTimeStamps());

        if (story.getUser() != null) {
            dto.setUserId(story.getUser().getId());
        }

        List<Integer> likedUserIds = new ArrayList<>();
        if (story.getLiked() != null) {
            for (User user : story.getLiked()) {
                likedUserIds.add(user.getId());
            }
        }
        dto.setLikedUserIds(likedUserIds);


        return dto;
    }
}
