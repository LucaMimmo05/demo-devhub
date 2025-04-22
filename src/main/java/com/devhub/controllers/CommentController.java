package com.devhub.controllers;

import com.devhub.dto.CommentResponseDTO;
import com.devhub.models.Comment;
import com.devhub.models.Post;
import com.devhub.models.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("api/comment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentController {

    @GET
    @Transactional
    public List<CommentResponseDTO> getComments() {
        List<Comment> comments = Comment.listAll();
        return comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    @POST
    public Response addComment(Comment comment) {
        User user = User.findById(comment.user.id);
        Post post = Post.findById(comment.post.id);

        if (user == null || post == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User or Post not found").build();
        }

        comment.user = user;
        comment.post = post;

        comment.persist();

        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

}
