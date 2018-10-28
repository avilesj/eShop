package com.javiles.eshop.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role
{

    private Long id;
    private String name;
    private Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers()
    {
        return users;
    }

    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Role))
        {
            return false;
        }

        Role role = (Role) o;

        return role.getName().equals(this.name);
    }
}
