package es.ligasnba.app.model.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import es.ligasnba.app.model.actapartido.ActaPartido;
import es.ligasnba.app.model.clasificacion.Clasificacion;
import es.ligasnba.app.model.clasificacion.ClasificacionData;
import es.ligasnba.app.model.equipo.Equipo;
import es.ligasnba.app.model.estadistica.Estadistica;
import es.ligasnba.app.model.estadistica.StatData;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.jugador.PlayerData;
import es.ligasnba.app.model.jugador.PlayerDataNextSeason;
import es.ligasnba.app.model.jugadordefault.JugadorDefault;
import es.ligasnba.app.model.lineacontrato.LineaContrato;
import es.ligasnba.app.model.partido.MatchData;
import es.ligasnba.app.model.partido.Partido;
import es.ligasnba.app.model.traspaso.TradeData;
import es.ligasnba.app.model.traspaso.Traspaso;
import es.ligasnba.app.model.usuario.UserData;
import es.ligasnba.app.util.constants.Constants;






public class ViewMapper {
	

	

	public ViewMapper() {
		// TODO Auto-generated constructor stub
	}

	public static MatchData mapMatch(Partido p){	
		MatchData  matchdata = new MatchData();
		
		matchdata.setIdPartido( p.getIdPartido() );
		matchdata.setFecha(p.getFecha());
//		matchdata.setEquipoLocal(p.getEquipoLocal().getnombre());
//		matchdata.setIdEquipoLocal(p.getEquipoLocal().getIdEquipo());
//		matchdata.setIdEquipoVisitante(p.getEquipoVisitante().getIdEquipo());
//		matchdata.setEquipoVisitante(p.getEquipoVisitante().getnombre());
		
//		ActaPartido actapartido = p.getActasPartido();
//		if (actapartido!=null){
//			 
//			matchdata.setIdActaPartido(actapartido.getIdActaPartido());
//			matchdata.setResultadoLocal(actapartido.getResultadoLocal());
//			matchdata.setResultadoVisitante(actapartido.getResultadoVisitante());
//			matchdata.setRevisado(actapartido.isRevisado());
//		}
//		matchdata.setTemporada(p.getTemporada().getNombre());
		
		return matchdata;
		
		
	}
	
	public static List<MatchData> mapMatches(List<Partido> listaPartidos){
		List<MatchData> listdata = new ArrayList<MatchData>();
		for (Partido p: listaPartidos){
			listdata.add( mapMatch(p) );
		}
		
		return listdata;
	}
	
	public static StatData mapStat(Estadistica e){
		
		StatData  statdata= new StatData();

		statdata.setNombreJugador(e.getJugador().getNombre());
		statdata.setIdEstadistica(e.getIdEstadistica());
		statdata.setPuntos(e.getPuntos());
		statdata.setRebotes(e.getRebotes());
		statdata.setAsistencias(e.getAsistencias());
		statdata.setMinutos(e.getMinutos());
		return statdata;
		
		
	}
	
