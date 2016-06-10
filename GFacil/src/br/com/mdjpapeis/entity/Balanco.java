package br.com.mdjpapeis.entity;

import java.math.BigDecimal;

public class Balanco {

	private int mes;
	private String mes2;
	private BigDecimal entrada;
	private BigDecimal saida;
	private BigDecimal percentual;
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getMes2() {
		return mes2;
	}
	public void setMes2(String mes2) {
		this.mes2 = mes2;
	}
	public BigDecimal getEntrada() {
		return entrada;
	}
	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}
	public BigDecimal getSaida() {
		return saida;
	}
	public void setSaida(BigDecimal saida) {
		this.saida = saida;
	}
	public BigDecimal getPercentual() {
		return percentual;
	}
	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}
	
}
