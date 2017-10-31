package es.ligasnba.app.model.arquetipoEquipo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.ligasnba.app.util.exceptions.InstanceNotFoundException;


@Service("ArquetipoEquipoService")
@Transactional
public class ArquetipoEquipoServiceImpl implements ArquetipoEquipoService{

	@SuppressWarnings("unused")
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private ArquetipoEquipoDao arquetipodao;
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public void setAsientoDao(ArquetipoEquipoDao asientodao) {
		this.arquetipodao = asientodao;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public List<ArquetipoEquipoDto> findArquetiposActivosByCompeticion(long idCompeticion){
		return this.arquetipodao.findArquetiposActivosByCompeticion(idCompeticion);
	}
	
	@Override
	@Transactional(readOnly=true)
	public ArquetipoEquipoDto findArquetipoById(long idArquetipo) throws InstanceNotFoundException{
		return this.arquetipodao.findArquetiposActivosById(idArquetipo);
	}
	
	
}
