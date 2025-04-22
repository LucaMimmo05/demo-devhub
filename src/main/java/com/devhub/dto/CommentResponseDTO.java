package com.devhub.dto;

import com.devhub.models.Comment;
import com.devhub.models.Post;
import com.devhub.models.User;


public class CommentResponseDTO {

    public Long id;
    public String content;
    public UserResponseDTO user;
    public PostResponseDTO post;

    // Costruttore per mappare la entity Comment
    public CommentResponseDTO(Comment comment) {
        this.id = comment.id;
        this.content = comment.content;
        this.user = new UserResponseDTO(comment.user);
        this.post = new PostResponseDTO(comment.post);
    }

    // DTO per l'utente (User)
    public static class UserResponseDTO {
        public Long id;
        public String username;

        public UserResponseDTO(User user) {
            this.id = user.id;
            this.username = user.username;
        }
    }

    // DTO per il post (Post)
    public static class PostResponseDTO {
        public Long id;
        public String title;

        public PostResponseDTO(Post post) {
            this.id = post.id;
            this.title = post.title;
        }
    }
}
