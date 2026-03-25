package com.spring.internal_working.internal_work.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id //primary key
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerate by db
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false,name="email")
    private String email;

    @Column(nullable = false,name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user_obj",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    List<Addresses> addresses = new ArrayList<>();

    public void addAddress(Addresses address){
        this.addresses.add(address);
        address.setUser_obj(this);
    }

    public void removeAddress(Addresses address){
        this.addresses.remove(address);
        address.setUser_obj(null);
    }

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Tags>  tags = new HashSet<>();

    public void addTag(String tagName){
        var tag = new Tags(tagName);
        tags.add(tag);
        tag.getUsers().add(this);

    }

//    @OneToOne(mappedBy = "user",cascade = {CascadeType.REMOVE})
//    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Product> fav_products = new HashSet<>();

    public void addFavProduct(Product product){
        this.fav_products.add(product);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "password = " + password + ", " +
                "email = " + email + ")";
    }


}
