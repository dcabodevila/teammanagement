package es.ligasnba.app.model.partido;

public class ValoracionData{

	private long idActaPartido;
	private Short valoracion;
	private String comentario;
	private Boolean victoria;
	private MatchData matchData;
	
	public long getIdActaPartido() {
		return idActaPartido;
	}
	public void setIdActaPartido(long idActaPartido) {
		this.idActaPartido = idActaPartido;
	}
	public Short getValoracion() {
		return valoracion;
	}
	public void setValoracion(Short valoracion) {
		this.valoracion = valoracion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Boolean getVictoria() {
		return victoria;
	}
	public void setVictoria(Boolean victoria) {
		this.victoria = victoria;
	}
	public MatchData getMatchData() {
		return matchData;
	}
	public void setMatchData(MatchData matchData) {
		this.matchData = matchData;
	}
	
	
	
}
