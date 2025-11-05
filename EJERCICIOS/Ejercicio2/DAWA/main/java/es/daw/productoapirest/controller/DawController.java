package es.daw.productoapirest.controller;

import es.daw.productoapirest.config.DawConfig;
import es.daw.productoapirest.dto.DawResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DawController {

    // Inyección por Propiedad
//    @Autowired
//    private DawConfig dawConfig;

    // Inyección por constructor
    private final DawConfig dawConfig;

//    @Autowired
//    public DawController(DawConfig dawConfig) {
//        this.dawConfig = dawConfig;
//    }

    @GetMapping("/values-conf-externo")
    public DawResponseDTO values() {
        return new DawResponseDTO(dawConfig.getCode(), dawConfig.getMessage());
    }

}