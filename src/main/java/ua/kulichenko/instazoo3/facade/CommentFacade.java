package ua.kulichenko.instazoo3.facade;

import org.springframework.stereotype.Component;
import ua.kulichenko.instazoo3.dto.CommentDTO;
import ua.kulichenko.instazoo3.entity.Comment;

@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(commentDTO.getUsername());
        commentDTO.setMessage(commentDTO.getMessage());

        return commentDTO;
    }
}
