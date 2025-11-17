package es.daw.springpurchasesapirest.entities;


//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
//@JsonIgnoreProperties({"compras"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String category;

    @ManyToMany(mappedBy = "products")
    private List<Purchase> purchases;

    public Product() {
        purchases = new ArrayList<>();
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.getProducts().add(this);
    }

    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
        purchase.getProducts().remove(this);
    }
}
