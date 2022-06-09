package br.com.alura.loja.modelo;

import javax.persistence.Entity;

@Entity
public class Computador extends Produto{
	private String marca;
	private Integer modelo;
	
	public Computador() {
		
	}
	
	public Computador(String marca, Integer modelo) {
		super();
		this.marca = marca;
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Integer getModelo() {
		return modelo;
	}
	public void setModelo(Integer modelo) {
		this.modelo = modelo;
	}
}
