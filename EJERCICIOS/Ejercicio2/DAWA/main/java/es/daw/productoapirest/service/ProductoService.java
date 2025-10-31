package es.daw.productoapirest.service;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Fabricante;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.exception.ProductoNotFoundException;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.FabricanteRepository;
import es.daw.productoapirest.repository.ProductoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    private final FabricanteRepository fabricanteRepository;
    private final ProductoMapper productoMapper;

//    // No es necesario el constructor con @Autowired gracias a @RequiredArgsConstructor
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

    /**
     * Dar de alta producto en la BD
     * @param productoDTO
     * @return
     */
    public Optional<ProductoDTO> crearProducto(ProductoDTO productoDTO) {
        // Convertir DTO a entidad
        Producto productoEntity = productoMapper.toEntity(productoDTO);

        // Asignar por defecto el fabricante cuyo código es 1 ...
        //Optional<Fabricante> fabOpt = fabricanteRepository.findById(1);
        Optional<Fabricante> fabOpt = fabricanteRepository.findById(productoDTO.getCodigoFabricante());
        if (fabOpt.isPresent()) {
            productoEntity.setFabricante(fabOpt.get());
        }else{
            return Optional.empty(); // si no encuentra el fabricante devuelve un optional vacío
        }

        // Guardar la entidad en la base de datos
        Producto productoGuardado = productoRepository.save(productoEntity);
        //productoRepository.save(productoEntity);

        // Convertir la entidad guardada de vuelta a DTO
        ProductoDTO dto = productoMapper.toDto(productoGuardado);

        return Optional.of(dto);

    }

//    public boolean deleteByCodigo(String codigo) {
//    //public void deleteByCodigo(String codigo) {
//        //productoRepository.deleteProductoByCodigo(codigo);
//        //productoRepository.deleteById(productoRepository.findByCodigo(codigo).get().getId());
//
//        // 2 respuestas diferentes.... si el producto existe...
//        Optional<Producto> productoEntity = productoRepository.findByCodigo(codigo);
//        if (productoEntity.isPresent()) {
//            productoRepository.deleteById(productoEntity.get().getId());
//            return true;
//        }
//        // El producto con código XXXX no existe en la BD
//        // lanzar excepción propia ProductoNotFoundException
//        return false;
//
//
//    }

    public void deleteByCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow( () -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado!"));

        productoRepository.delete(producto);
    }


    public Optional<ProductoDTO> update(String codigo, ProductoDTO productoDTO) {
        Producto productoEntity = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado!"));

        productoEntity.setNombre(productoDTO.getNombre());
        productoEntity.setPrecio(productoDTO.getPrecio());
        productoEntity.setCodigo(productoDTO.getCodigo());

        //necesito un objeto fabricante y tengo el id
        Optional<Fabricante> fab = fabricanteRepository.findById(productoDTO.getCodigoFabricante());
        if (fab.isPresent()) {
            productoEntity.setFabricante(fab.get());
        }
        else{
            // PENDIENTE!!! lanzar fabricant not found exception y devuelve 400 (bad request)... me pasa un código de fabricante mal!!!!
            return Optional.empty();
        }

        Producto productoGuardado = productoRepository.save(productoEntity);

        return Optional.of(productoMapper.toDto(productoGuardado));

    }

    /**
     *
     * @param codigo
     * @param productoDTO
     * @return
     */
    public Optional<ProductoDTO> updateParcial(String codigo, ProductoDTO productoDTO) {
       Producto producto = productoRepository.findByCodigo(codigo)
               .orElseThrow(() -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado!"));

        if ( productoDTO.getNombre() != null) producto.setNombre(productoDTO.getNombre());
        if ( productoDTO.getPrecio() != null) producto.setPrecio(productoDTO.getPrecio());
        if ( productoDTO.getPrecio() != null) producto.setCodigo(productoDTO.getCodigo());

        if (productoDTO.getCodigoFabricante() != null){
            Fabricante  fabricante = fabricanteRepository.findById(productoDTO.getCodigoFabricante())
                    .orElseThrow(() -> new RuntimeException("Fabricante con código "+productoDTO.getCodigoFabricante()+" no encontrado!"));

            producto.setFabricante(fabricante);
        }

        Producto productoGuardado = productoRepository.save(producto);

        return Optional.of(productoMapper.toDto(productoGuardado));
    }


}
