package es.daw.productoapirest.mapper;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDTO toDto(Producto entity);

    Producto toEntity(ProductoDTO dto);

    List<ProductoDTO> toDtos(List<Producto> entity);

    List<Producto> toEntitys(List<ProductoDTO> dtos);
}
