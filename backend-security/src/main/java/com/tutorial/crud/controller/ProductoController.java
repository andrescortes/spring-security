package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Message;
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list() {
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id) {
        if (!productoService.existsById(id)) {
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        }
        Producto producto = productoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre) {
        if (!productoService.existsByName(nombre)) {
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        }
        Producto producto = productoService.getByName(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto) {
        if (StringUtils.isBlank(productoDto.getName())) {
            return new ResponseEntity(new Message("el nombre es obligatorio"),
                HttpStatus.BAD_REQUEST);
        }
        if (productoDto.getPrice() == null || productoDto.getPrice() < 0) {
            return new ResponseEntity(new Message("el precio debe ser mayor que 0"),
                HttpStatus.BAD_REQUEST);
        }
        if (productoService.existsByName(productoDto.getName())) {
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        Producto producto = new Producto(productoDto.getName(), productoDto.getPrice());
        productoService.save(producto);
        return new ResponseEntity(new Message("producto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id,
        @RequestBody ProductoDto productoDto) {
        if (!productoService.existsById(id)) {
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        }
        if (productoService.existsByName(productoDto.getName())
            && productoService.getByName(productoDto.getName()).get().getId() != id) {
            return new ResponseEntity(new Message("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(productoDto.getName())) {
            return new ResponseEntity(new Message("el nombre es obligatorio"),
                HttpStatus.BAD_REQUEST);
        }
        if (productoDto.getPrice() == null || productoDto.getPrice() < 0) {
            return new ResponseEntity(new Message("el precio debe ser mayor que 0"),
                HttpStatus.BAD_REQUEST);
        }

        Producto producto = productoService.getOne(id).get();
        producto.setName(productoDto.getName());
        producto.setPrice(productoDto.getPrice());
        productoService.save(producto);
        return new ResponseEntity(new Message("producto actualizado"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!productoService.existsById(id)) {
            return new ResponseEntity(new Message("no existe"), HttpStatus.NOT_FOUND);
        }
        productoService.delete(id);
        return new ResponseEntity(new Message("producto eliminado"), HttpStatus.OK);
    }


}
