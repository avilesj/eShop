package com.hercule.eshop.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cart")
public class Cart
{
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "users_id")
    private User user;
    private LocalDateTime createdOn;

    @PrePersist
    public void setDateTimeOnCreate()
    {
        createdOn = LocalDateTime.now();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
