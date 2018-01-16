DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS equipo;
DROP TABLE IF EXISTS equipodefault;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS traspaso;
DROP TABLE IF EXISTS contrato;
DROP TABLE IF EXISTS competicion;
DROP TABLE IF EXISTS usuarios_liga;
DROP TABLE IF EXISTS temporada;
DROP TABLE IF EXISTS lineacontrato;
DROP TABLE IF EXISTS jugadores_ofrecidos;
DROP TABLE IF EXISTS jugadores_recibidos;
DROP TABLE IF EXISTS partido;
DROP TABLE IF EXISTS actapartido;
DROP TABLE IF EXISTS clasificacion;
DROP TABLE IF EXISTS estadistica;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS competitionrol;
DROP TABLE IF EXISTS noticia;
DROP TABLE IF EXISTS jugadordefault;
DROP TABLE IF EXISTS historico_equipos_jugador;
DROP TABLE IF EXISTS tipo_competicion;
DROP TABLE IF EXISTS tipo_estado_competicion;
DROP TABLE IF EXISTS posicion;
DROP TABLE IF EXISTS valoracion;
CREATE TABLE usuario (
  idUsuario BIGINT NOT NULL AUTO_INCREMENT,
  nombreUsuario VARCHAR(50) NOT NULL,
  pass VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  userValue INT,
  userState INT,
  activateKey VARCHAR(50) NOT NULL,
  activated BOOLEAN NOT NULL,
  PRIMARY KEY (idUsuario)
)
ENGINE = InnoDB;
CREATE TABLE rol (
  idRol BIGINT NOT NULL AUTO_INCREMENT,
  rol INT,
  idUsuario BIGINT REFERENCES usuario(idUsuario),
  PRIMARY KEY(idRol)
)
ENGINE = InnoDB;
CREATE TABLE competitionrol (
  idRol BIGINT NOT NULL AUTO_INCREMENT,
  rol INT,
  idUsuario BIGINT REFERENCES usuario(idUsuario),
  idCompeticion BIGINT REFERENCES competicion(idCompeticion),
  PRIMARY KEY(idRol)
)
ENGINE = InnoDB;
CREATE TABLE equipo (
  idEquipo BIGINT NOT NULL AUTO_INCREMENT,
  nombreEquipo VARCHAR(50) NOT NULL,
  logo VARCHAR(50),
  idUsuario BIGINT REFERENCES usuario(idUsuario),
  idCompeticion BIGINT REFERENCES competicion(idCompeticion),
  fechaUltimoContrato datetime,
  conference SMALLINT,
  division SMALLINT,
  version BIGINT,
  presupuestoActual DECIMAL(15,2) default 110000000,
  presupuestoProximaTemporada DECIMAL(15,2) default 71000000,
  idEquipoOriginal INT REFERENCES equipodefault(idEquipo),
  midLevelExceptionUsed BOOLEAN default false,
  idArquetipo BIGINT DEFAULT 1 references arquetipoEquipo(idArquetipo),
  PRIMARY KEY(idEquipo)
)ENGINE = InnoDB	;
CREATE TABLE equipodefault (
  idEquipo INT NOT NULL,
  nombreEquipo VARCHAR(50) NOT NULL,
  logo VARCHAR(50),
  conference SMALLINT,
  division SMALLINT,
  abreviatura VARCHAR(3),
  PRIMARY KEY(idEquipo)
)
ENGINE = InnoDB	;
CREATE TABLE jugador (
  idJugador BIGINT NOT NULL AUTO_INCREMENT,
  idCompeticion BIGINT NOT NULL REFERENCES competicion(idCompeticion),
  idEquipo BIGINT REFERENCES equipo(idEquipo),
  idContrato BIGINT REFERENCES contrato(idContrato),
  idEquipoOriginal INT REFERENCES equipodefault(idEquipo),
  idJugadorOriginal BIGINT REFERENCES jugadordefault(idJugadorDefault),  
  nombreJugador VARCHAR(50) NOT NULL,
  imagen VARCHAR(100) default "unknown.jpg",
  media INT default 40,
  fechaNacimiento datetime,
  moneyInterest INT default 10,
  winningInterest INT default 5,
  loyaltyInterest INT default 5,
  pos1 INT,
  pos2 INT,
  minutos INT,
  cache DECIMAL(15,2) default 473604,
  yearsPro INT default 1,
  aprobado BOOLEAN default false,
  retirado BOOLEAN default false,
  PRIMARY KEY(idJugador)
)
ENGINE = InnoDB	;
CREATE TABLE traspaso (
  idTraspaso BIGINT NOT NULL AUTO_INCREMENT,
  idEquipoOrigen BIGINT REFERENCES equipo(idEquipo),
  idEquipoDestino BIGINT REFERENCES equipo(idEquipo),
  comentario VARCHAR(500),
  importe DECIMAL(15,2),
  aprobado BOOLEAN,
  fecha datetime,
  idContrato BIGINT REFERENCES contrato(idContrato),
  PRIMARY KEY(idTraspaso)
)
ENGINE = InnoDB	;
CREATE TABLE contrato (
  idContrato BIGINT NOT NULL AUTO_INCREMENT,
  idJugador BIGINT REFERENCES jugador(idJugador),
  idJugadorOfrecido BIGINT REFERENCES jugador(idJugador),
  idEquipo  BIGINT REFERENCES equipo(idEquipo),
  fecha datetime,  
  firmado BOOLEAN,
  useMidLevelException BOOLEAN default false,
  idTraspaso BIGINT REFERENCES traspaso(idTraspaso),
  pendiente BOOLEAN default false,
  PRIMARY KEY (idContrato) 
) ENGINE=InnoDB;
CREATE TABLE lineacontrato (
  idLineaContrato BIGINT NOT NULL AUTO_INCREMENT,
  idContrato BIGINT REFERENCES contrato(idContrato),
  idTemporada BIGINT REFERENCES temporada(idTemporada),
  salario DECIMAL(15,2),		
  opcionEquipo BOOLEAN,
  opcionJugador BOOLEAN,
  PRIMARY KEY (idLineaContrato) 
) ENGINE=InnoDB;
CREATE TABLE competicion (
  idCompeticion BIGINT NOT NULL AUTO_INCREMENT,
  idAdmin BIGINT REFERENCES usuario(idUsuario),
  nombre VARCHAR(50) NOT NULL,
  clave2k VARCHAR(50) NOT NULL,
  idTemporadaActual BIGINT,
  numVueltas INT,
  fechaCierreMercado timestamp,
  actualDate datetime,
  startDate datetime,
  finishDate datetime,
  paused BOOLEAN default true,
  offSeasonStartDate date,
  offSeasonFinishDate date,
  idTipoCompeticion SMALLINT REFERENCES tipo_competicion(idTipoCompeticion),
  idTipoEstado SMALLINT REFERENCES tipo_estado_competicion(idTipoEstado),
  estado INT,
  salaryCap DECIMAL(15,2),
  salaryTop DECIMAL(15,2),
  mercadoAbierto BOOLEAN default true,
  fechaComienzoRS date,
  fechaComienzoPO date,
  maxPartidosSemana int default 3,
  minPartidosSemana int default 4,  
  PRIMARY KEY (idCompeticion) 
) ENGINE=InnoDB;
CREATE TABLE usuarios_liga(
  idCompeticion BIGINT NOT NULL,
  idUsuario BIGINT NOT NULL,
  PRIMARY KEY (`idUsuario`,`idCompeticion`) USING BTREE
)ENGINE=InnoDB;
CREATE TABLE temporada(
  idTemporada BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  idCompeticion BIGINT REFERENCES competicion(idCompeticion),
  orden SMALLINT,
  PRIMARY KEY (idTemporada)
)ENGINE=InnoDB;
CREATE TABLE clasificacion (
  idClasificacion BIGINT NOT NULL AUTO_INCREMENT,  
  idEquipo BIGINT REFERENCES equipo(idEquipo),
  idTemporada BIGINT REFERENCES temporada(idTemporada),
  victorias INT,
  empates INT,
  derrotas INT,
  PRIMARY KEY (idClasificacion)
)
ENGINE = InnoDB;
CREATE TABLE jugadores_ofrecidos(
  idJugador BIGINT NOT NULL,
  idTraspaso BIGINT NOT NULL,
  PRIMARY KEY (`idJugador`,`idTraspaso`) USING BTREE
)ENGINE=InnoDB;
CREATE TABLE jugadores_recibidos(
  idJugador BIGINT NOT NULL,
  idTraspaso BIGINT NOT NULL,
  PRIMARY KEY (`idJugador`,`idTraspaso`) USING BTREE
)ENGINE=InnoDB;
CREATE TABLE partido(
  idPartido BIGINT NOT NULL AUTO_INCREMENT,
  fecha datetime,
  idTemporada BIGINT REFERENCES temporada(idTemporada),
  idEquipoLocal BIGINT REFERENCES equipo(idEquipo),
  idEquipoVisitante BIGINT REFERENCES equipo(idEquipo),
  validado BOOLEAN,
  discrepancia BOOLEAN,
  playoff BOOLEAN default false,
  PRIMARY KEY(idPartido)
)ENGINE=InnoDB;
CREATE TABLE estadistica(
  idEstadistica BIGINT NOT NULL AUTO_INCREMENT,
  idJugador BIGINT REFERENCES jugador(idJugador),
  idActaPartido BIGINT REFERENCES actapartido(idActaPartido),
  idEquipoJugador BIGINT REFERENCES equipo(idEquipo),
  puntos INT,
  asistencias INT,
  rebotes INT,
  minutos INT,
  PRIMARY KEY(idEstadistica)
)ENGINE=InnoDB;
CREATE TABLE actapartido(
  idActaPartido BIGINT NOT NULL AUTO_INCREMENT,
  idPartido BIGINT REFERENCES partido(idPartido),  
  idUsuarioValorador BIGINT REFERENCES usuario(idUsuario),
  idUsuarioValorado BIGINT REFERENCES usuario(idUsuario),
  idEquipoValorador BIGINT REFERENCES equipo(idEquipo),
  idEquipoValorado BIGINT REFERENCES equipo(idEquipo),
  valoracion SMALLINT,
  comentario VARCHAR(1000),
  victoria BOOLEAN,
  PRIMARY KEY(idActaPartido)
)ENGINE=InnoDB;
Create TABLE noticia(
  idNoticia BIGINT NOT NULL AUTO_INCREMENT,
  idEquipo BIGINT REFERENCES equipo(idEquipo),
  idCompeticion BIGINT REFERENCES competicion(idCompeticion),
  texto VARCHAR(1000),
  fecha datetime,
  mensajeNuevo BOOLEAN DEFAULT true,
  notificar BOOLEAN DEFAULT false,  
  PRIMARY KEY(idNoticia)
)
ENGINE=InnoDB;
Create TABLE jugadordefault(
  idJugadorDefault BIGINT NOT NULL,
  nombre VARCHAR(100),
  imagen VARCHAR(100) DEFAULT "unknown.jpg",
  media INT default 40,
  fechaNacimiento datetime,
  pos1 INT,
  pos2 INT,
  idEquipoDefault INT REFERENCES equipodefault(idEquipo),
  salario1 DECIMAL(15,2),
  salario2 DECIMAL(15,2),
  salario3 DECIMAL(15,2),
  salario4 DECIMAL(15,2),
  moneyInterest INT default 10,
  winningInterest INT default 5,
  loyaltyInterest INT default 5,
  cache DECIMAL(15,2) default 473604,
  yearsPro INT default 0,      
  PRIMARY KEY(idJugadorDefault)
)
ENGINE=InnoDB;
Create TABLE tipo_competicion(
  idTipoCompeticion SMALLINT NOT NULL,
  nombre VARCHAR(50),
  descripcion VARCHAR(100),
  PRIMARY KEY(idTipoCompeticion)
)
ENGINE=InnoDB;
Create TABLE tipo_estado_competicion(
  idTipoEstado SMALLINT NOT NULL,
  nombre VARCHAR(50),
  descripcion VARCHAR(100),
  PRIMARY KEY(idTipoEstado)
)
ENGINE=InnoDB;
CREATE TABLE historico_equipos_jugador (
  idHistoricoEquipos BIGINT NOT NULL AUTO_INCREMENT, 
  idJugador BIGINT NOT NULL REFERENCES jugador(idJugador),
  idEquipo BIGINT NOT NULL REFERENCES equipo(idEquipo),
  idTemporada BIGINT NOT NULL REFERENCES temporada(idTemporada),
  fecha DATE,  
  PRIMARY KEY(idHistoricoEquipos)
)ENGINE=InnoDB;
Create TABLE posicion(
  idPosicion SMALLINT NOT NULL,
  nombre VARCHAR(3),
  PRIMARY KEY(idPosicion)
)ENGINE=InnoDB;
CREATE TABLE valoracion (
  idValoracion BIGINT NOT NULL AUTO_INCREMENT,
  idUsuario BIGINT REFERENCES usuario(idUsuario),
  idUsuarioValorado BIGINT REFERENCES usuario(idUsuario),
  idPartido BIGINT REFERENCES partido(idPartido),
  valoracion SMALLINT, 
  fecha datetime,
  comentario VARCHAR(250),
  PRIMARY KEY(idValoracion)
)ENGINE = InnoDB;
Create TABLE asiento(
  idAsiento BIGINT NOT NULL AUTO_INCREMENT,
  idEquipo BIGINT REFERENCES equipo(idEquipo),
  concepto VARCHAR(500),
  fecha datetime,
  idTemporada BIGINT REFERENCES temporada(idTemporada),
  importe DECIMAL(15,2) default 0,
  regular BOOLEAN default true,
  PRIMARY KEY(idAsiento)
)
ENGINE=InnoDB;
Create TABLE arquetipoEquipo(
  idArquetipo BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(150),
  descripcion VARCHAR(350),
  idCompeticion BIGINT REFERENCES competicion(idCompeticion),
  activo boolean default true,
  ingresoFijo DECIMAL(15,2) default 71000000,
  ingresoPartidoJugadoRS DECIMAL(15,2) default 1000000,
  ingresoPartidoGanadoRS DECIMAL(15,2) default 250000,
  ingresoClasificacionPO DECIMAL(15,2) default 3000000,
  ingresoPartidoJugadoPO DECIMAL(15,2) default 500000,
  ingresoPartidoGanadoPO DECIMAL(15,2) default 250000,
  ingresoRondasPOGanadas DECIMAL(15,2) default 500000,
  numPartidosGanadosObjetivo1 SMALLINT default 0,
  numPartidosGanadosObjetivo2 SMALLINT default 0,
  ingresoObjetivo1 DECIMAL(15,2) default 0,
  ingresoObjetivo2 DECIMAL(15,2) default 0,
  numRondasGanadasObjetivo3 SMALLINT default 0,
  ingresoObjetivo3 DECIMAL(15,2) default 0,
  ingresoCampeon DECIMAL(15,2) default 0,
  PRIMARY KEY(idArquetipo)
)
ENGINE=InnoDB;
CREATE TABLE valoracion_ofertas (
  ID_VALORACION_OFERTA BIGINT NOT NULL AUTO_INCREMENT,
  ID_CONTRATO BIGINT REFERENCES contrato(idContrato),  
  ID_COMPETICION BIGINT REFERENCES competicion(idCompeticion),
  ID_EQUIPO BIGINT REFERENCES equipo(idEquipo),  
  NOMBRE_EQUIPO VARCHAR(50),
  ID_JUGADOR BIGINT REFERENCES jugador(idJugador),  
  NOMBRE_JUGADOR VARCHAR(50),
  SALARIO_T1 DECIMAL(15,2),
  SALARIO_T2 DECIMAL(15,2),
  SALARIO_T3 DECIMAL(15,2),
  VALORACION_MONEY DECIMAL(15,2),
  MONEY_INTEREST INT,
  VALORACION_WINNING DECIMAL(15,2),
  WINNING_INTEREST INT,
  VALORACION_LOYALTY DECIMAL(15,2),
  LOYALTY_INTEREST INT,
  VALORACION_GLOBAL DECIMAL(15,2),
  VALORACION_MAXIMA DECIMAL(15,2),
  VALORACION_GLOBAL_PREVIA DECIMAL(15,2),
  ES_SIGN_AND_TRADE BOOLEAN,
  FECHA DATE,
  PRIMARY KEY(ID_VALORACION_OFERTA)
)
ENGINE = InnoDB;
INSERT INTO tipo_competicion (idTipoCompeticion,nombre,descripcion) VALUES (1,'Draft','Equipos sin jugadores. GM eligen a sus jugadores');
INSERT INTO tipo_competicion (idTipoCompeticion,nombre,descripcion) VALUES (2,'Real','Plantillas reales de la NBA');
INSERT INTO tipo_estado_competicion (idTipoEstado,nombre,descripcion) VALUES (1,'Pretemporada','GM configuran sus plantillas');
INSERT INTO tipo_estado_competicion (idTipoEstado,nombre,descripcion) VALUES (2,'Temporada Regular ','Equipos disputando la temporada regular');
INSERT INTO tipo_estado_competicion (idTipoEstado,nombre,descripcion) VALUES (3,'PlayOffs','Equipos disputando los PlayOffs');
INSERT INTO tipo_estado_competicion (idTipoEstado,nombre,descripcion) VALUES (4,'Post-temporada','Renovaciones / Contrataciones FA');
INSERT INTO tipo_estado_competicion (idTipoEstado,nombre,descripcion) VALUES (5,'Draft','GM eligen a sus jugadores');
INSERT INTO equipodefault
(`idEquipo`,
`nombreEquipo`,
`logo`,
`conference`,
`division`,
`abreviatura`)
VALUES
(1,'Atlanta Hawks','hawks.gif',1,3,'ATL'),
(2,'Boston Celtics','celtics.gif',1,1, 'BOS'),
(3,'Brooklyn Nets','nets.gif',1,1,'BRK'),
(4,'Chicago Bulls','bulls.gif',1,2, 'CHI'),
(5,'Charlotte Hornets','hornets.gif',1,3,'CHO'),
(6,'Cleveland Cavaliers','cavaliers.gif',1,2,'CLE'),
(7,'Dallas Mavericks','mavericks.gif',2,6,'DAL'),
(8,'Denver Nuggets','nuggets.gif',2,4,'DEN'),
(9,'Detroit Pistons','pistons.gif',1,2,'DET'),
(10,'Golden State Warriors','warriors.gif',2,5,'GSW'),
(11,'Houston Rockets','rockets.gif',2,6,'HOU'),
(12,'Indiana Pacers','pacers.gif',1,2,'IND'),
(13,'Los Angeles Clippers','clippers.gif',2,5,'LAC'),
(14,'Los Angeles Lakers','lakers.gif',2,5,'LAL'),
(15,'Memphis Grizzlies','grizzlies.gif',2,6,'MEM'),
(16,'Miami Heat','heat.gif',1,3,'MIA'),
(17,'Milwaukee Bucks','bucks.gif',1,2,'MIL'),
(18,'Minnesota Timberwolves','timberwolves.gif',2,4,'MIN'),
(19,'New Orleans Pelicans','pelicans.gif',2,6,'NOP'),
(20,'New York Knicks','knicks.gif',1,1,'NYK'),
(21,'Oklahoma City Thunder','thunder.gif',2,4,'OKC'),
(22,'Orlando Magic','magic.gif',1,3,'ORL'), 
(23,'Philadelphia 76ers','sixers.gif',1,1,'PHI'), 
(24,'Phoenix Suns','suns.gif',2,5,'PHO'),
(25,'Portland Trail Blazers','trailblazers.gif',2,4,'POR'),
(26,'Sacramento Kings','kings.gif',2,5,'SAC'),
(27,'San Antonio Spurs','spurs.gif',2,6,'SAS'),
(28,'Toronto Raptors','raptors.gif',1,1,'TOR'),
(29,'Utah Jazz','jazz.gif',2,4,'UTA'),
(30,'Washington Wizards','wizards.gif',1,3,'WAS');
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (1,'LeBron James','lebron.png',94,{ts '1985-08-02 17:52:08.'},3,4,6,33285709.00,35607968.00,null,null,6,8,5,30000000.00,12);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (2,'Mike Conley','unknown.jpg',85,{ts '1987-08-02 17:52:08.'},1,null,15,28530608.00,30521115.00,32511623.00,34504132.00,5,5,10,20000000.00,8);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (3,'Russell Westbrook','unknown.jpg',93,{ts '1990-08-02 17:52:08.'},1,2,21,28530608.00,30670404.00,null,null,10,4,6,30000000.00,10);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (4,'James Harden','harden.png',94,{ts '1989-08-02 17:52:08.'},2,1,11,28299339.00,30431854.00,32703493.00,null,10,5,5,28000000.00,8);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (5,'DeMar DeRozan','unknown.jpg',89,{ts '1989-08-02 17:52:08.'},null,null,28,27739975.00,27739975.00,27739975.00,27739975.00,10,5,5,26000000.00,5);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (6,'Al Horford','unknown.jpg',86,{ts '1989-08-02 17:52:08.'},null,null,2,27734405.00,28928710.00,30123015.00,null,10,5,5,16000000.00,9);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (7,'Carmelo Anthony','melo.png',84,{ts '1989-08-02 17:52:08.'},null,null,20,26243760.00,27928140.00,null,null,10,5,5,17000000.00,12);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (8,'Damian Lillard','unknown.jpg',84,{ts '1989-08-02 17:52:08.'},null,null,25,26153057.00,27977689.00,29802321.00,31626953.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (9,'Chris Bosh','unknown.jpg',74,{ts '1989-08-02 17:52:08.'},null,null,16,25289390.00,26837720.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (10,'Dirk Nowitzki','dirk.png',79,{ts '1989-08-02 17:52:08.'},null,null,7,25000000.00,null,null,null,10,5,5,8000000.00,19);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (11,'Chris Paul','unknown.jpg',93,{ts '1989-08-02 17:52:08.'},null,null,11,24268959.00,null,null,null,10,5,5,20000000.00,9);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (12,'C.J. McCollum','unknown.jpg',87,{ts '1989-08-02 17:52:08.'},null,null,25,23962573.00,25759766.00,27556959.00,29354152.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (13,'Dwyane Wade','wade.png',80,{ts '1989-08-02 17:52:08.'},null,null,4,23800000.00,null,null,null,10,5,5,16000000.00,13);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (14,'Bradley Beal','unknown.jpg',82,null,null,null,30,23775506.00,25434263.00,27093019.00,28751775.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (15,'Anthony Davis','unknown.jpg',91,null,null,null,19,23775506.00,25434263.00,27093019.00,28751775.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (16,'Andre Drummond','unknown.jpg',90,null,null,null,9,23775506.00,25434263.00,27093019.00,28751775.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (17,'Hassan Whiteside','unknown.jpg',88,null,null,null,16,23775506.00,25434263.00,27093018.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (18,'Dwight Howard','unknown.jpg',81,null,null,null,5,23500000.00,23819725.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (19,'Chandler Parsons','unknown.jpg',79,null,null,null,15,23112004.00,24107258.00,25102511.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (20,'Harrison Barnes','unknown.jpg',80,null,null,null,7,23112004.00,24107258.00,25102512.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (21,'Marc Gasol','unknown.jpg',85,null,null,null,15,22642350.00,24119025.00,25595700.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (22,'Kevin Love','unknown.jpg',83,null,null,null,6,22642350.00,24119025.00,25595700.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (23,'DeAndre Jordan','unknown.jpg',82,null,null,null,13,22642350.00,24119025.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (24,'Brook Lopez','unknown.jpg',81,null,null,null,3,22642350.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (25,'Steven Adams','unknown.jpg',80,null,null,null,21,22471910.00,24157303.00,25842697.00,27528090.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (26,'Giannis Antetokounmpo','unknown.jpg',87,null,null,null,17,22471910.00,24157303.00,25842697.00,27528090.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (27,'Nicolas Batum','unknown.jpg',81,null,null,null,5,22434783.00,24000000.00,25565217.00,27130434.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (28,'LaMarcus Aldridge','unknown.jpg',83,null,null,null,27,21461010.00,22347015.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (29,'Rudy Gobert','unknown.jpg',85,null,null,null,31,21224719.00,22741573.00,24258427.00,25775281.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (30,'Victor Oladipo','unknown.jpg',82,null,null,null,21,21000000.00,21000000.00,21000000.00,21000000.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (31,'Ryan Anderson','unknown.jpg',79,null,null,null,11,19578455.00,20421546.00,21264635.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (32,'Paul George','unknown.jpg',83,null,null,null,12,19508958.00,20703384.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (33,'Kawhi Leonard','unknown.jpg',92,null,null,null,27,18868625.00,20099189.00,21329752.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (34,'Kyrie Irving','unknown.jpg',92,null,null,null,6,18868625.00,20099188.00,21329750.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (35,'Jimmy Butler','unknown.jpg',87,null,null,null,4,18696918.00,19841624.00,19841624.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (36,'Allen Crabbe','unknown.jpg',80,null,null,null,25,18500000.00,19332500.00,18500000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (37,'John Wall','unknown.jpg',90,null,null,null,30,18063850.00,19169800.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (38,'DeMarcus Cousins','unknown.jpg',90,null,null,null,19,18063850.00,null,null,null,10,5,5,26000000.00,6);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (39,'Wesley Matthews','unknown.jpg',79,null,null,null,7,17900000.00,18600000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (40,'Greg Monroe','unknown.jpg',80,null,null,null,17,17900000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (41,'Enes Kanter','unknown.jpg',75,null,null,null,21,17884176.00,18622514.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (42,'Klay Thompson','unknown.jpg',85,null,null,null,10,17826150.00,18988725.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (43,'Joakim Noah','unknown.jpg',77,null,null,null,20,17765000.00,18530000.00,19295000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (44,'Luol Deng','unknown.jpg',40,null,null,null,14,17190000.00,18000000.00,18810000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (45,'Evan Turner','unknown.jpg',40,null,null,null,25,17131148.00,17868852.00,18606557.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (46,'Evan Fournier','unknown.jpg',40,null,null,null,22,17000000.00,17000000.00,17000000.00,17000000.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (47,'Bismack Biyombo','unknown.jpg',40,null,null,null,22,17000000.00,17000000.00,18872365.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (48,'Goran Dragic','unknown.jpg',40,null,null,null,16,17000000.00,18110000.00,19220000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (49,'Kent Bazemore','unknown.jpg',40,null,null,null,1,16910113.00,18089887.00,19269662.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (50,'Ian Mahinmi','unknown.jpg',40,null,null,null,30,16661641.00,15944154.00,15450051.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (51,'Draymond Green','unknown.jpg',40,null,null,null,10,16400000.00,17469565.00,18539130.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (52,'Tristan Thompson','unknown.jpg',40,null,null,null,6,16400000.00,17469565.00,18539130.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (53,'Reggie Jackson','unknown.jpg',40,null,null,null,9,16000000.00,17043478.00,18086956.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (54,'Tobias Harris','unknown.jpg',40,null,null,null,9,16000000.00,14800000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (55,'Dennis Schroder','unknown.jpg',40,null,null,null,1,15500000.00,15500000.00,15500000.00,15500000.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (56,'Jonas Valanciunas','unknown.jpg',40,null,null,null,28,15460675.00,16539326.00,17617977.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (57,'Tony Parker','unknown.jpg',40,null,null,null,27,15453126.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (58,'Timofey Mozgov','unknown.jpg',40,null,null,null,14,15280000.00,16000000.00,16720000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (59,'DeMarre Carroll','unknown.jpg',40,null,null,null,28,14800000.00,15400000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (60,'Thaddeus Young','unknown.jpg',40,null,null,null,12,14796348.00,13764045.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (61,'Eric Bledsoe','unknown.jpg',40,null,null,null,24,14500000.00,15000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (62,'Jamal Crawford','unknown.jpg',40,null,null,null,13,14264988.00,14500000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (63,'Ricky Rubio','unknown.jpg',40,null,null,null,31,14250000.00,14800000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (64,'Gorgui Dieng','unknown.jpg',40,null,null,null,18,14112360.00,15170787.00,16229213.00,17287640.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (65,'Khris Middleton','unknown.jpg',40,null,null,null,17,14100000.00,13000000.00,13000000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (66,'Robin Lopez','unknown.jpg',40,null,null,null,4,13788500.00,14357750.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (67,'J.R. Smith','unknown.jpg',40,null,null,null,6,13760000.00,14720000.00,15680000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (68,'Brandon Knight','unknown.jpg',40,null,null,null,24,13618750.00,14631250.00,15643750.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (69,'Marvin Williams','unknown.jpg',40,null,null,null,5,13168750.00,14087500.00,15006250.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (70,'Tyson Chandler','unknown.jpg',40,null,null,null,24,13000000.00,13585000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (71,'Michael Kidd-Gilchrist','unknown.jpg',40,null,null,null,5,13000000.00,13000000.00,13000000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (72,'Eric Gordon','unknown.jpg',40,null,null,null,11,12943020.00,13500375.00,14057730.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (73,'Kenneth Faried','unknown.jpg',40,null,null,null,8,12921348.00,13764045.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (74,'Marcin Gortat','unknown.jpg',40,null,null,null,30,12782609.00,13565218.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (75,'Cody Zeller','unknown.jpg',40,null,null,null,5,12584270.00,13528090.00,14471910.00,15415730.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (76,'Miles Plumlee','unknown.jpg',40,null,null,null,1,12500000.00,12500000.00,12500000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (77,'Nikola Vucevic','unknown.jpg',40,null,null,null,22,12250000.00,12750000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (78,'Wilson Chandler','unknown.jpg',40,null,null,null,8,12000000.00,12800000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (79,'Kemba Walker','unknown.jpg',40,null,null,null,5,12000000.00,12000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (80,'Jeremy Lin','unknown.jpg',40,null,null,null,3,12000000.00,12516746.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (81,'Derrick Favors','unknown.jpg',40,null,null,null,31,12000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (82,'Austin Rivers','unknown.jpg',40,null,null,null,13,11825000.00,12650000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (83,'Courtney Lee','unknown.jpg',40,null,null,null,20,11747890.00,12253780.00,12759670.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (84,'Solomon Hill','unknown.jpg',40,null,null,null,19,11747073.00,12252928.00,12758781.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (85,'John Henson','unknown.jpg',40,null,null,null,17,11672536.00,10827466.00,9982396.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (86,'Nikola Pekovic','unknown.jpg',40,null,null,null,18,11600000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (87,'Jordan Clarkson','unknown.jpg',40,null,null,null,14,11562500.00,12500000.00,13437500.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (88,'Monta Ellis','unknown.jpg',40,null,null,null,12,11230000.00,11700000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (89,'Alec Burks','unknown.jpg',40,null,null,null,31,10845506.00,11536515.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (90,'Omer Asik','unknown.jpg',40,null,null,null,19,10595505.00,11286516.00,11977527.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (91,'Joe Johnson','unknown.jpg',40,null,null,null,31,10505000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (92,'Mirza Teletovic','unknown.jpg',40,null,null,null,17,10500000.00,10500000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (93,'Terrence Ross','unknown.jpg',40,null,null,null,22,10500000.00,10500000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (94,'Jon Leuer','unknown.jpg',40,null,null,null,9,10497319.00,10002681.00,9508043.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (95,'Iman Shumpert','unknown.jpg',40,null,null,null,6,10300000.00,11000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (96,'Jared Dudley','unknown.jpg',40,null,null,null,24,10000000.00,9530000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (97,'Danny Green','unknown.jpg',40,null,null,null,27,10000000.00,10000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (98,'Meyers Leonard','unknown.jpg',40,null,null,null,25,9904495.00,10595506.00,11286515.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (99,'Al Jefferson','unknown.jpg',40,null,null,null,12,9769821.00,10000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (100,'Maurice Harkless','unknown.jpg',40,null,null,null,25,9662921.00,10337079.00,11011236.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (101,'Matthew Dellavedova','unknown.jpg',40,null,null,null,17,9607500.00,9607500.00,9607500.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (102,'Trevor Booker','unknown.jpg',40,null,null,null,3,9125000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (103,'Dwight Powell','unknown.jpg',40,null,null,null,7,9003125.00,9631250.00,10259375.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (104,'Jerryd Bayless','unknown.jpg',40,null,null,null,23,9000000.00,8575916.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (105,'Avery Bradley','unknown.jpg',40,null,null,null,2,8808989.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (106,'E Twaun Moore','unknown.jpg',40,null,null,null,19,8445024.00,8808685.00,8664928.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (107,'Kosta Koufos','unknown.jpg',40,null,null,null,26,8393000.00,8739500.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (108,'Markieff Morris','unknown.jpg',40,null,null,null,30,8000000.00,8600000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (109,'Garrett Temple','unknown.jpg',40,null,null,null,26,8000000.00,8000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (110,'Tyler Zeller','unknown.jpg',40,null,null,null,2,8000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (111,'Cory Joseph','unknown.jpg',40,null,null,null,28,7660000.00,8000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (112,'Corey Brewer','unknown.jpg',40,null,null,null,14,7600000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (113,'Andrew Wiggins','unknown.jpg',40,null,null,null,18,7574322.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (114,'Boris Diaw','unknown.jpg',40,null,null,null,31,7500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (115,'Darrell Arthur','unknown.jpg',40,null,null,null,8,7464912.00,7464912.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (116,'Trevor Ariza','unknown.jpg',40,null,null,null,11,7420912.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (117,'Channing Frye','unknown.jpg',40,null,null,null,6,7420912.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (118,'Al-Farouq Aminu','unknown.jpg',40,null,null,null,25,7319035.00,6957105.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (119,'Cole Aldrich','unknown.jpg',40,null,null,null,18,7300000.00,6956021.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (120,'D.J. Augustin','unknown.jpg',40,null,null,null,22,7250000.00,7250000.00,7250000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (121,'Boban Marjanovic','unknown.jpg',40,null,null,null,9,7000000.00,7000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (122,'Jeremy Lamb','unknown.jpg',40,null,null,null,5,7000000.00,7488372.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (123,'Lou Williams','unknown.jpg',40,null,null,null,13,7000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (124,'Jae Crowder','unknown.jpg',40,null,null,null,2,6796117.00,7305825.00,7815533.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (125,'Jabari Parker','unknown.jpg',40,null,null,null,17,6782392.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (126,'Lance Thomas','unknown.jpg',40,null,null,null,20,6655325.00,7119650.00,7583975.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (127,'Tarik Black','unknown.jpg',40,null,null,null,14,6655325.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (128,'Aron Baynes','unknown.jpg',40,null,null,null,9,6500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (129,'Matt Barnes','unknown.jpg',40,null,null,null,26,6400625.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (130,'Andrew Nicholson','unknown.jpg',40,null,null,null,3,6362998.00,6637002.00,6911007.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (131,'Ed Davis','unknown.jpg',40,null,null,null,25,6352531.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (132,'Marco Belinelli','unknown.jpg',40,null,null,null,1,6333333.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (133,'Wayne Ellington','unknown.jpg',40,null,null,null,16,6270000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (134,'Ramon Sessions','unknown.jpg',40,null,null,null,5,6270000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (135,'Isaiah Thomas','unknown.jpg',40,null,null,null,2,6261395.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (136,'Karl-Anthony Towns','unknown.jpg',40,null,null,null,18,6216840.00,7839435.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (137,'Ben Simmons','unknown.jpg',40,null,null,null,23,6168840.00,6434520.00,8113930.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (138,'Joel Embiid','unknown.jpg',40,null,null,null,23,6100266.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (139,'Spencer Hawes','unknown.jpg',40,null,null,null,17,6021175.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (140,'Josh McRoberts','unknown.jpg',40,null,null,null,16,6021175.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (141,'Ish Smith','unknown.jpg',40,null,null,null,9,6000000.00,6000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (142,'Brandan Wright','unknown.jpg',40,null,null,null,15,6000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (143,'Tyler Johnson','unknown.jpg',40,null,null,null,16,5881260.00,19245370.00,19245370.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (144,'Wesley Johnson','unknown.jpg',40,null,null,null,13,5881260.00,6134000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (145,'Alex Abrines','unknown.jpg',40,null,null,null,21,5725000.00,5455236.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (146,'D''Angelo Russell','unknown.jpg',40,null,null,null,14,5562360.00,7019698.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (147,'Brandon Ingram','unknown.jpg',40,null,null,null,14,5519400.00,5757120.00,7265485.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (148,'Aaron Gordon','unknown.jpg',40,null,null,null,22,5504420.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (149,'Deron Williams','unknown.jpg',40,null,null,null,3,5500000.00,5500000.00,5500000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (150,'Patrick Beverley','unknown.jpg',40,null,null,null,13,5500000.00,5000000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (151,'Josh Smith','unknown.jpg',40,null,null,null,9,5400000.00,5400000.00,5400000.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (152,'Jason Smith','unknown.jpg',40,null,null,null,30,5225000.00,5450000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (153,'Marcus Morris','unknown.jpg',40,null,null,null,9,5000000.00,5375000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (154,'C.J. Watson','unknown.jpg',40,null,null,null,22,5000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (155,'Jahlil Okafor','unknown.jpg',40,null,null,null,23,4995120.00,6313832.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (156,'Dante Exum','unknown.jpg',40,null,null,null,31,4992385.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (157,'Jaylen Brown','unknown.jpg',40,null,null,null,2,4956480.00,5169960.00,6534829.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (158,'Alexis Ajinca','unknown.jpg',40,null,null,null,19,4900000.00,5300000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (159,'Jameer Nelson','unknown.jpg',40,null,null,null,8,4736050.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (160,'Kyle Singler','unknown.jpg',40,null,null,null,21,4666500.00,4996000.00,5333500.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (161,'Marcus Smart','unknown.jpg',40,null,null,null,2,4538020.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (162,'Kristaps Porzingis','unknown.jpg',40,null,null,null,20,4503600.00,5697054.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (163,'Dragan Bender','unknown.jpg',40,null,null,null,24,4468800.00,4661280.00,5896519.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (164,'Devin Harris','unknown.jpg',40,null,null,null,7,4403000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (165,'Lavoy Allen','unknown.jpg',40,null,null,null,12,4300000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (166,'Lance Stephenson','unknown.jpg',40,null,null,null,12,4180000.00,4360000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (167,'Julius Randle','unknown.jpg',40,null,null,null,14,4149242.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (168,'Kyle O''Quinn','unknown.jpg',40,null,null,null,20,4100000.00,4250000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (169,'Mario Hezonja','unknown.jpg',40,null,null,null,22,4078320.00,5167231.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (170,'Kris Dunn','unknown.jpg',40,null,null,null,18,4046760.00,4221000.00,5348007.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (171,'Leandro Barbosa','unknown.jpg',40,null,null,null,24,4000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (172,'Nemanja Bjelica','unknown.jpg',40,null,null,null,18,3949999.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (173,'J.J. Barea','unknown.jpg',40,null,null,null,7,3903900.00,3710850.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (174,'Quincy Pondexter','unknown.jpg',40,null,null,null,19,3853931.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (175,'Nik Stauskas','unknown.jpg',40,null,null,null,23,3807146.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (176,'Willie Cauley-Stein','unknown.jpg',40,null,null,null,26,3704160.00,4696875.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (177,'Buddy Hield','unknown.jpg',40,null,null,null,26,3675480.00,3833760.00,4861208.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (178,'Will Barton','unknown.jpg',40,null,null,null,8,3533333.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (179,'Noah Vonleh','unknown.jpg',40,null,null,null,25,3505233.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (180,'Mike Miller','unknown.jpg',40,null,null,null,8,3500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (181,'Troy Daniels','unknown.jpg',40,null,null,null,15,3408520.00,3258540.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (182,'Emmanuel Mudiay','unknown.jpg',40,null,null,null,8,3381480.00,4294480.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (183,'Jamal Murray','unknown.jpg',40,null,null,null,8,3355320.00,3499800.00,4444746.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (184,'K.J. McDaniels','unknown.jpg',40,null,null,null,3,3333334.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (185,'Elfrid Payton','unknown.jpg',40,null,null,null,22,3332340.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (186,'Doug McDermott','unknown.jpg',40,null,null,null,21,3294994.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (187,'Zach LaVine','unknown.jpg',40,null,null,null,18,3202217.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (188,'T.J. Warren','unknown.jpg',40,null,null,null,24,3152931.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (189,'Stanley Johnson','unknown.jpg',40,null,null,null,9,3097800.00,3940402.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (190,'Marquese Chriss','unknown.jpg',40,null,null,null,24,3073800.00,3206160.00,4078236.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (191,'Mindaugas Kuzminskas','unknown.jpg',40,null,null,null,20,3028410.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (192,'James Ennis','unknown.jpg',40,null,null,null,15,3028410.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (193,'Seth Curry','unknown.jpg',40,null,null,null,7,3028410.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (194,'Rajon Rondo','unknown.jpg',40,null,null,null,4,3000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (195,'Tomas Satoransky','unknown.jpg',40,null,null,null,30,3000000.00,3129187.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (196,'Justin Hamilton','unknown.jpg',40,null,null,null,3,3000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (197,'Lucas Nogueira','unknown.jpg',40,null,null,null,28,2947305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (198,'Jusuf Nurkic','unknown.jpg',40,null,null,null,25,2947305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (199,'Frank Kaminsky','unknown.jpg',40,null,null,null,5,2847600.00,3627842.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (200,'Jakob Poeltl','unknown.jpg',40,null,null,null,28,2825640.00,2947320.00,3754886.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (201,'Justise Winslow','unknown.jpg',40,null,null,null,16,2705040.00,3448926.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (202,'Thon Maker','unknown.jpg',40,null,null,null,17,2684160.00,2799720.00,3569643.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (203,'Myles Turner','unknown.jpg',40,null,null,null,12,2569920.00,3410284.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (204,'Gary Harris','unknown.jpg',40,null,null,null,8,2550055.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (205,'Domantas Sabonis','unknown.jpg',40,null,null,null,21,2550000.00,2659800.00,3529555.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (206,'Richard Jefferson','unknown.jpg',40,null,null,null,6,2500000.00,2612500.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (207,'Malcolm Delaney','unknown.jpg',40,null,null,null,1,2500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (208,'Bruno Caboclo','unknown.jpg',40,null,null,null,28,2451225.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (209,'Ronnie Price','unknown.jpg',40,null,null,null,21,2442455.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (210,'Trey Lyles','unknown.jpg',40,null,null,null,31,2441400.00,3364249.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (211,'Dario Saric','unknown.jpg',40,null,null,null,23,2422560.00,2526840.00,3481986.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (212,'Taurean Waller-Prince','unknown.jpg',40,null,null,null,1,2422560.00,2526840.00,3481986.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (213,'Rodney Hood','unknown.jpg',40,null,null,null,31,2386864.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (214,'Shabazz Napier','unknown.jpg',40,null,null,null,25,2361360.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (215,'Clint Capela','unknown.jpg',40,null,null,null,11,2334520.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (216,'Devin Booker','unknown.jpg',40,null,null,null,24,2319360.00,3314365.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (217,'Georgios Papagiannis','unknown.jpg',40,null,null,null,26,2301360.00,2400480.00,3430288.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (218,'Cameron Payne','unknown.jpg',40,null,null,null,4,2203440.00,3263295.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (219,'Denzel Valentine','unknown.jpg',40,null,null,null,4,2186400.00,2280600.00,3377569.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (220,'Kyle Anderson','unknown.jpg',40,null,null,null,27,2151704.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (221,'Kelly Oubre','unknown.jpg',40,null,null,null,30,2093040.00,3208630.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (222,'Juan Hernangomez','unknown.jpg',40,null,null,null,8,2076840.00,2166360.00,3321030.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (223,'Anthony Tolliver','unknown.jpg',40,null,null,null,26,2000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (224,'Tim Frazier','unknown.jpg',40,null,null,null,30,2000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (225,'Terry Rozier','unknown.jpg',40,null,null,null,2,1988520.00,3050390.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (226,'Anderson Varejao','unknown.jpg',40,null,null,null,25,1984005.00,1984005.00,1984005.00,1984005.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (227,'Rashad Vaughn','unknown.jpg',40,null,null,null,17,1889040.00,2901565.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (228,'Tim Duncan','unknown.jpg',40,null,null,null,27,1881250.00,1881250.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (229,'Kevin Seraphin','unknown.jpg',40,null,null,null,12,1881000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (230,'Wade Baldwin','unknown.jpg',40,null,null,null,15,1874400.00,1955160.00,3003126.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (231,'Larry Sanders','unknown.jpg',40,null,null,null,17,1865547.00,1865547.00,1865547.00,1865547.00,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (232,'Larry Sanders','unknown.jpg',40,null,null,null,6,1841849.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (233,'Sam Dekker','unknown.jpg',40,null,null,null,13,1794600.00,2760095.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (234,'Henry Ellenson','unknown.jpg',40,null,null,null,9,1780800.00,1857480.00,2856804.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (235,'Jerian Grant','unknown.jpg',40,null,null,null,4,1713840.00,2639314.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (236,'Quincy Acy','unknown.jpg',40,null,null,null,3,1709538.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (237,'Jordan Crawford','unknown.jpg',40,null,null,null,19,1709538.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (238,'Malik Beasley','unknown.jpg',40,null,null,null,8,1700640.00,1773840.00,2731714.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (239,'Mike Dunleavy','unknown.jpg',40,null,null,null,1,1662500.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (240,'Delon Wright','unknown.jpg',40,null,null,null,28,1645200.00,2536898.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (241,'Caris LeVert','unknown.jpg',40,null,null,null,3,1632480.00,1702800.00,2625718.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (242,'Justin Anderson','unknown.jpg',40,null,null,null,23,1579440.00,2516048.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (243,'Robert Covington','unknown.jpg',40,null,null,null,23,1577230.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (244,'Ryan Kelly','unknown.jpg',40,null,null,null,11,1577230.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (245,'Archie Goodwin','unknown.jpg',40,null,null,null,3,1577230.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (246,'DeAndre Bembry','unknown.jpg',40,null,null,null,1,1567200.00,1634640.00,2603982.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (247,'Jerami Grant','unknown.jpg',40,null,null,null,21,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (248,'Glenn Robinson','unknown.jpg',40,null,null,null,12,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (249,'Sean Kilpatrick','unknown.jpg',40,null,null,null,3,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (250,'Joe Harris','unknown.jpg',40,null,null,null,3,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (251,'Johnny O''Bryant','unknown.jpg',40,null,null,null,5,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (252,'Spencer Dinwiddie','unknown.jpg',40,null,null,null,3,1524305.00,1656092.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (253,'Elijah Millsap','unknown.jpg',40,null,null,null,24,1524305.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (254,'Bobby Portis','unknown.jpg',40,null,null,null,4,1516320.00,2494346.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (255,'Malachi Richardson','unknown.jpg',40,null,null,null,26,1504560.00,1569360.00,2581597.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (256,'Arron Afflalo','unknown.jpg',40,null,null,null,26,1500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (257,'Josh Huestis','unknown.jpg',40,null,null,null,21,1471382.00,2243326.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (258,'Montrezl Harrell','unknown.jpg',40,null,null,null,13,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (259,'Jordan Mickey','unknown.jpg',40,null,null,null,2,1471382.00,1329039.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (260,'Rakeem Christmas','unknown.jpg',40,null,null,null,12,1471382.00,1600520.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (261,'Joe Young','unknown.jpg',40,null,null,null,12,1471382.00,1600520.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (262,'Richaun Holmes','unknown.jpg',40,null,null,null,23,1471382.00,1600520.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (263,'Raul Neto','unknown.jpg',40,null,null,null,31,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (264,'Norman Powell','unknown.jpg',40,null,null,null,28,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (265,'Pat Connaughton','unknown.jpg',40,null,null,null,25,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (266,'Josh Richardson','unknown.jpg',40,null,null,null,16,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (267,'Briante Weber','unknown.jpg',40,null,null,null,5,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (268,'Axel Toupane','unknown.jpg',40,null,null,null,19,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (269,'Edy Tavares','unknown.jpg',40,null,null,null,6,1471382.00,1600520.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (270,'T.J. McConnell','unknown.jpg',40,null,null,null,23,1471382.00,1600520.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (271,'Salah Mejri','unknown.jpg',40,null,null,null,7,1471382.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (272,'Rondae Hollis-Jefferson','unknown.jpg',40,null,null,null,3,1455720.00,2470357.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (273,'Willy Hernangomez','unknown.jpg',40,null,null,null,20,1435750.00,1544951.00,1701735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (274,'Nikola Jokic','unknown.jpg',40,null,null,null,8,1417000.00,1475500.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (275,'Tyus Jones','unknown.jpg',40,null,null,null,18,1397400.00,2444053.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (276,'Timothe Luwawu-Cabarrot','unknown.jpg',40,null,null,null,23,1386600.00,1544951.00,2529684.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (277,'Demetrius Jackson','unknown.jpg',40,null,null,null,2,1384750.00,1544951.00,1676735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (278,'Kevin Martin','unknown.jpg',40,null,null,null,18,1360305.00,1360305.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (279,'Jarell Martin','unknown.jpg',40,null,null,null,15,1341600.00,2516222.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (280,'Brice Johnson','unknown.jpg',40,null,null,null,13,1331160.00,1544951.00,2500725.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (281,'A.J. Hammons','unknown.jpg',40,null,null,null,7,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (282,'Chinanu Onuaku','unknown.jpg',40,null,null,null,11,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (283,'Pascal Siakam','unknown.jpg',40,null,null,null,28,1312611.00,1544951.00,2351839.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (284,'Skal Labissiere','unknown.jpg',40,null,null,null,26,1312611.00,1544951.00,2338847.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (285,'Dejounte Murray','unknown.jpg',40,null,null,null,27,1312611.00,1544951.00,2321735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (286,'Damian Jones','unknown.jpg',40,null,null,null,10,1312611.00,1544951.00,2305057.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (287,'Ivica Zubac','unknown.jpg',40,null,null,null,14,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (288,'Cheick Diallo','unknown.jpg',40,null,null,null,19,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (289,'Malcolm Brogdon','unknown.jpg',40,null,null,null,17,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (290,'Tyler Ulis','unknown.jpg',40,null,null,null,24,1312611.00,1544951.00,1676735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (291,'Paul Zipser','unknown.jpg',40,null,null,null,4,1312611.00,1544951.00,1676735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (292,'Yogi Ferrell','unknown.jpg',40,null,null,null,7,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (293,'Joel Bolomboy','unknown.jpg',40,null,null,null,31,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (294,'Jake Layman','unknown.jpg',40,null,null,null,25,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (295,'Davis Bertans','unknown.jpg',40,null,null,null,27,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (296,'Diamond Stone','unknown.jpg',40,null,null,null,13,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (297,'Patrick McCaw','unknown.jpg',40,null,null,null,10,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (298,'David Nwaba','unknown.jpg',40,null,null,null,14,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (299,'Michael Gbinije','unknown.jpg',40,null,null,null,9,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (300,'Andrew Harrison','unknown.jpg',40,null,null,null,15,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (301,'Kay Felder','unknown.jpg',40,null,null,null,6,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (302,'Stephen Zimmerman','unknown.jpg',40,null,null,null,22,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (303,'Georges Niang','unknown.jpg',40,null,null,null,12,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (304,'Daniel Ochefu','unknown.jpg',40,null,null,null,30,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (305,'Sheldon McClellan','unknown.jpg',40,null,null,null,30,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (306,'Fred VanVleet','unknown.jpg',40,null,null,null,28,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (307,'Bryn Forbes','unknown.jpg',40,null,null,null,27,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (308,'Derrick Jones','unknown.jpg',40,null,null,null,24,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (309,'Semaj Christon','unknown.jpg',40,null,null,null,21,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (310,'Marshall Plumlee','unknown.jpg',40,null,null,null,20,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (311,'Rodney McGruder','unknown.jpg',40,null,null,null,16,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (312,'Troy Williams','unknown.jpg',40,null,null,null,15,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (313,'Kyle Wiltjer','unknown.jpg',40,null,null,null,13,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (314,'Tim Quarterman','unknown.jpg',40,null,null,null,11,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (315,'Dorian Finney-Smith','unknown.jpg',40,null,null,null,7,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (316,'Nicolas Brussino','unknown.jpg',40,null,null,null,7,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (317,'Treveon Graham','unknown.jpg',40,null,null,null,5,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (318,'Isaiah Taylor','unknown.jpg',40,null,null,null,11,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (319,'Okaro White','unknown.jpg',40,null,null,null,16,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (320,'Chasson Randle','unknown.jpg',40,null,null,null,20,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (321,'Shawn Long','unknown.jpg',40,null,null,null,11,1312611.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (322,'Jarrod Uthoff','unknown.jpg',40,null,null,null,11,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (323,'Gary Payton','unknown.jpg',40,null,null,null,17,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (324,'Patricio Garino','unknown.jpg',40,null,null,null,22,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (325,'Marcus Georges-Hunt','unknown.jpg',40,null,null,null,22,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (326,'Quinn Cook','unknown.jpg',40,null,null,null,19,1312611.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (327,'Deyonta Davis','unknown.jpg',40,null,null,null,15,1307614.00,1544951.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (328,'Larry Nance Jr.','unknown.jpg',40,null,null,null,14,1259640.00,2272391.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (329,'Chris McCullough','unknown.jpg',40,null,null,null,30,1242840.00,2243326.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (330,'Livio Jean-Charles','unknown.jpg',40,null,null,null,27,1242240.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (331,'Kevon Looney','unknown.jpg',40,null,null,null,10,1233840.00,2227081.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (332,'Pablo Prigioni','unknown.jpg',40,null,null,null,11,1178992.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (333,'Isaiah Whitehead','unknown.jpg',40,null,null,null,3,1122248.00,1544951.00,1676735.00,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (334,'DeAndre Liggins','unknown.jpg',40,null,null,null,13,1087745.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (335,'Gerald Henderson','unknown.jpg',40,null,null,null,23,1000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (336,'Festus Ezeli','unknown.jpg',40,null,null,null,25,1000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (337,'Paul Pierce','unknown.jpg',40,null,null,null,13,1000000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (338,'Jason Thompson','unknown.jpg',40,null,null,null,10,945126.00,945126.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (339,'Martell Webster','unknown.jpg',40,null,null,null,30,830000.00,830000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (340,'Carlos Delfino','unknown.jpg',40,null,null,null,13,650000.00,650000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (341,'Caron Butler','unknown.jpg',40,null,null,null,26,517220.00,517220.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (342,'Jordan Farmar','unknown.jpg',40,null,null,null,13,510921.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (343,'Tibor Pleiss','unknown.jpg',40,null,null,null,23,500000.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (344,'Aaron Gray','unknown.jpg',40,null,null,null,9,452059.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (345,'Gal Mekel','unknown.jpg',40,null,null,null,7,315760.00,null,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (346,'Miroslav Raduljica','unknown.jpg',40,null,null,null,13,300000.00,300000.00,null,null,10,5,5,473604.00,0);
INSERT INTO jugadordefault (idJugadorDefault,nombre,imagen,media,fechaNacimiento,pos1,pos2,idEquipoDefault,salario1,salario2,salario3,salario4,moneyInterest,winningInterest,loyaltyInterest,cache,yearsPro) VALUES (347,'Isaiah Canaan','unknown.jpg',40,null,null,null,4,200000.00,null,null,null,10,5,5,473604.00,0);
UPDATE jugadordefault SET imagen='unknown.jpg' WHERE imagen is null;