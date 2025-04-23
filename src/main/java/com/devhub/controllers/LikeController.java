package com.devhub.controllers;

import com.devhub.models.Like;
import com.devhub.models.LikeId;
import com.devhub.models.Post;
import com.devhub.models.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/like")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeController {

    @GET
    @Transactional
    public List<Like> getLikes() {
        return Like.listAll();
    }

    @POST
    @Transactional
    public Response createLike(Like like) {
        if (like.user == null || like.post == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User and Post must be provided").build();
        }

        // Ottieni user e post dai dati ricevuti
        User user = User.findById(like.user.id);
        Post post = Post.findById(like.post.id);

        if (user == null || post == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User or Post not found").build();
        }

        like.user = user;
        like.post = post;

        like.persist();
        return Response.status(Response.Status.CREATED).entity(like).build();
    }

}
