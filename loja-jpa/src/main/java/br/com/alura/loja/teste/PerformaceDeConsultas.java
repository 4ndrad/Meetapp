package br.com.alura.loja.teste;

import java.math.BigDecimal;

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

public class PerformaceDeConsultas {
	public static void main(String[] args) {
		popularBancoDedados();
		EntityManager em = JPAUtil.getEntityManager();
		
		PedidoDAO pedidoDao = new PedidoDAO(em);
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);
		
		em.close();
		System.out.println(pedido.getCliente().getNome());
		
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

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, videogame));
		
		Pedido pedido2 = new Pedido(cliente2);
		pedido.adicionarItem(new ItemPedido(17, pedido, mac));
		pedido.adicionarItem(new ItemPedido(56, pedido, videogame));
		
		EntityManager em = JPAUtil.getEntityManager();

		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao = new PedidoDAO(em);

		// como ta em RESOURCE_LOCAL, precisa disso para delimitar uma transação
		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(mac);
		
		clienteDao.cadastrar(cliente);
		clienteDao.cadastrar(cliente2);
		
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);

		// commit
		em.getTransaction().commit();
		em.close();
	}
}
