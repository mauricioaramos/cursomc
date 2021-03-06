package com.mauricao.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mauricao.cursomc.domain.Categoria;
import com.mauricao.cursomc.dto.CategoriaDTO;
import com.mauricao.cursomc.services.CategoriaService;

@Validated
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id ) {
		
		Categoria categoria = service.find(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoria){
		Categoria obj = service.fromDTO(categoria);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable Integer id){
		Categoria categoria = service.fromDTO(categoriaDto);
		categoria.setId(id);
		categoria = service.update(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Categoria> delete(@PathVariable Integer id ) {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		List<Categoria> categorias = service.findAll();
		List<CategoriaDTO> categoriasDtos = categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDtos);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page, 
			@RequestParam(name = "linesPerPage", defaultValue = "4")Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(name = "direction", defaultValue = "ASC")String direction) {
		
		Page<Categoria> categorias = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasDtos = categorias.map(categoria -> new CategoriaDTO(categoria));
		
		return ResponseEntity.ok().body(categoriasDtos);
	}

}
