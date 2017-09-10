package es.ligasnba.app.model.estadistica;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;






@Service("statsService")
@Transactional
public class statsServiceImpl implements statsService{

	@Autowired
	private EstadisticaDao estadisticadao;
	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public void setEstadisticaDao(EstadisticaDao estadisticadao) {
		this.estadisticadao = estadisticadao;
	}
	public void settransactionManager(PlatformTransactionManager t) {
		transactionManager = t;
	}
	
	
	
	public List<Estadistica> getStatsByGame(long idActaPartido, String field, String order, long idEquipo, int startIndex, int count){
		List<Estadistica> stats = estadisticadao.findStatsByGameAndTeam(idActaPartido, field, order,idEquipo, startIndex, count);
		return stats;

	}


	


	
}
