package es.ligasnba.app.model.jugador;

import java.util.List;


public class CustomPlayerResponse {

	private String page;
	private String total;
	private String records;
	private List<PlayerData> rows;
		
	public CustomPlayerResponse() {
	}
	
	public String getPage() {
		return page;
	}
	public String getRecords() {
		return records;
	}
	public List<PlayerData> getRows() {
		return rows;
	}
	public String getTotal() {
		return total;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public void setRows(List<PlayerData> rows) {
		this.rows = rows;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	
	
}
