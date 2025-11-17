package es.daw.springpurchasesapirest.mappers;

import es.daw.springpurchasesapirest.dtos.PurchaseDTO;
import es.daw.springpurchasesapirest.entities.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T19:52:21+0100",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Override
    public PurchaseDTO toPurchaseDTO(Purchase purchase) {
        if ( purchase == null ) {
            return null;
        }

        PurchaseDTO purchaseDTO = new PurchaseDTO();

        purchaseDTO.setCustomer( purchase.getCustomer() );
        purchaseDTO.setDate( purchase.getDate() );
        purchaseDTO.setTotal( purchase.getTotal() );

        return purchaseDTO;
    }

    @Override
    public Purchase toPurchaseEntity(PurchaseDTO purchaseDTO) {
        if ( purchaseDTO == null ) {
            return null;
        }

        Purchase purchase = new Purchase();

        purchase.setCustomer( purchaseDTO.getCustomer() );
        purchase.setDate( purchaseDTO.getDate() );
        purchase.setTotal( purchaseDTO.getTotal() );

        return purchase;
    }

    @Override
    public List<PurchaseDTO> toPurchaseDTOList(List<Purchase> purchases) {
        if ( purchases == null ) {
            return null;
        }

        List<PurchaseDTO> list = new ArrayList<PurchaseDTO>( purchases.size() );
        for ( Purchase purchase : purchases ) {
            list.add( toPurchaseDTO( purchase ) );
        }

        return list;
    }
}
