package com.hercule.eshop.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartItem> cartItem;

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


    public List<CartItem> getCartItem()
    {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem)
    {
        this.cartItem = cartItem;
    }
}
