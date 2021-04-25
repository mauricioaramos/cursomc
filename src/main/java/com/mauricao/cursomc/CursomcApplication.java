package com.mauricao.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mauricao.cursomc.domain.Categoria;
import com.mauricao.cursomc.domain.Cidade;
import com.mauricao.cursomc.domain.Estado;
import com.mauricao.cursomc.domain.Produto;
import com.mauricao.cursomc.repositories.CategoriaRepository;
import com.mauricao.cursomc.repositories.CidadeRepository;
import com.mauricao.cursomc.repositories.EstadoRepository;
import com.mauricao.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null,"Computador", 2000D);
		Produto p2 = new Produto(null,"Impressora", 800D);
		Produto p3 = new Produto(null,"Mouse", 80D);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado estadoMinasGerais = new Estado(null, "Minas Gerais");
		Estado estadoSaoPaulo = new Estado(null, "Sao paulo");
		
		Cidade cidadeUderlandia = new Cidade(null,"Uberlandia", estadoMinasGerais);
		Cidade cidadeSaoPaulo = new Cidade(null,"Sao Paulo", estadoSaoPaulo);
		Cidade cidadeCampinas = new Cidade(null,"Campinas", estadoSaoPaulo);
		
		estadoMinasGerais.getCidades().addAll(Arrays.asList(cidadeUderlandia));
		estadoSaoPaulo.getCidades().addAll(Arrays.asList(cidadeSaoPaulo, cidadeCampinas));
		
		estadoRepository.saveAll(Arrays.asList(estadoMinasGerais, estadoSaoPaulo));
		cidadeRepository.saveAll(Arrays.asList(cidadeUderlandia, cidadeSaoPaulo, cidadeCampinas));
		
	}

}
