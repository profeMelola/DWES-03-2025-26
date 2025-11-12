package es.daw.productoapirest.mapper;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Fabricante;
import es.daw.productoapirest.entity.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-11T17:21:55+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO toDto(Producto entity) {
        if ( entity == null ) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setCodigoFabricante( entityFabricanteCodigo( entity ) );
        productoDTO.setNombre( entity.getNombre() );
        productoDTO.setPrecio( entity.getPrecio() );
        productoDTO.setCodigo( entity.getCodigo() );

        return productoDTO;
    }

    @Override
    public Producto toEntity(ProductoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Producto producto = new Producto();

        producto.setCodigo( dto.getCodigo() );
        producto.setNombre( dto.getNombre() );
        producto.setPrecio( dto.getPrecio() );

        return producto;
    }

    @Override
    public List<ProductoDTO> toDtos(List<Producto> entity) {
        if ( entity == null ) {
            return null;
        }

        List<ProductoDTO> list = new ArrayList<ProductoDTO>( entity.size() );
        for ( Producto producto : entity ) {
            list.add( toDto( producto ) );
        }

        return list;
    }

    @Override
    public List<Producto> toEntitys(List<ProductoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Producto> list = new ArrayList<Producto>( dtos.size() );
        for ( ProductoDTO productoDTO : dtos ) {
            list.add( toEntity( productoDTO ) );
        }

        return list;
    }

    private Integer entityFabricanteCodigo(Producto producto) {
        Fabricante fabricante = producto.getFabricante();
        if ( fabricante == null ) {
            return null;
        }
        return fabricante.getCodigo();
    }
}
