package com.javiles.eshop.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<CartItem> cartItem = new ArrayList<>();
    private long size;

    @PrePersist
    public void setDateTimeOnCreate()
    {
        createdOn = LocalDateTime.now();
    }

    @PostLoad
    public void calculateSize() {
        this.size = cartItem.stream().mapToLong(CartItem::getQuantity).sum();
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

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }
}
