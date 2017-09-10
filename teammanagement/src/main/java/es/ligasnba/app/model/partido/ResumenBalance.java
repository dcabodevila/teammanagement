package es.ligasnba.app.model.partido;

public class ResumenBalance implements Comparable<ResumenBalance>{

	private Short numeroVictorias;
	private Short numeroDerrotas;
	
	public Short getNumeroVictorias() {
		return numeroVictorias;
	}
	public void setNumeroVictorias(Short numeroVictorias) {
		this.numeroVictorias = numeroVictorias;
	}
	public Short getNumeroDerrotas() {
		return numeroDerrotas;
	}
	public void setNumeroDerrotas(Short numeroDerrotas) {
		this.numeroDerrotas = numeroDerrotas;
	}
	@Override
	public int compareTo(ResumenBalance o) {
		
		final float ratio1 = getRatio(this.numeroVictorias, this.numeroDerrotas);
		final float ratio2 = getRatio(o.numeroVictorias, o.numeroDerrotas);
		
		if (ratio1==ratio2){
			return 0;
		}
		if (ratio1>ratio2){
			return 1;
		}
		if (ratio1<ratio2){
			return -1;
		}
		return 0;
	}

	public float getRatio(Short numeroVictorias, Short numeroDerrotas){
		if ((numeroVictorias==null) || (numeroDerrotas==null)){
			return 0;
		}
		
		int jugados = numeroVictorias + numeroDerrotas;
		return (jugados>0 ? numeroVictorias / jugados : 0); 
	}
	
}
