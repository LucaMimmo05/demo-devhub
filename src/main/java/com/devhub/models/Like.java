package com.devhub.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like extends PanacheEntityBase {

    @EmbeddedId
    public LikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public User user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    @JsonbProperty
    public Post post;

    public Like() {}

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
        this.id = new LikeId(user.id, post.id);
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
}
