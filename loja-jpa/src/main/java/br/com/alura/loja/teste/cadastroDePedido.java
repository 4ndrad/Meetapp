package br.com.alura.loja.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.DAO.CategoriaDAO;
import br.com.alura.loja.DAO.ClienteDAO;
import br.com.alura.loja.DAO.PedidoDAO;
import br.com.alura.loja.DAO.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class cadastroDePedido {
	public static void main(String[] args) {
		popularBancoDedados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);

		Produto produto = produtoDao.buscarPorID(1l);
		Produto produto2 = produtoDao.buscarPorID(2l);
		Produto produto3 = produtoDao.buscarPorID(3l);
		
		Cliente cliente = clienteDao.buscarPorID(1l);
		Cliente cliente2 = clienteDao.buscarPorID(2l);
		
		em.getTransaction().begin();

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente2);
		pedido.adicionarItem(new ItemPedido(17, pedido, produto2));
		pedido.adicionarItem(new ItemPedido(56, pedido, produto3));
		
		PedidoDAO pedidoDao = new PedidoDAO(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL VENDIDO: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		relatorio.forEach(System.out::println);
		
		em.close();
		
	}


	private static void popularBancoDedados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Iphone X", "Celular da Apple", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("1500"), videogames);
		Produto mac = new Produto("Mac", "Macbook pro Apple", new BigDecimal("2000"), informatica);
		
		Cliente cliente = new Cliente("Natan", "12345678910");
		Cliente cliente2 = new Cliente("Ana Carolina", "10987654321");

		EntityManager em = JPAUtil.getEntityManager();

		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);

		// como ta em RESOURCE_LOCAL, precisa disso para delimitar uma transa��o
		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(mac);
		
		clienteDao.cadastrar(cliente);
		clienteDao.cadastrar(cliente2);

		// commit
		em.getTransaction().commit();
		em.close();
	}
}
