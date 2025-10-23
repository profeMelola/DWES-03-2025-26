package es.daw.productoapirest.service;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Fabricante;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.FabricanteRepository;
import es.daw.productoapirest.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final FabricanteRepository fabricanteRepository;
    private final ProductoMapper productoMapper;

//    @Autowired
//    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper, .....) {
//        this.productoRepository = productoRepository;
//        this.productoMapper = productoMapper;
    //.....
//    }

    public List<ProductoDTO> findAll() {
        List<Producto> productosEntities = productoRepository.findAll();
        return productoMapper.toDtos(productosEntities);
    }

    public Optional<ProductoDTO> findByCodigo(String codigo) {
        // El repository siempre devuelve un optional!!!!
        Optional<Producto> productoEntity = productoRepository.findByCodigo(codigo);

        if (productoEntity.isPresent()) {
            ProductoDTO productoDTO = productoMapper.toDto(productoEntity.get());
            return Optional.of(productoDTO);
        } else {
            return Optional.empty();
        }

    }

    // PENDIENTE!!!
    // ENDPOINT PARA CREAR PRODUCTO CON ID FABRICANTE POR DEFECTO A 1!!!
    public Optional<ProductoDTO> create(ProductoDTO productoDTO) {

        // Convertir DTO a entidad JPA
        Producto prodEntity = productoMapper.toEntity(productoDTO);

        // Asignar el fabricante al producto. Por defecto asignamos el fabricante con código 1
        // Para ello, necesito obtener el entity fabricante para asignarlo al entity producto
        //Optional<Fabricante> fabricanteOpt = fabricanteRepository.findById(1);
        Optional<Fabricante> fabricanteOpt = fabricanteRepository.findById(productoDTO.getCodigoFabricante());

        if (fabricanteOpt.isPresent()) {
            prodEntity.setFabricante(fabricanteOpt.get());
        }else {
            // propagar excepciones propias para indicar que el fabricante no existe...
            return Optional.empty();
        }

        // Guardar el nuevo producto
        Producto productoGuardado = productoRepository.save(prodEntity);

        return Optional.of(productoMapper.toDto(productoGuardado));


    }




}
