package es.daw.productoapirest.mapper;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(source="fabricante.codigo", target="codigoFabricante")
    ProductoDTO toDto(Producto entity);

    @Mapping(target = "fabricante", ignore = true)
    Producto toEntity(ProductoDTO dto);

    List<ProductoDTO> toDtos(List<Producto> entity);

    List<Producto> toEntitys(List<ProductoDTO> dtos);
}
