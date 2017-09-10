package es.ligasnba.app.model;

import es.ligasnba.app.model.competicion.Competicion;
import es.ligasnba.app.model.contrato.Contrato;
import es.ligasnba.app.model.equipo.Equipo;
//import es.ligasnba.app.model.finanzas.Finanzas;
import es.ligasnba.app.model.jugador.Jugador;
import es.ligasnba.app.model.temporada.Temporada;
import es.ligasnba.app.model.usuario.Usuario;
import es.ligasnba.app.model.usuario.UsuarioDao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sir
 */
public class Main {
	private UsuarioDao usuariodao;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Jugador j1=new Jugador("Joe Johnson",86);
//        Jugador j2=new Jugador("Monta Ellis",83);
//        Jugador j3=new Jugador("Kevin Garnett",80);
    //    u1.addEquipo(e1);
      //  u2.addEquipo(e2);
/*        e1.addJugardor(j1);
        e1.addJugardor(j3);
        e2.addJugardor(j2);*/
        //Competicion com1 = new Competicion(1,"Liga Andrés Montes");

      //  com1.addTemporada(t1);
       // com1.addTemporada(t2);
        //com1.setTemporadaactual(t1);
//        e1.setCompeticion(com1);

//       Contrato c1 = new Contrato(1,e1,j1,t1,15000000);
//       Contrato c2 = new Contrato(2,e1,j2,t1,20000000);
//       Contrato c3 = new Contrato(3,e1,j1,t2,20000000);
//       j1.addContratoFirmado(c1);
//       j1.addContratoFirmado(c3);
//       j2.addContratoFirmado(c2);
 /*      e1.addContrato(c1);
       e1.addContrato(c2);
       e1.addContrato(c3);*/
       //Finanzas f1= new Finanzas(1,t1,e1);
     //  Finanzas f2= new Finanzas(2,t2,e1);
//       for(int i=0;i<e1.getNumJugadores();i++) System.out.println(e1.getJugadores(i));
  //     System.out.println();
       //System.out.println( f2.calcularSumaSalarial(t1) );
     /* try{
      System.out.println( (u2.findusuario(1)).toString());
      }catch (Exception e){
          e.printStackTrace();
      }
      */
       /** Getting the Session Factory and session */
       
       //Session sess = session.getCurrentSession();
       /** Starting the Transaction */
       //Transaction tx = sess.beginTransaction();
       //Usuario u = new Usuario(6,"sirnolimit");
       //sess.save(u);
       //tx.commit();
       System.out.println("Record Inserted");  
       /** Closing Session */
       
       
       

       
       




    }

}
