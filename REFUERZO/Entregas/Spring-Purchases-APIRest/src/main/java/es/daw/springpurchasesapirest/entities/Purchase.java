package es.daw.springpurchasesapirest.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="purchases")
@Data
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customer;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private double total;

    @ManyToMany
    @JoinTable(
            name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Purchase() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        product.getPurchases().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getPurchases().remove(this);
    }

}