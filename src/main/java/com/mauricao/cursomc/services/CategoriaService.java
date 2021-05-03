package com.mauricao.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mauricao.cursomc.domain.Categoria;
import com.mauricao.cursomc.repositories.CategoriaRepository;
import com.mauricao.cursomc.services.exceptions.DataIntegrityException;
import com.mauricao.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar( Integer id ) {
		Optional <Categoria> categoria = repo.findById(id);
		return categoria.orElse(null);
	}
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		} 
	
	public Categoria insert( Categoria categoria ) {
		categoria.setId(null);
		return repo.save(categoria);
	}
	public Categoria update( Categoria categoria ) {
		this.find(categoria.getId());
		return repo.save(categoria);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
			
		}catch( DataIntegrityViolationException e ) {
			throw new DataIntegrityException("Nao eh possivel excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}
