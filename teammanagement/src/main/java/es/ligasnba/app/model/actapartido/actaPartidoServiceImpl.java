package es.ligasnba.app.model.actapartido;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.model.partido.ResumenBalance;




@Service("actaPartidoService")
@Transactional
public class actaPartidoServiceImpl implements actaPartidoService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ActaPartidoDao actapartidodao;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setActaPartidoDao(ActaPartidoDao actapartidodao) {
		this.actapartidodao = actapartidodao;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResumenBalance findBalanceEquipo(long idEquipo, long idTemporada, Boolean isPlayOffs){
		return this.actapartidodao.findBalanceEquipo(idEquipo, idTemporada, isPlayOffs);
		
	}

}

