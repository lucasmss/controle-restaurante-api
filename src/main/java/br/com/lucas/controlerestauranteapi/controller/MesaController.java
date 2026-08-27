package br.com.lucas.controlerestauranteapi.controller;

import br.com.lucas.controlerestauranteapi.entity.Mesa;
import br.com.lucas.controlerestauranteapi.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MesaController {
    private final MesaService mesaService;

    public MesaController(MesaService mesaService){
        this.mesaService = mesaService;
    }

    @GetMapping("/mesas")
    public List<Mesa> listarMesas(){
        return mesaService.listarTodas();
    }

    @GetMapping("/mesas/{id}")
    public Mesa buscarMesaId(@PathVariable Long id){
        return mesaService.buscarMesaId(id);
    }

    @PostMapping("/mesas")
    public Mesa novaMesa(@Valid @RequestBody Mesa mesa){
        return mesaService.novaMesa(mesa);
    }

    @PutMapping("/mesas/{id}")
    public Mesa atualizarMesa(@PathVariable Long id, @RequestBody Mesa mesa){
        return mesaService.atualizarMesa(id, mesa);
    }

    @DeleteMapping("mesas/{id}")
    public void excluirMesa(@PathVariable Long id){
        mesaService.excluirMesa(id);
    }

}
