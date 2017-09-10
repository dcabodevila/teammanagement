package es.ligasnba.app.model.partido;

import java.util.List;

import es.ligasnba.app.model.partido.MatchData;

public class CustomMatchResponse {

	private String page;
	private String total;
	private String records;
	private List<MatchData> rows;
	
	public CustomMatchResponse() {
	}
	
	public String getPage() {
		return page;
	}
	public String getRecords() {
		return records;
	}
	public List<MatchData> getRows() {
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
	public void setRows(List<MatchData> rows) {
		this.rows = rows;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
