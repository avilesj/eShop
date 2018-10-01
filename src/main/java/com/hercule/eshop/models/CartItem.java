package com.hercule.eshop.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CartItem")
public class CartItem
{
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private long quantity;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }


    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public long getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(long quantity)
    {
        this.quantity = quantity;
    }

    @PrePersist
    public void setDateTimeOnCreate()
    {
        createdOn = LocalDateTime.now();
    }
}