	public static List<StatData> mapStats(List<Estadistica> listaStats){
		List<StatData> listdata = new ArrayList<StatData>();
		for (Estadistica e: listaStats){
			listdata.add( mapStat(e) );
		}
		
		return listdata;
	}
	public static PlayerData mapPlayer(Jugador j){
		
		PlayerData playerdata = new PlayerData();
		playerdata.setIdJugador(j.getIdJugador());
		playerdata.setNombre(j.getNombre());
		if (j.getPosicion1()!=null){
			playerdata.setPosicion1(j.getPosicion1().toString());
		}
		if (j.getPosicion2()!=null){
			playerdata.setPosicion2(j.getPosicion2().toString());
		}
		playerdata.setMedia(j.getMedia());
		playerdata.setEdad(CommonFunctions.calcularEdad(j.getFechaNacimiento()));
		playerdata.setImagen(j.getImagen());
		playerdata.setMinutos(j.getMinutos());
		playerdata.setNextSalary(getNextSalary(j));
		playerdata.setContractYears(getYears(j));
		playerdata.setMoneyInterest(j.getMoneyInterest());
		playerdata.setWinningInterest(j.getWinningInterest());
		playerdata.setLoyaltyInterest(j.getLoyaltyInterest());
		if ((j.getJugadorDefault()!=null) && (j.getJugadorDefault().getEquipoDefault()!=null)){
			playerdata.setEquipoOriginal(j.getJugadorDefault().getEquipoDefault().getnombre());
			playerdata.setLogoEquipoOriginal(j.getJugadorDefault().getEquipoDefault().getLogo());
		}
		
		return playerdata;
		
		
	}

	
	public static PlayerData mapDefaultPlayer(JugadorDefault j){
		
		PlayerData playerdata = new PlayerData();
		playerdata.setIdJugador(j.getIdJugadorDefault());
		playerdata.setNombre(j.getNombre());
		if (j.getPos1()!=null){
			playerdata.setPosicion1(j.getPos1().toString());
		}
		if (j.getPos2()!=null){
			playerdata.setPosicion2(j.getPos2().toString());
		}
		playerdata.setMedia(j.getMedia());
		final Integer edad = CommonFunctions.calcularEdad(j.getFechaNacimiento());
		playerdata.setEdad(edad);
		playerdata.setImagen(j.getImagen());
		if (j.getEquipoDefault()!=null){
			playerdata.setEquipoOriginal(j.getEquipoDefault().getnombre());
			playerdata.setLogoEquipoOriginal(j.getEquipoDefault().getLogo());
		}
		playerdata.setContractYears(getDefaultYears(j));
		return playerdata;
		
		
	}
	
	private static List<LineaContrato> getContractLines(Jugador j){
				
		List<LineaContrato> lineasActuales = new ArrayList<LineaContrato>();
		
		if (j.getContrato() == null)
			return lineasActuales;
		
		List<LineaContrato> lineas = j.getContrato().getListLineasContrato();
		
		 
		
		for (int i=0; i<lineas.size();i++){
			
			LineaContrato linea = lineas.get(i);
			
			if (linea.getTemporada().getIdTemporada() == j.getCompeticion().getIdTemporadaActual()){
				for (int x=i; x<lineas.size(); x++)
					lineasActuales.add(lineas.get(x));
			}
							
		}
		return lineasActuales;
		
	}	
	
	
	public static List<PlayerData> mapPlayers(List<Jugador> listaJugadores){
		List<PlayerData> listdata = new ArrayList<PlayerData>();
		for (Jugador j: listaJugadores){
			listdata.add( mapPlayer(j) );
		}
		
		return listdata;
	}
	

	public static List<PlayerData> mapDefaultPlayers(List<JugadorDefault> listaJugadores){
		List<PlayerData> listdata = new ArrayList<PlayerData>();
		for (JugadorDefault j: listaJugadores){
			listdata.add( mapDefaultPlayer(j) );
		}
		
		return listdata;
	}
	

	

	public static int getSalary(Jugador j){
			
		BigDecimal salario = new BigDecimal(0);
		
		if (j.getContrato()==null)
			return salario.intValue();		
		
		List<LineaContrato> lineas =j.getContrato().getListLineasContrato();
		
		for (int i=0; i<lineas.size();i++){
			
			LineaContrato linea = lineas.get(i);
			
			if (linea.getTemporada().getIdTemporada() == j.getCompeticion().getIdTemporadaActual())
				return linea.getSalario().intValue();
							
		}
		
		
		return salario.intValue();
		
	}
	
	public static BigDecimal getNextSalary(Jugador j){
		
		BigDecimal salario = new BigDecimal(0);
		
		if (j.getContrato()==null)
			return salario;		
		
		List<LineaContrato> lineas =j.getContrato().getListLineasContrato();
		
		for (LineaContrato linea : lineas){
						
			if (linea.getTemporada().getIdTemporada() > j.getCompeticion().getIdTemporadaActual()){
				return linea.getSalario();
			}
							
		}
		
		
		return null;
		
	}
	
	public static int getDefaultYears(JugadorDefault j){
		
		int count=0;
		
		if (j.getSalario1()!=null){
			count++;
			if (j.getSalario2()!=null){
				count++;
				if (j.getSalario3()!=null){
					count++;
					if (j.getSalario4()!=null){
						count++;

					}
					
				}

			}
			
		}	
		
		return count;
			
	}
	
