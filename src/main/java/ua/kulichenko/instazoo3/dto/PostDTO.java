package ua.kulichenko.instazoo3.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String caption;
    private String username; //userName?
    private String location;
    private Integer lakes;
    private Set<String> usersLiked;
}
