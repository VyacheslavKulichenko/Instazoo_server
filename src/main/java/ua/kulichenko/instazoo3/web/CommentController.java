package ua.kulichenko.instazoo3.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kulichenko.instazoo3.dto.CommentDTO;
import ua.kulichenko.instazoo3.entity.Comment;
import ua.kulichenko.instazoo3.facade.CommentFacade;
import ua.kulichenko.instazoo3.facade.PostFacade;
import ua.kulichenko.instazoo3.payload.response.MessageResponse;
import ua.kulichenko.instazoo3.services.CommentServise;
import ua.kulichenko.instazoo3.validations.ResponsErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentServise commentServise;
    @Autowired
    private CommentFacade commentFacade;
    @Autowired
    private ResponsErrorValidation responsErrorValidation;

    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO,
                                                @PathVariable("postId") String postId,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responsErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return  errors;

        Comment comment = commentServise.saveComment(Long.parseLong(postId), commentDTO, principal);
        CommentDTO createdComment = commentFacade.commentToCommentDTO(comment);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId) {
      List<CommentDTO> commentDTOList = commentServise.getAllCommentsForPost(Long.parseLong(postId))
              .stream()
              .map(commentFacade::commentToCommentDTO)
              .collect(Collectors.toList());
      return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId) {
        commentServise.deleteComment(Long.parseLong(commentId));
        return new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}
