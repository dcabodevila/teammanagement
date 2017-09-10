package es.ligasnba.app.model.estadistica;

import java.util.List;


public class CustomStatResponse {

	private String page;
	private String total;
	private String records;
	private List<StatData> rows;
	
	public CustomStatResponse() {
	}
	
	public String getPage() {
		return page;
	}
	public String getRecords() {
		return records;
	}
	public List<StatData> getRows() {
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
	public void setRows(List<StatData> rows) {
		this.rows = rows;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
