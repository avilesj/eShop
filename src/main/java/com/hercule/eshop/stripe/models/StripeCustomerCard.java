package com.hercule.eshop.stripe.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "StripeCustomerCard")
public class StripeCustomerCard
{
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stripe_customer_id")
    private StripeCustomer stripeCustomer;
    private LocalDateTime createdOn;
    private String token;
    private String lastFour;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public StripeCustomer getStripeCustomer()
    {
        return stripeCustomer;
    }

    public void setStripeCustomer(StripeCustomer stripeCustomer)
    {
        this.stripeCustomer = stripeCustomer;
    }

    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getLastFour()
    {
        return lastFour;
    }

    public void setLastFour(String lastFour)
    {
        this.lastFour = lastFour;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof StripeCustomerCard))
        {
            return false;
        }

        StripeCustomerCard stripeCustomerCard = (StripeCustomerCard) o;

        return stripeCustomerCard.getToken().equals(this.token);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.token);
    }
}
