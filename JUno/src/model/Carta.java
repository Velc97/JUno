package model;

/**
 * Progetto Juno - Classe Carta
 * <p>Una carta è costituita da un colore e un numero</p>
 * @see Mazzo
 * @author Cosmo Vellucci 1760067
 */
public class Carta 
{
	private String colore;
	private String numero; 
	private String descrizione; //Descrizione completa della carta, composta da colore+numero
	
	/**
	 * @param colore indica il colore della carta
	 * @param numero indica il numero della carta
	 */
	public Carta(String colore, String numero)
	{
		this.colore = colore;
		this.numero = numero;
		setDescrizione(colore, numero);
	}
		
	/**
	 * Restituisce la descrizione della carta (composta da colore + numero)
	 * @return descrizione della carta
	 */
	public String getDescrizione()
		{return descrizione;}
	
	/**
	 * Restituisce il colore della carta
	 * @return colore colore della carta
	 */
	public String getColore()
		{return colore;}
	
	/**
	 * Restituisce il numero della carta
	 * @return numero numero della carta
	 */
	public String getNumero()
		{return numero;}
	
	/**
	 * Imposta la descrizione della carta (composta da colore + numero)
	 * @param colore colore della carta
	 * @param numero numero della carta
	 */
	public void setDescrizione(String colore, String numero)
		{descrizione = colore + " " + numero;}
	
	/**
	 * Imposta il colore della carta
	 * @param colore colore della carta
	 */
	public void setColore(String colore)
		{this.colore = colore;}
	
}