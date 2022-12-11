package ua.kulichenko.instazoo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kulichenko.instazoo3.entity.Comment;
import ua.kulichenko.instazoo3.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //найдём комментарии по посту
    List<Comment> findAllByPost(Post post);
    Comment findByIdAndUserId(Long commentId, Long userId);

}
