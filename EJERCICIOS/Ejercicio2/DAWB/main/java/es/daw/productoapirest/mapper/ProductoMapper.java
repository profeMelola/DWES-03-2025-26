package es.daw.productoapirest.mapper;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;


import java.util.List;

// 1.
// Permite que Spring gestione el bean del mapper, para poder usarlo como una dependencia inyectable.
@Mapper(componentModel = "spring")
public interface ProductoMapper {

    // Mapea de Entidad a DTO
    ProductoDTO toDTO(Producto producto);

    // Mapea de DTO a Entidad
    Producto toEntity(ProductoDTO productoDTO);

    // Si necesitas listas, también puedes mapearlas directamente
    List<ProductoDTO> toDTOList(List<Producto> productos);
    List<Producto> toEntityList(List<ProductoDTO> productoDTOs);
}