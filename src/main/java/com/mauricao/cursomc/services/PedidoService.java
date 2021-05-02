package com.mauricao.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mauricao.cursomc.domain.Categoria;
import com.mauricao.cursomc.domain.Pedido;
import com.mauricao.cursomc.repositories.PedidoRepository;
import com.mauricao.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar( Integer id ) {
		Optional <Pedido> pedido = repo.findById(id);
		return pedido.orElse(null);
	}
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		} 

}
