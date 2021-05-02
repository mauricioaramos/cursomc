package com.mauricao.cursomc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mauricao.cursomc.domain.Categoria;
import com.mauricao.cursomc.domain.Cidade;
import com.mauricao.cursomc.domain.Cliente;
import com.mauricao.cursomc.domain.Endereco;
import com.mauricao.cursomc.domain.Estado;
import com.mauricao.cursomc.domain.ItemPedido;
import com.mauricao.cursomc.domain.Pagamento;
import com.mauricao.cursomc.domain.PagamentoComBoleto;
import com.mauricao.cursomc.domain.PagamentoComCartao;
import com.mauricao.cursomc.domain.Pedido;
import com.mauricao.cursomc.domain.Produto;
import com.mauricao.cursomc.domain.enums.EstadoPagamento;
import com.mauricao.cursomc.domain.enums.TipoCliente;
import com.mauricao.cursomc.repositories.CategoriaRepository;
import com.mauricao.cursomc.repositories.CidadeRepository;
import com.mauricao.cursomc.repositories.ClienteRepository;
import com.mauricao.cursomc.repositories.EnderecoRepository;
import com.mauricao.cursomc.repositories.EstadoRepository;
import com.mauricao.cursomc.repositories.ItemPedidoRepository;
import com.mauricao.cursomc.repositories.PagamentoRepository;
import com.mauricao.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private ClienteRepository clienteRepository  ;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository ;
	
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
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "13242565576", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("7124557677", "113786543"));
		Endereco e1 = new Endereco(null, "Rua flores", "300", "ap 303", "Jardim", "787665", cli1, cidadeUderlandia);
		Endereco e2 = new Endereco(null, "Av matos", "32", "ap 30", "Jardim 7", "78347665", cli1, cidadeSaoPaulo);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1)); 
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, LocalDate.parse("30/09/2017 10:32",formatter), cli1, e1);
		Pedido ped2 = new Pedido(null, LocalDate.parse("10/10/2017 19:35", formatter), cli1, e2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1); 

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, LocalDate.parse("20/10/2017 00:00", formatter), null);
		ped2.setPagamento(pagto2);
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2)); 
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}
