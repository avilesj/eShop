package com.javiles.eshop.stripe.models;

import com.javiles.eshop.models.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StripeCustomer")
public class StripeCustomer
{
    @Id
    @GeneratedValue
    private long id;
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "users_id")
    private User user;
    private LocalDateTime createdOn;
    private String customerId;

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

    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
}