	public static int getYears(Jugador j){
		
		int count=0;
		
		if (j.getContrato()!=null){
			
			
			for (int i=0;i<j.getContrato().getListLineasContrato().size();i++){
				if (j.getContrato().getListLineasContrato().get(i).getTemporada().getIdTemporada()>=j.getCompeticion().getIdTemporadaActual())
					count++;				
			}
				
		}
		
		
		return count;
			
	}
	
	public static UserData mapUser(Equipo e){
	
		UserData userdata = new UserData();
		
		userdata.setIdEquipo(e.getIdEquipo());
		
		//No pasamos los datos del admin, ya que no permitimos bora
		if (e.getUsuario()!=null){
			
			userdata.setIdUsuario(e.getUsuario().getIdUsuario());
			userdata.setNombreUsuario(e.getUsuario().getLogin());
		}
		else
			{
			userdata.setNombreUsuario("");
			userdata.setIdUsuario(0);
			}
		userdata.setNombreEquipo(e.getnombre());
		
		return userdata;
		
	}
	
	public static List<UserData> mapUsers(List<Equipo> listaEquipos){
		
		List<UserData> listdata = new ArrayList<UserData>();
		
		for (Equipo e: listaEquipos)
			listdata.add( mapUser(e) );
		
		
		return listdata;
		
	}
	
	
	
	public static TradeData mapTrade(Traspaso t){
		
		TradeData tradedata = new TradeData();
		
		tradedata.setIdTraspaso(t.getIdTraspaso());
		tradedata.setIdEquipoPropone(t.getEquipoOrigen().getIdEquipo());
		tradedata.setNombreEquipoPropone(t.getEquipoOrigen().getnombre());
		tradedata.setIdEquipoRecibe(t.getEquipoDestino().getIdEquipo());
		tradedata.setNombreEquipoRecibe(t.getEquipoDestino().getnombre());
		
		List<Jugador> jugadoresOfrecidos = t.getListaJugadoresOfrecidos();
		
		
		for (int i=0; i< jugadoresOfrecidos.size();i++)
				
			tradedata.getNombresJugadoresOfrecidos().add(jugadoresOfrecidos.get(i).getNombre());
		
		
		List<Jugador> jugadoresRecibidos = t.getListaJugadoresRecibidos();
		
		for (int i=0; i< jugadoresRecibidos.size();i++)
			
			tradedata.getNombresJugadoresRecibidos().add(jugadoresRecibidos.get(i).getNombre());

		return tradedata;
		
	}
	
	public static List<TradeData> mapTrades(List<Traspaso> listaTraspasos){
		
		List<TradeData> listdata = new ArrayList<TradeData>();
		
		for (Traspaso e: listaTraspasos)
			listdata.add( mapTrade(e) );
		
		
		return listdata;
		
	}
	
	public static ClasificacionData mapClasificacion(Clasificacion c){
		
		ClasificacionData clasificacion = new ClasificacionData();
		
		if (c.getEquipo()!=null){
			clasificacion.setNombreEquipo(c.getEquipo().getnombre());
			clasificacion.setImage( c.getEquipo().getLogo());
			if (c.getEquipo().getUsuario()!=null)
				clasificacion.setNombreUsuario(c.getEquipo().getUsuario().getLogin());
			else 	
				clasificacion.setNombreUsuario("");
		}
		
		clasificacion.setIdEquipo(c.getEquipo().getIdEquipo());
		clasificacion.setDerrotas(c.getDerrotas());
		clasificacion.setVictorias(c.getVictorias());
			
		
		return clasificacion;
		
	}
	
	public static List<ClasificacionData> mapClasificaciones(List<Clasificacion> listaClasificaciones){
		
		List<ClasificacionData> listdata = new ArrayList<ClasificacionData>();
		
		for (Clasificacion c: listaClasificaciones)
			listdata.add( mapClasificacion(c) );
		
		
		return listdata;
	}
	
	
	
}

