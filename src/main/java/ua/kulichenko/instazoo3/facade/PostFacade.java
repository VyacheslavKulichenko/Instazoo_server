package ua.kulichenko.instazoo3.facade;

import org.springframework.stereotype.Component;
import ua.kulichenko.instazoo3.dto.PostDTO;
import ua.kulichenko.instazoo3.entity.Post;

@Component
public class PostFacade {

    public PostDTO postToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setUsername(post.getUserinsta().getUsername());
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setLakes(post.getLakes());
        postDTO.setUsersLiked(post.getLikedUsers());
        postDTO.setLocation(post.getLocation());
        postDTO.setTitle(post.getTitle());

        return postDTO;


    }
}
