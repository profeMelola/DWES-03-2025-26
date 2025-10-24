package es.daw.productoapirest.service;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
//    // 1. INYECCIÓN POR PROPIEDAD
//    @Autowired
//    private ProductoRepository productoRepository;
//

    // Inyección por constructor  ****
    // - Al declarar las dependencias como `final`, te aseguras de que éstas se inicialicen una única vez (en el constructor)
    // y no puedan ser sobrescritas.
    //  Esto mejora la estabilidad del código al garantizar que las dependencias no cambien.
    // - Puedes crear fácilmente objetos de prueba y pasar implementaciones "mock" o "fake" a los tests unitarios de la clase.
    // - No dependes de un framework como Spring para inicializar las dependencias en el momento de las pruebas.
    // - Con la inyección por constructor, es inmediato saber cuáles son las dependencias que necesita una clase,
    // ya que están listadas explícitamente en la firma del constructor. Esto evita confusión sobre cómo y cuándo se inicializan.

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    // No es necesario el constructor con @Autowired gracias a @RequiredArgsConstructor
//    @Autowired
//    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
//        this.productoRepository = productoRepository;
//        this.productoMapper = productoMapper;
//    }

    public List<ProductoDTO> findAll() {

        List<Producto> productosEntities = productoRepository.findAll();
        return productoMapper.toDtos(productosEntities);
    }

//    public ProductoDTO findByCodigo(String codigo) {
    public Optional<ProductoDTO> findByCodigo(String codigo) {
        Optional<Producto> productoEntity = productoRepository.findByCodigo(codigo);

        if (productoEntity.isPresent()) {
            return Optional.of(productoMapper.toDto(productoEntity.get()));
        }
        // Si no lo encuentra
        return Optional.empty();
    }

    public Optional<ProductoDTO> crearProducto(ProductoDTO productoDTO) {
        // Convertir DTO a entidad
        Producto productoEntity = productoMapper.toEntity(productoDTO);

        // Guardar la entidad en la base de datos
        Producto productoGuardado = productoRepository.save(productoEntity);
        //productoRepository.save(productoEntity);



        // Convertir la entidad guardada de vuelta a DTO
        Optional<ProductoDTO> dto = productoMapper.toDto(productoGuardado);

        // PARA EL VIERNES 24 DE OCTUBRE........

    }


}
