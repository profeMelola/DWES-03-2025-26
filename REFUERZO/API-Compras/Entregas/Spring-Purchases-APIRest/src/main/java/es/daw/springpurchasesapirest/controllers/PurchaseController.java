package es.daw.springpurchasesapirest.controllers;

import es.daw.springpurchasesapirest.dtos.PurchaseDTO;
import es.daw.springpurchasesapirest.services.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@Validated

public class PurchaseController {

    private final PurchaseService purchaseService;


    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> findAll() {
        return ResponseEntity.status(200).body(purchaseService.findAll().get());
    }

    @PostMapping
    public ResponseEntity<Void> newPurchase(@Valid @RequestBody PurchaseDTO purchase) {
        return purchaseService.newPurchase(purchase).isPresent() ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<PurchaseDTO>> findByDate(@PathVariable
                                                        @DateTimeFormat(iso =
                                                                DateTimeFormat.ISO.DATE)
            // I changed it from Lo
                                                        LocalDate date) {

        Optional<List<PurchaseDTO>> purchasesFilteredByDate =
                purchaseService.findPurchaseByDate(date);

        return purchasesFilteredByDate.map(purchaseDTOS -> ResponseEntity.status(HttpStatus.OK).body(purchaseDTOS))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
