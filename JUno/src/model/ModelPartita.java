package model;

import java.util.ArrayList;

/**
 * Progetto Juno - Classe model partita
 * <p>La classe è coinvolta in un pattern MVC</p>
 * @see view.ViewPartita
 * @see controller.ControllerPartita
 */
public class ModelPartita 
{
	/**
	 * Mano del primo NPC
	 */
	public ArrayList<Carta> manoNPC1 = new ArrayList<Carta>();
	/**
	 * Mano del secondo NPC
	 */
	public ArrayList<Carta> manoNPC2 = new ArrayList<Carta>();
	/**
	 * Mano del terzo NPC
	 */
	public ArrayList<Carta> manoNPC3 = new ArrayList<Carta>();
	/**
	 * Mano del giocatore
	 */
	public ArrayList<Carta> manoGiocatore = new ArrayList<Carta>();
	/**
	 * Mazzo di pesca contentente le carte da pescare
	 */
	public Mazzo mazzoPesca;
	/**
	 * Pila degli scarti contentente le carte da scartate
	 */
	public Mazzo pilaDegliScarti;
	private boolean dichiarazioneUNO;
	private int turnoAttuale;
	private int turnoSuccessivo;
	private boolean turnoInvertito;
	private int numeroRound;
	/**
	 * specifica il numero di punti necessari per vincere la partita
	 */
	public static final int PUNTI_NECESSARI_VITTORA = 500;
	
	/**
	 * Costruttore della classe, inizializza il numero di round a 1
	 */
	public ModelPartita() 
		{numeroRound = 1;}
	
	/**
	 * <p>Imposta il valore della dichiarazione di "UNO!" da parte del giocatore</p>
	 * @param dichiarazioneUNO contiene il valore vero/falso della dichiarazione "UNO!" da impostare
	 */
	public void setDichiarazioneUNO(boolean dichiarazioneUNO)
		{this.dichiarazioneUNO = dichiarazioneUNO;}

	/**
	 * <p>Restituisce </p>
	 * @return valore di dichiarazione "UNO!" da parte del giocatore
	 */
	public boolean getDichiarazioneUNO()
		{return dichiarazioneUNO;}
	
	/**
	 * <p>Imposta il numero di turno attuale</p>
	 * @param turno è il numero turno attuale da impostare
	 */
	public void setTurno(int turno)
	{
		if(turnoInvertito == false) //Caso di ordine normale 0->1->2->3
		{
			turnoAttuale = turno;
			if(turnoAttuale == 4)
				{turnoAttuale = 0;}
			
			turnoSuccessivo = turnoAttuale + 1;
			if(turnoSuccessivo == 4)
				{turnoSuccessivo = 0;}
		}
		else //Caso di ordine invertito 0<-1<-2<-3
		{
			turnoAttuale = turno;
			if(turnoAttuale == -1)
				{turnoAttuale = 3;}
			
			turnoSuccessivo = turnoAttuale - 1;
			if(turnoSuccessivo == -1)
				{turnoSuccessivo = 3;}
		}

	}
	
	/**
	 * <p>Restituisce il valore del turno successivo</p>
	 * @return il valore del turno successivo
	 */
	public int getTurnoSuccessivo()
		{return turnoSuccessivo;}

	/**
	 * <p>Restituisce il valore del turno attuale</p>
	 * @return il valore del turno attuale
	 */
	public int getTurnoAttuale() 
		{return turnoAttuale;}
	
	/**
	 * <p>Cambia l'ordine del turno</p>
	 */
	public void switchTurnoInvertito()
	{
		//Cambio del turno
		if(turnoInvertito == true) //Caso di turno normale
			{turnoInvertito = false;}
		else //Caso di turno invertito
			{turnoInvertito = true;}
	}
	
	/**
	 * <p>Imposta l'inversione del turno</p>
	 * @param set è il valore vero/falso per specificare se il turno è invertito o meno
	 */
	public void setTurnoInvertito(boolean set)
		{turnoInvertito = set;}
	
	/**
	 * <p>Restituisce il numero del round attuale</p>
	 * @return il numere
	 */
	public int getNumeroRound()
		{return numeroRound;}
	
	/**
	 * <p>Incrementa il numero del round attuale di 1</p>
	 */
	public void incrementaNumeroRound()
		{numeroRound++;}

	/**
	 * <p>Restituisce il numero di punti necessari per vincere la partita</p>
	 * @return il numero di punti necessari per vincere la partita
	 */
	public int getPUNTI_NECESSARI_VITTORIA()
		{return PUNTI_NECESSARI_VITTORA;}

}
