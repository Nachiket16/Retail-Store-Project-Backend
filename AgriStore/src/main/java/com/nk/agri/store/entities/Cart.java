package com.nk.agri.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart
{
    @Id
    private String cartId;

    private Date createdAt;
    @OneToOne
    private User user;

    //mapping with cart items -> By directional
    @OneToMany(
            mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.EAGER
    )
    private List<CartItem> items = new ArrayList<>();

}
