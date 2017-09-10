package es.ligasnba.app.model.contrato;

import java.math.BigDecimal;
import java.util.List;

public class TeamContractData {
	
	private long idEquipo;
	private long idCompeticion;
	private List<ContractData> listaContractData;
	private BigDecimal sumaSalarialT1;
	private BigDecimal sumaSalarialT2;
	private BigDecimal sumaSalarialT3;
	
	public long getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}
	public long getIdCompeticion() {
		return idCompeticion;
	}
	public void setIdCompeticion(long idCompeticion) {
		this.idCompeticion = idCompeticion;
	}
	public List<ContractData> getListaContractData() {
		return listaContractData;
	}
	public void setListaContractData(List<ContractData> listaContractData) {
		this.listaContractData = listaContractData;
	}
	public BigDecimal getSumaSalarialT1() {
		return sumaSalarialT1;
	}
	public void setSumaSalarialT1(BigDecimal sumaSalarialT1) {
		this.sumaSalarialT1 = sumaSalarialT1;
	}
	public BigDecimal getSumaSalarialT2() {
		return sumaSalarialT2;
	}
	public void setSumaSalarialT2(BigDecimal sumaSalarialT2) {
		this.sumaSalarialT2 = sumaSalarialT2;
	}
	public BigDecimal getSumaSalarialT3() {
		return sumaSalarialT3;
	}
	public void setSumaSalarialT3(BigDecimal sumaSalarialT3) {
		this.sumaSalarialT3 = sumaSalarialT3;
	}
}
