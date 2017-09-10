package es.ligasnba.app.util.jqgrid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomGenericResponse {
	private Boolean success;
	private Date fecha; 
	private String message;
	private List<String> messageLines;
	
	public CustomGenericResponse(final boolean success, String mensaje) {
		this.success = success;
		this.message = mensaje;
		this.fecha = new Date();
	}
	
	public CustomGenericResponse() {
		messageLines = new ArrayList<String>();
	}
	 
	public Boolean getSuccess() {
	return success;
	}
	 
	public void setSuccess(Boolean success) {
	this.success = success;
	}	 
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public List<String> getMessageLines() {
		return messageLines;
	}
	public void setMessageLines(List<String> messageLines) {
		this.messageLines = messageLines;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
