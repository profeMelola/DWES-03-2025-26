package es.daw.springpurchasesapirest.services;

import es.daw.springpurchasesapirest.dtos.PurchaseDTO;
import es.daw.springpurchasesapirest.entities.Product;
import es.daw.springpurchasesapirest.entities.Purchase;
import es.daw.springpurchasesapirest.exceptions.ProductoNotFoundException;
import es.daw.springpurchasesapirest.mappers.PurchaseMapper;
import es.daw.springpurchasesapirest.repositories.ProductRepository;
import es.daw.springpurchasesapirest.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;


    public Optional<List<PurchaseDTO>> findAll() {
        List<Purchase> purchasesEntities = purchaseRepository.findAll();
        return Optional.ofNullable(purchaseMapper.toPurchaseDTOList(purchasesEntities));
    }

    public Optional<PurchaseDTO> newPurchase(PurchaseDTO purchase) {
        List<Product> products = purchase.getProducts().stream()
                .map(productID ->
                        productRepository.findById(productID)
                                .orElseThrow(() -> new ProductoNotFoundException("product not found, id: " + productID)))
                .toList();

        Purchase newPurchase = purchaseMapper.toPurchaseEntity(purchase);
        newPurchase.setProducts(products);
        purchaseRepository.save(newPurchase);
        return Optional.ofNullable(purchase);
    }

    public Optional<List<PurchaseDTO>> findPurchaseByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return Optional.ofNullable(purchaseMapper.toPurchaseDTOList(purchaseRepository.findByDateBetween(start,
                end).get()));

    }


}
