DROP TABLE IF EXISTS teammanagement.usuario;
DROP TABLE IF EXISTS teammanagement.equipo;
CREATE TABLE teammanagement.usuario (
  idUsuario INT  NOT NULL AUTO_INCREMENT,
  nombreUsuario VARCHAR(50) NOT NULL,
  idEquipo INT REFERENCES equipo(idequipo),
  PRIMARY KEY (idusuario)
)
ENGINE = MyISAM;
CREATE TABLE teammanagement.equipo (
idEquipo INT NOT NULL AUTO_INCREMENT,
nombreEquipo VARCHAR(50) NOT NULL,
idUsuario INT REFERENCES usuario(idusuario),
PRIMARY KEY(idEquipo)
)
ENGINE = MyISAM;

INSERT INTO teammanagement.usuario VALUES (1,'sirgodnolimit',2);
INSERT INTO teammanagement.usuario VALUES (2,'Maria la Tontita',3);
