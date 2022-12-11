package ua.kulichenko.instazoo3.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kulichenko.instazoo3.dto.PostDTO;
import ua.kulichenko.instazoo3.entity.ImageModel;
import ua.kulichenko.instazoo3.entity.Post;
import ua.kulichenko.instazoo3.entity.Userinsta;
import ua.kulichenko.instazoo3.exeption.PostNotFoundExeption;
import ua.kulichenko.instazoo3.repository.ImageRepository;
import ua.kulichenko.instazoo3.repository.PostRepository;
import ua.kulichenko.instazoo3.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        Userinsta userinsta = getUserinstaByPrincipal(principal);
        Post post = new Post();
        post.setUserinsta(userinsta);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLakes(0);

        LOG.info("Saving Post for User: ", userinsta.getEmail());
        return postRepository.save(post);

    }

    public List<Post> getAllPosts() {

        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {

        Userinsta userinsta = getUserinstaByPrincipal(principal);
        return postRepository.findPostByIdAndUserinsta(postId, userinsta)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found for username: " + userinsta.getEmail()));

    }

    public List<Post> getAllPostForUser(Principal principal) {
        Userinsta userinsta = getUserinstaByPrincipal(principal);
        return postRepository.findAllByUserinstaOrderByCreatedDateDesc(userinsta);
    }

    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found"));
        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();
        if (userLiked.isPresent()){
            post.setLakes(post.getLakes() - 1);
            post.getLikedUsers().add(username);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal) {

        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    private Userinsta getUserinstaByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserinstaByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

}
