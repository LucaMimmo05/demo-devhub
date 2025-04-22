package com.devhub.controllers;

import com.devhub.models.Category;
import com.devhub.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("api/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {



    @GET
    @Transactional
    public List<Category> getAllCategories() throws SQLException {
        return Category.listAll();
    }

    @POST
    @Transactional
    public Response createCategory(Category category) {
        category.persist();

        return Response.status(Response.Status.CREATED).entity(category).build();
    }

}
