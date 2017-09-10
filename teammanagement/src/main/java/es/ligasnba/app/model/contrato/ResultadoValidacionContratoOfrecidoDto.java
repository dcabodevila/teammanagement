package es.ligasnba.app.model.contrato;

public class ResultadoValidacionContratoOfrecidoDto {
	
	private boolean valido;
	private String motivosNoValido;

	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	public String getMotivosNoValido() {
		return motivosNoValido;
	}
	public void setMotivosNoValido(String motivosNoValido) {
		this.motivosNoValido = motivosNoValido;
	}


}
