package view;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.Carta;
import model.ModelOpzioni;


/**
 * Progetto Juno - Classe vista partita
 * <p>Contiene il panel della partita</p>
 * <p>Estende JPanel</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see JPanel
 * @see controller.ControllerPartita
 * @see model.ModelPartita
 */
public class ViewPartita extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * È il box per il panel delle carte del giocatore
	 */
	public JPanel cardPanel;
	
	/**
	 * Scrollable pane da includere nel card panel
	 */
	private JScrollPane scrollableCardPanel;
	
	/**
	 * Bottone per tornare indietro al menu principale
	 */
	public JButton bottoneIndietro = new JButton("indietro");
	
	/**
	 * Bottone per pescare una carta
	 */
	public JButton bottonePesca = new JButton("Pesca");
	
	/**
	 * Bottone per dichiarare "UNO!"
	 */
	public JButton bottoneUNO = new JButton("UNO");
	
	/**
	 * Panel del primo NPC
	 */
	public PanelProfiloUtente profiloNPC1;
	
	/**
	 * Panel del secondo NPC
	 */
	public PanelProfiloUtente profiloNPC2;
	
	/**
	 * Panel del terzo NPC
	 */
	public PanelProfiloUtente profiloNPC3;
	
	/**
	 * Box per il log di gioco
	 */
	public JTextArea textAreaLog;
	
	/**
	 * Scrollable pane da includere nel log di gioco
	 */
	public JScrollPane scrollableLog;
	
	/**
	 * Immagine della pila degli scarti
	 */
	public JLabel labelPilaScarti;
	
	/**
	 * Immagine del mazzo di pesca
	 */
	public JLabel labelMazzoPesca;
	
	/**
	 * Label contente il numero di carte del mazzo di pesca
	 */
	public JLabel labelNumeroCarteMazzoPesca;
	
	/**
	 * Tabella dei punteggi
	 */
	public JTable punteggi;
	
	/**
	 * Scroll pane da includere nella tabella dei punteggi
	 */
	public JScrollPane scrollPanePunteggi;
	
	/**
	 * Modello per la tabella dei punti
	 */
	public DefaultTableModel tabellonePuntiPartita;
	
	
	/**
	 * Costruttore della classe
	 * <p>Si occupa di costruire il panel per la partita</p>
	 */
	public ViewPartita()
	{
		this.setBounds(155, 60, 1600, 900);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		this.setLayout(null);
		
		creaNPC();
		setTavolo();
        setCardPanel();
		setTextLog();
	    setBottoni();
	    aggiungiComponent();
	}
	
	/**
	 * <p>Crea gli NPC e i loro rispettivi panel</p>
	 */
	private void creaNPC()
	{
		//Selezione dei nomi dalla lista di nomi randomizzabili
		String[][] listaNomiNPC = 
		{
			{"Il Buono", "Il Brutto", "Il Cattivo"},
			{"Harry", "Hermione", "Ron"},
			{"Ciri", "Yennefer", "Triss"},
			{"Frodo", "Sam", "Bilbo"},
			{"Odahviing", "Paarthurnax", "Alduin"},
			{"Aldo", "Giovanni", "Giacomo"},
			{"Falagar", "Gavin", "Adara"},
			{"Tarantino", "Scorsese", "Kubrik"},
			{"Henry", "James", "Tommy"},
			{"Qui", "Quo", "Qua"}
		};
		int randomNum = ThreadLocalRandom.current().nextInt(0, 10);

		//Creazione degli NPC
		profiloNPC1 = new PanelProfiloUtente("resources\\avatar\\Robot.png", ThreadLocalRandom.current().nextInt(0, 10000), listaNomiNPC[randomNum][0]); 
		profiloNPC1.setLocation(70, 30);
		profiloNPC2 = new PanelProfiloUtente("resources\\avatar\\Robot.png", ThreadLocalRandom.current().nextInt(0, 10000), listaNomiNPC[randomNum][1]); 
		profiloNPC2.setLocation(570, 30);
		profiloNPC3 = new PanelProfiloUtente("resources\\avatar\\Robot.png", ThreadLocalRandom.current().nextInt(0, 10000), listaNomiNPC[randomNum][2]); 
		profiloNPC3.setLocation(1070, 30);
		
	}
	
	/**
	 * <p>Imposta font, dimensione e posizione dei bottoni</p>
	 */
	private void setBottoni()
	{
	    bottoneIndietro.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
	    bottoneIndietro.setBounds(70, 810, 200, 65);
	    
	    bottonePesca.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
	    bottonePesca.setBounds(1370, 600, 200, 65);
	    
	    bottoneUNO.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
	    bottoneUNO.setBounds(1370, 700, 200, 65);
	}
	
	/**
	 * <p>Imposta il card panel</p>
	 */
	private void setCardPanel()
	{
        cardPanel = new JPanel();
		cardPanel.setBackground(Color.yellow);
		
	    scrollableCardPanel = new JScrollPane(cardPanel);
	    scrollableCardPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollableCardPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	    scrollableCardPanel.setBounds(550, 600, 800, 210);
	}

	/**
	 * <p>Imposta il log della partita</p>
	 */
	private void setTextLog()
	{
        textAreaLog = new JTextArea(20, 20);
        textAreaLog.setEditable(false);
        scrollableLog = new JScrollPane(textAreaLog);
        scrollableLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        scrollableLog.setBounds(70, 250, 450, 330);
	}
	
	/**
	 * <p>Imposta i componenti principali del tavolo su cui giocare</p>
	 */
	private void setTavolo()
	{
		//Aggiunta del mazzo 
        labelMazzoPesca = new ImmagineCarta("RETRO", 650,350);
        labelMazzoPesca.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        
        //Aggiunta della pila degli scarti
        aggiungiPilaDegliScarti("");
        
        //Aggiunta label numero carte del mazzo pesca
        labelNumeroCarteMazzoPesca = new JLabel("", SwingConstants.CENTER);
        labelNumeroCarteMazzoPesca.setBounds(600, 300, 210, 60);
        labelNumeroCarteMazzoPesca.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
	}
	
	/**
	 * <p>Agggiunge i componenti al panel</p>
	 */
	private void aggiungiComponent()
	{
		this.add(profiloNPC1);
		this.add(profiloNPC2);
		this.add(profiloNPC3);
		this.add(bottoneIndietro);
		this.add(bottonePesca);
		this.add(bottoneUNO);
		this.add(scrollableCardPanel);
		this.add(scrollableLog);
		this.add(labelMazzoPesca);
		this.add(labelNumeroCarteMazzoPesca);
	}
	
	/**
	 * <p>Aggiunge il label della pila degli scarti</p>
	 * @param descrizioneCarta carta da aggiungere al label
	 */
	public void aggiungiPilaDegliScarti(String descrizioneCarta)
	{	
		//Aggiunta del label alla pila degli scarti
        labelPilaScarti = new ImmagineCarta(descrizioneCarta, 850, 350);
        labelPilaScarti.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        
        //Eventuale aggiunta della descrizione visiva della carta
        if(ModelOpzioni.getDescrizioneCarte())
        	{labelPilaScarti.setToolTipText(descrizioneCarta);}
        this.add(labelPilaScarti);
	}
	
	/**
	 * <p>Genera un'animazione in cui si scarta una carta dalla cima del mazzo</p>
	 * @param cartaDaScartare carta da scartare dal mazzo
	 */
	public void animazioneScartoDaMazzoPesca(Carta cartaDaScartare)
	{
		//Creazione del label da animare
        AudioPlayer.riproduci(EnumFileAudio.cardFlip.toString(), ModelOpzioni.getVolume());
		JLabel imageLabelAnimata = new ImmagineCarta(cartaDaScartare.getDescrizione(), 650, 350);
        this.add(imageLabelAnimata); 
        
        //Timer per animazione della carta
        Timer timer = new Timer(1, new ActionListener()
        {  
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	if(imageLabelAnimata.getLocation().x == 850) 
            	{
            		((Timer)e.getSource()).stop();
            		remove(imageLabelAnimata);
            		remove(labelPilaScarti);
            		aggiungiPilaDegliScarti(cartaDaScartare.getDescrizione());
            		repaint();
            	}
            	else
            		{imageLabelAnimata.setLocation(imageLabelAnimata.getLocation().x+5, imageLabelAnimata.getLocation().y);}
            }               
        });     
        timer.start();
	}
	
	/**
	 * <p>Genera un'animazione in cui si cambia la carta scartata nella pila</p>
	 * @param cartaDaScartare carta da scartare dalla cima del mazzo
	 */
	public void animazioneScartoDaMano(Carta cartaDaScartare)
	{
	    remove(labelPilaScarti);
	    aggiungiPilaDegliScarti(cartaDaScartare.getDescrizione());
	    repaint();
	}
	
	/**
	 * <p>Genera una finestra di dialogo in cui viene mostrato il nome del vincitore del round assieme al suo punteggio</p>
	 * @param nomeVincitore nome del vincitore del round
	 * @param puntiVincitore punti del vincitore del round
	 */
	public void dialogVittoria(String nomeVincitore, String puntiVincitore)
	{
		AudioPlayer.riproduci(EnumFileAudio.roundWin.toString(), ModelOpzioni.getVolume());
		JOptionPane.showMessageDialog(null, nomeVincitore + " vince il round con punti " + puntiVincitore);
	}
	
	/**
	 * <p>Genera una finestra di dialogo in cui viene mostrato il nome del vincitore della partita</p>
	 * @param nomeVincitore nome del vincitore della partita
	 */
	public void dialogFinePartita(String nomeVincitore)
	{
		AudioPlayer.riproduci(EnumFileAudio.trumpets.toString(), ModelOpzioni.getVolume());
		JOptionPane.showMessageDialog(null, nomeVincitore + " vince la partita! Complimenti!");
	}
}
