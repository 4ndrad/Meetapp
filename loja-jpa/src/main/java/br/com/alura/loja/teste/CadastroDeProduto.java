package br.com.alura.loja.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.DAO.CategoriaDAO;
import br.com.alura.loja.DAO.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.CategoriaId;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		Produto p = produtoDao.buscarPorID(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("VIDEOGAMES");
        todos.forEach(p2 -> System.out.println(p2.getNome()));
        
        BigDecimal buscarPrecoDoPrdouto = produtoDao.buscarPrecoDoPrdoutoComNome("PS5");
        System.out.println("Preço do produto: " + buscarPrecoDoPrdouto);
	}
	
	private static void cadastrarProduto(){
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Iphone X", "Celular da Apple", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("1500"), videogames);
		Produto mac = new Produto("Mac", "Macbook pro Apple", new BigDecimal("2000"), informatica);

		EntityManager em = JPAUtil.getEntityManager();

		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);

		// como ta em RESOURCE_LOCAL, precisa disso para delimitar uma transação
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		categoriaDao.cadastrar(videogames);
		produtoDao.cadastrar(videogame);
		categoriaDao.cadastrar(informatica);
		produtoDao.cadastrar(mac);

		// commit
		em.getTransaction().commit();
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		em.close();
	}
}
