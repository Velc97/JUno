package model;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Progetto Juno - Classe Mazzo
 * <p> Rappresenta un'ArrayList di Carte con le sue funzionalità</p>
 * @author Cosmo Vellucci 1760067
 */
public class Mazzo extends ArrayList<Carta>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Enumerazione per il colore delle carte
	 */
	public enum ColoreCarta
	{
		/**
		 * Colore blu
		 */
		BLU,
		
		/**
		 * Colore Giallo
		 */
		GIALLO,
		
		/**
		 * Colore Rosso
		 */
		ROSSO,
		
		/**
		 * Colore Verde
		 */
		VERDE;
		
		/**
		 * Enumerazione per il colore delle carte nere
		 */
		public enum ColoreCartaJolly
		{
			/**
			 * Colore Nero
			 */
			NERO
		}
	}
	
	/**
	 * Enumerazione per i valori delle carte (effetti compresi)
	 */
	public enum ValoreCarta
	{
		/**
		 * Numero 0
		 * */
		ZERO,
		
		/**
		 * Numero 1
		 * */
		UNO,
		
		/**
		 * Numero 2
		 * */
		DUE,
		
		/**
		 * Numero 3
		 * */
		TRE,
		
		/**
		 * Numero 4
		 * */
		QUATTRO,
		
		/**
		 * Numero 5
		 * */
		CINQUE,
		
		/**
		 * Numero 6
		 * */
		SEI,
		
		/**
		 * Numero 7
		 * */
		SETTE,
		
		/**
		 * Numero 8
		 * */
		OTTO,
		
		/**
		 * Numero 9
		 * */
		NOVE,
		
		/**
		 * Effetto Inverti turno
		 */
		INVERTI,
		
		/**
		 * Effetto Pesca due carte
		 * */
		PESCA2,
		
		/**
		 * Effetto Salta turno
		 */
		SALTO;
		
		/**
		 * Enumerazione per i valori delle carte nere
		 */
		public enum ValoreCartaNera
		{
			/**
			 * Effetto Pesca quattro carte
			 */
			PESCA4,
			
			/**
			 * Effetto Cambia colore (Jolly)
			 */
			JOLLY
		}
		
	}
	
	/**
	 * <p>Costruttore vuoto</p>
	 */
	public Mazzo() {}
	
	/**
	 * <p>Costruttore che copia un mazzo di carte dato</p>
	 * @param mazzoDaCopiare il mazzo di carte da copiare nel mazzo di carte attuale
	 */
	public Mazzo(Mazzo mazzoDaCopiare) 
	{
		for(Carta cartaDaCopiare : mazzoDaCopiare)
			{this.add(cartaDaCopiare);}
	}

	/**
	 * <p>Crea il mazzo standard di carte</p>
	 */
	public void creaMazzo()
	{	
		//Memorizzazione delle enumerazioni per iterarvi sopra in seguito
		ValoreCarta listaValoreCarta[] = ValoreCarta.values();
		ColoreCarta listaColoreCarta[] = ColoreCarta.values();
		ValoreCarta.ValoreCartaNera listaValoreCartaNera[] = ValoreCarta.ValoreCartaNera.values();
		
		//Aggiunta delle carte da 1 a 9 più effetti
		for (ValoreCarta valCarta : listaValoreCarta) 
		{
			for(ColoreCarta colCarta : listaColoreCarta)
			{
				this.add(new Carta(colCarta.toString(), valCarta.toString()));
				if(!valCarta.equals(ValoreCarta.ZERO)) //Controllo sulle carte con valore numerico zero, che devono essere inserite una sola volta
					{this.add(new Carta(colCarta.toString(), valCarta.toString()));}
			}
        }

		//Aggiunta delle carte Nere
		for(ValoreCarta.ValoreCartaNera valCartaNera : listaValoreCartaNera)
		{
			for(int i=0; i<4; i++)
				{this.add(new Carta(ColoreCarta.ColoreCartaJolly.NERO.toString(), valCartaNera.toString()));}
		}
	}
	
	/**
	 * <p>Rimuove una carta dal mazzo di carte</p>
	 * @return carta rimossa dal mazzo di carte
	 */
	public Carta rimuoviCarta()
	{
		Carta cartaDaDare = this.get(0);
		this.remove(0);
		return cartaDaDare;
	}
	
	/**
	 * <p>Mischia le carte del mazzo di carte</p>
	 */
	public void mischia()
		{Collections.shuffle(this);}
	
	/**
	 * <p>Aggiunge la carta</p>
	 * @param cartaDaAggiungere carta da aggiungere al mazzo
	 */
	public void aggiungiCarta(Carta cartaDaAggiungere)
		{this.add(0, cartaDaAggiungere);}
	
	
	
}