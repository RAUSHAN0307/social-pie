package com.rk.social_media.utility;
import com.rk.social_media.dto.CommentDto;
import com.rk.social_media.entity.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());

        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
            dto.setUserFullName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName());
        }

        if(comment.getPost() != null){
            dto.setPostId(comment.getPost().getId());
        }
        dto.setLikeCount(comment.getLiked() != null ? comment.getLiked().size() : 0);
        dto.setLocalDateTime(comment.getLocalDateTime());
        return dto;
    }
}
