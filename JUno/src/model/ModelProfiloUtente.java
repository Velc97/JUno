package model;

import java.io.Serializable;

/**
 * Progetto Juno - Classe model profilo utente
 * <p>La classe è coinvolta in un pattern MVC</p>
 * @see view.ViewProfiloUtente
 * @see controller.ControllerProfiloUtente
 */
public class ModelProfiloUtente implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Percorso all'immagine di profilo del giocatore
	 */
	private String pathAvatar;
	
	/**
	 * Valore dell'esperienza (per calcolare il livello)
	 */
	private int exp;
	
	/**
	 * Nome dell'utente
	 */
	private String nomeUtente;
	
	/**
	 * Numero di partite giocate
	 */
	private int partiteGiocate;
	
	/**
	 * Numero di partite vinte
	 */
	private int partiteVinte;
	
	/**
	 * Numero di partite perse
	 */
	private int partitePerse;
	
	
	/**
	 * <p>Costruttore vuoto</p>
	 */
	public ModelProfiloUtente() {}
	
	
	/**
	 * <p>Calcola il rateo W/L, ovvero il rapporto tra vittorie e sconfitte</p>
	 * @return rateo vittorie/sconfitte
	 */
	public float calcolaRateo()
	{
		if(partitePerse == 0) //Tipico dei videogiochi: se non ci sono partite perse il rateo è considerato pari a 1
			{return 1;}
		return partiteVinte / partitePerse;
	}
	
	/**
	 * <p>Imposta il percorso dell'immagine avatar del giocatore</p>
	 * @param pathAvatar è il percorso da impostare
	 */
	public void setPathAvatar(String pathAvatar)
		{this.pathAvatar = pathAvatar;}
	
	/**
	 * <p>Imposta il valode dell'esperienza del giocatore</p>
	 * @param exp valore dell'esperienza da settare
	 */
	public void setExp(int exp)
		{this.exp = exp;}
	
	/**
	 * <p>Imposta il nome del giocatore</p>
	 * @param nomeUtente è il nome da impostare del giocatore
	 */
	public void setNomeUtente(String nomeUtente)
		{this.nomeUtente = nomeUtente;}
	
	/**
	 * <p>Imposta il numero di partite giocate (in totale) dall'utente</p>
	 * @param partiteGiocate è il numero di partite giocate
	 */
	public void setPartiteGiocate(int partiteGiocate)
		{this.partiteGiocate = partiteGiocate;}
	
	/**
	 * <p>Imposta il numero di partite vinte del giocatore</p>
	 * @param partiteVinte il numero di partite giocate
	 */
	public void setPartiteVinte(int partiteVinte)
		{this.partiteVinte = partiteVinte;}
	
	/**
	 * <p>Imposta il numero di partite perse del giocatore</p>
	 * @param partitePerse è il numero di partite perse
	 */
	public void setPartitePerse(int partitePerse)
		{this.partitePerse = partitePerse;}
	
	/**
	 * <p>Restituisce il percorso al file dell'immagine avatar dell'utente</p>
	 * @return percorso al file
	 */
	public String getPathAvatar()
		{return pathAvatar;}

	/**
	 * <p>Restituisce l'esperienza dell'utente</p>
	 * @return valore dell'esperienza
	 */
	public int getExp()
		{return exp;}

	/**
	 * <p>Restituisce il nome dell'utente</p>
	 * @return nome dell'utente
	 */
	public String getNomeUtente()
		{return nomeUtente;}
	
	/**
	 * <p>Restituisce il numero di partite giocate</p>
	 * @return numero di partite giocate
	 */
	public int getPartiteGiocate()
		{return partiteGiocate;}

	/**
	 * <p>Restituisce il numero di partite vinte</p>
	 * @return numero di partite vinte
	 */
	public int getPartiteVinte()
		{return partiteVinte;}

	/**
	 * <p>Restituisce il numero di partite perse</p>
	 * @return numero di partite perse
	 */
	public int getPartitePerse()
		{return partitePerse;}
	
	/**
	 * <p>Ripristina le impostazioni del profilo utente a quelle di default</p>
	 */
	public void ripristina()
	{
		pathAvatar = "resources\\avatar\\default.png";
		exp = 0;
		nomeUtente = "JUno Player";
		partiteGiocate = 0;
		partiteVinte = 0;
		partitePerse = 0;
	}
}
