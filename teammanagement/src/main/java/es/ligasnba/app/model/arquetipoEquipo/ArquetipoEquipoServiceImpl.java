package es.ligasnba.app.model.arquetipoEquipo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;


@Service("ArquetipoEquipoService")
@Transactional
public class ArquetipoEquipoServiceImpl implements ArquetipoEquipoService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ArquetipoEquipoDao asientodao;
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setAsientoDao(ArquetipoEquipoDao asientodao) {
		this.asientodao = asientodao;
	}
	
	
	public List<ArquetipoEquipoDto> findArquetiposActivosByCompeticion(long idCompeticion){
		return this.asientodao.findArquetiposActivosByCompeticion(idCompeticion);
	}
	
}
