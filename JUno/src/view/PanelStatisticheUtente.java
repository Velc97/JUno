package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Progetto Juno - Classe Panel Statistiche Utente
 * <p>Si occupa di mostrare le statistiche dell'utente</p>
 */
public class PanelStatisticheUtente extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Label "Partite giocate:"</p>
	 */
	private JLabel labelPartite;
	
	/**
	 * <p>Label contente il numero di partite giocate dall'utente</p>
	 */
	private JLabel labelNumeroPartite;
	
	/**
	 * <p>Label "Nuero Partite Vinte:"</p>
	 */
	private JLabel labelPartiteVinte;
	
	/**
	 * <p>Label contente il numero di partite vinte dall'utente</p>
	 */
	private JLabel labelNumeroPartiteVinte;
	
	/**
	 * <p>Label "Partite Perse:"</p>
	 */
	private JLabel labelPartitePerse;
	
	/**
	 * <p>Label contente il numero di partite perse dall'utente</p>
	 */
	private JLabel labelNumeroPartitePerse;
	
	/**
	 * <p>Label "Rateo:"</p>
	 */
	private JLabel labelRateo;
	
	/**
	 * <p>Label contente il rateo di vittorie/sconfitte dell'utente</p>
	 */
	private JLabel labelValoreRateo;
	
	/**
	 * Costruttore della classe
	 * <p>Si occupa di impostare il panel</p>
	 * @param pGiocate numero di partite giocate
	 * @param pVinte numero di partite vinte
	 * @param pPerse numero di partite perse
	 * @param rateo valore vittorie/sconfitte
	 */
	public PanelStatisticheUtente(int pGiocate, int pVinte, int pPerse, float rateo)
	{
		this.setSize(200, 150);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setLayout(null);	
		
		setLabel(pGiocate, pVinte, pPerse, rateo);
	}
	
	/**
	 * <p>Imposta tutti i label del panel</p>
	 * @param pGiocate numero di partite giocate
	 * @param pVinte numero di partite vinte
	 * @param pPerse numero di partite perse
	 * @param rateo valore vittorie/sconfitte
	 */
	private void setLabel(int pGiocate, int pVinte, int pPerse, float rateo)
	{
		labelPartite = new JLabel("Partite giocate", SwingConstants.LEFT);
		labelPartite.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelPartite.setBounds(10, 0, 150, 40);
		this.add(labelPartite);
		
		labelNumeroPartite = new JLabel(String.valueOf(pGiocate), SwingConstants.RIGHT);
		labelNumeroPartite.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelNumeroPartite.setBounds(120, 0, 60, 40);
		this.add(labelNumeroPartite);
		
		labelPartiteVinte = new JLabel("Partite vinte", SwingConstants.LEFT);
		labelPartiteVinte.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelPartiteVinte.setBounds(10, 40, 90, 40);
		this.add(labelPartiteVinte);
		
		labelNumeroPartiteVinte = new JLabel(String.valueOf(pVinte), SwingConstants.RIGHT);
		labelNumeroPartiteVinte.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelNumeroPartiteVinte.setBounds(120, 40, 60, 40);
		this.add(labelNumeroPartiteVinte);
		
		labelPartitePerse = new JLabel("Partite perse", SwingConstants.LEFT);
		labelPartitePerse.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelPartitePerse.setBounds(10, 80, 90, 40);
		this.add(labelPartitePerse);
		
		labelNumeroPartitePerse = new JLabel(String.valueOf(pPerse), SwingConstants.RIGHT);
		labelNumeroPartitePerse.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelNumeroPartitePerse.setBounds(120, 80, 60, 40);
		this.add(labelNumeroPartitePerse);
		
		labelRateo = new JLabel("Rapporto V/S", SwingConstants.LEFT);
		labelRateo.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelRateo.setBounds(10, 110, 100, 40);
		this.add(labelRateo);
		
		labelValoreRateo = new JLabel(String.format("%.2f", rateo), SwingConstants.RIGHT);
		labelValoreRateo.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		labelValoreRateo.setBounds(120, 110, 60, 40);
		this.add(labelValoreRateo);
	}
	
}
