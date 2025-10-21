package es.daw.productoapirest.service;

import es.daw.productoapirest.dto.ProductoDTO;
import es.daw.productoapirest.entity.Producto;
import es.daw.productoapirest.mapper.ProductoMapper;
import es.daw.productoapirest.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        // Mapear de DTO a Entidad
        Producto producto = productoMapper.toEntity(productoDTO);

        // Guardar la entidad en la base de datos
        Producto productoGuardado = productoRepository.save(producto);

        // Mapear de Entidad a DTO
        return productoMapper.toDTO(productoGuardado);
    }


//    public ProductoDTO crearProducto(ProductoDTO dto) {
//        Producto producto = productoMapper.toEntity(dto);
//        Producto guardado = productoRepository.save(producto);
//        return productoMapper.toDTO(guardado);
//    }

    public List<ProductoDTO> obtenerTodos() {
        return productoMapper.toDTOList(productoRepository.findAll());
    }

    public ProductoDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setPrecio(BigDecimal.valueOf(dto.getPrecio()));
        // suponiendo que `cantidad` no es persistente, se omite o se gestiona externamente

        Producto actualizado = productoRepository.save(existente);
        return productoMapper.toDTO(actualizado);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}