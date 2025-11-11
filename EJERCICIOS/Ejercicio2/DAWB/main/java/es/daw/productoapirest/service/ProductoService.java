package es.daw.productoapirest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Fabricante;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.exception.ProductoNotFoundException;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.FabricanteRepository;
import es.daw.productoapirest.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final FabricanteRepository fabricanteRepository;
    private final ProductoMapper productoMapper;
    private final ObjectMapper objectMapper;

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

    public void deleteByCodigo(String codigo){
    //public boolean deleteByCodigo(String codigo){
//        Optional<Producto> productoEntity = productoRepository.findByCodigo(codigo);
//        if (productoEntity.isPresent()){
//            //productoRepository.delete(productoEntity.get()); //borrar por entity completo
//            productoRepository.deleteById(productoEntity.get().getId());
//            // devolver un 204
//            return true;
//        }
//
//        //throw new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado")
//
//
//        // PENDIENTE!!! Lanzar excepción propia ProductoNotFoundException
//        // El mensaje: El producto con código XXX no existe en la BD
//        return false; // devolver un 404

        // Devuelve el valor si está presente o lanza una excepción en caso contrario
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow( () -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado"));

        productoRepository.delete(producto);
        //productoRepository.deleteById(producto.getId());

    }


    public Optional<ProductoDTO> update(String codigo, ProductoDTO productoDTO) {
    //public void update(String codigo, ProductoDTO productoDTO) {

        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado"));

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCodigo(productoDTO.getCodigo());

        // asignar el fabricante..
        Optional<Fabricante> fabricanteOpt = fabricanteRepository.findById(productoDTO.getCodigoFabricante());
        if (fabricanteOpt.isPresent()) {
            producto.setFabricante(fabricanteOpt.get());
        }
        else{
            // PENDIENTE: LANZAR FABRICANTE NOT FOUND EXCEPTION....
            // return Optional.empty();
        }

        Producto productoSalvado = productoRepository.save(producto);

        return Optional.of(productoMapper.toDto(productoSalvado));


    }


    /**
     *
     * @param codigo
     * @param camposActualizados
     * @return
     */
    public Optional<ProductoDTO> updateParcial(String codigo, Map<String,Object> camposActualizados) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado"));

        /*
            REFLEXIÓN!!!!
            Se utiliza para acceder y manipular campos (atributos) de una clase mediante la reflexión.
            La reflexión es una poderosa característica de Java que te permite inspeccionar, analizar y
            modificar la estructura y el comportamiento de clases, métodos, constructores y
            campos en tiempo de ejecución.
         */
        camposActualizados.forEach((key, value) -> {

            // CASO ESPECIAL: campo virtual "codigoFabricante"
            if ("codigoFabricante".equals(key)) {
                Integer idFabricante = objectMapper.convertValue(value, Integer.class);

                Fabricante fabricante = fabricanteRepository.findById(idFabricante)
                        .orElseThrow(() -> new RuntimeException(
                                "Fabricante no encontrado con id: " + idFabricante));

                producto.setFabricante(fabricante);
                return; // saltamos al siguiente campo
            }

            // Resto de campos: usar reflexión
            Field field = ReflectionUtils.findField(Producto.class, key);

            if (field != null) {
                field.setAccessible(true);
                Object valorConvertido = objectMapper.convertValue(value, field.getType());
                ReflectionUtils.setField(field, producto, valorConvertido);
            } else {
                throw new IllegalArgumentException("Campo no válido: " + key);
            }
        });

        Producto productoSalvado = productoRepository.save(producto);
        return Optional.of(productoMapper.toDto(productoSalvado));



    }

    public Optional<ProductoDTO> updateParcialdto(String codigo, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con codigo "+codigo+" no encontrado"));

        if (productoDTO.getNombre() != null) producto.setNombre(productoDTO.getNombre());
        if (productoDTO.getPrecio() != null) producto.setPrecio(productoDTO.getPrecio());
        if (productoDTO.getCodigo() != null) producto.setCodigo(productoDTO.getCodigo());

        if (productoDTO.getCodigoFabricante() != null){
            Fabricante fab = fabricanteRepository.findById(productoDTO.getCodigoFabricante())
                    .orElseThrow(() -> new RuntimeException("No se encontró el fabricante con codigo "+productoDTO.getCodigoFabricante()));
            producto.setFabricante(fab);
        }

        Producto productoSalvado = productoRepository.save(producto);
        return Optional.of(productoMapper.toDto(productoSalvado));



    }

}
