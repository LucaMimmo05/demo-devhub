package com.devhub.controllers;

import com.devhub.models.Post;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @GET
    @Transactional
    public List<Post> getPost() {
        return Post.listAll();
    }

    @POST
    @Transactional
    public Response createPost(Post post) {
        post.persist();

        return Response.status(Response.Status.CREATED).entity(post).build();
    }
}
