package com.javiles.eshop.stripe.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StripeCustomerPayment")
public class StripeCustomerPayment
{
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stripe_customer_id")
    private StripeCustomer stripeCustomer;
    private LocalDateTime createdOn;

    private String paymentId;
    private long amountInCents;
    private String currency;

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

    public String getPaymentId()
    {
        return paymentId;
    }

    public void setPaymentId(String paymentId)
    {
        this.paymentId = paymentId;
    }

    public long getAmountInCents()
    {
        return amountInCents;
    }

    public void setAmountInCents(long amountInCents)
    {
        this.amountInCents = amountInCents;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    @PrePersist
    public void setDateTimeOnCreate()
    {
        createdOn = LocalDateTime.now();
    }


}
