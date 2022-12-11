package ua.kulichenko.instazoo3.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kulichenko.instazoo3.dto.CommentDTO;
import ua.kulichenko.instazoo3.entity.Comment;
import ua.kulichenko.instazoo3.entity.Post;
import ua.kulichenko.instazoo3.entity.Userinsta;
import ua.kulichenko.instazoo3.exeption.PostNotFoundExeption;
import ua.kulichenko.instazoo3.repository.CommentRepository;
import ua.kulichenko.instazoo3.repository.PostRepository;
import ua.kulichenko.instazoo3.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServise {

    public static final Logger LOG = LoggerFactory.getLogger(CommentServise.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServise(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal) {
       Userinsta userinsta = getUserinstaByPrincipal(principal);
       Post post = postRepository.findById(postId)
               .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found for username: " + userinsta.getEmail()));
       Comment comment = new Comment();
       comment.setPost(post);
       comment.setUserId(userinsta.getId());
       comment.setUserName(userinsta.getUsername());
       comment.setMassage(commentDTO.getMessage());

       LOG.info("Saving comment for Post: {}" + post.getId());
       return commentRepository.save(comment);
    }
     public List<Comment> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found"));
     List<Comment> comments = commentRepository.findAllByPost(post);
     return comments;
    }

    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    private Userinsta getUserinstaByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserinstaByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }
}
