package controller;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.ModelOpzioni;
import model.ModelPartita;
import model.ModelProfiloUtente;
import model.Mazzo.ValoreCarta;
import model.Carta;
import model.Mazzo;
import view.ImmagineCarta;
import view.MainFrame;
import view.MenuDiGioco;
import view.ViewPartita;
import view.ViewProfiloUtente;

/**
 * Progetto Juno - Classe controller partita
 * <p>Controller tra ViewPartita e ModelPartita</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see view.ViewPartita
 * @see model.ModelPartita
 */
public class ControllerPartita
{
	/**
	 * Modello della partita
	 * @see model.ModelPartita
	 */
	private ModelPartita model;
	
	/**
	 * Vista della partita
	 * @see view.ViewPartita
	 */
	private ViewPartita view;
	
	/**
	 * Vista profilo utente
	 * @see view.ViewProfiloUtente
	 */
	private ViewProfiloUtente viewUtente;
	
	/**
	 * Model profilo utente
	 * @see model.ModelProfiloUtente
	 */
	private ModelProfiloUtente modelUtente;
	
	/**
	 * Valore del punteggio da calcolare durante la fine del round
	 */
	private int punteggio;
	
	/**
	 * Valore in punti delle carte di colore non nere con effetto
	 */
	private static final int PUNTEGGIO_CARTE_STANDARD = 20;
	
	/**
	 * Valore in punti delle carte nere
	 */
	private static final int PUNTEGGIO_CARTE_JOLLY = 50;
	
	/**
	 * Opzioni disponibile per la finestra di dialogo di cambio colore
	 */
	private String[] optionsCartaPescata = new String[] 
	{
		Mazzo.ColoreCarta.BLU.toString(),
		Mazzo.ColoreCarta.GIALLO.toString(),
		Mazzo.ColoreCarta.ROSSO.toString(),
		Mazzo.ColoreCarta.VERDE.toString()
	};
	
	/**
	 * Costruttore della classe
	 * <p>Si occupa di avviare il primo round della partita</p>
	 * @param model model del controller
	 * @param modelUtente mode utente del controller
	 * @param view vista del controller
	 * @param viewUtente vista utente del controller
	 * @see view.ViewPartita
	 * @see model.ModelPartita
	 * @see view.ViewProfiloUtente
	 * @see model.ModelProfiloUtente
	 */
	public ControllerPartita(ModelPartita model, ModelProfiloUtente modelUtente, ViewPartita view, ViewProfiloUtente viewUtente)
	{
		this.model = model;
		this.view = view;
		this.viewUtente = viewUtente;
		this.modelUtente = modelUtente;
		
		creaTabellaPunteggi();
		aggiungiEventiPulsanti();
		aggiungiGiocatore();
		
		startRound(model.getNumeroRound());
	}
	
	
	/**
	 * <p>Resituisce la vista del controller</p>
	 * @return la vista del controller
	 */
	public ViewPartita getView()
		{return this.view;}

	
	/**
	 * <p>Aggiunge l'action ai pulsanti</p>
	 */
	private void aggiungiEventiPulsanti()
	{
		//Bottone per tornare indietro al menu principale
		this.view.bottoneIndietro.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				String[] testoBottoni = {"Continua","Annulla"};
				int risposta = JOptionPane.showOptionDialog(null,
        	        		"Tornando indietro la partita verrà annullata. Continuare?",
        	        		"Attenzione!",
        	        		JOptionPane.YES_NO_OPTION,
        	        		JOptionPane.QUESTION_MESSAGE,
        	        		null,
        	        		testoBottoni,
        	        		testoBottoni[1]);
				if(risposta == 0)
				{
					MainFrame.rimuoviComponent(MainFrame.viewPartita);
					MainFrame.menuDiGioco = new MenuDiGioco();
					MainFrame.aggiungiComponent(MainFrame.menuDiGioco);
				}
	        }
		});
		
		//Bottone per pescare una carta
		this.view.bottonePesca.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				checkMazzo();

				//Pesca della carta
				Carta cartaPescata = model.mazzoPesca.rimuoviCarta();
				
				//Creazione del label da aggiungere al dialog
		        JLabel labelCartaPescata = new ImmagineCarta(cartaPescata.getDescrizione(), 10, 10);
				
		        //Creazione del panel da aggiungere al dialog
		        JPanel panelPescaCarta = new JPanel();
		        panelPescaCarta.add(labelCartaPescata);
		        
		        //Creazione del dialog per la pesca
				String[] opzioniDialogPesca = new String[] {"Gioca", "Mantieni"};
				int risultato = JOptionPane.showOptionDialog(null, panelPescaCarta, "Carta Pescata", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, opzioniDialogPesca, opzioniDialogPesca[0]);
				
				//Disattivazione dell'interfaccia
				toggleInterfaccia(false); 
				
				//Controllo sul risultato del dialog
				if (risultato == -1 || risultato == 1) //Si chiude la finestra o si mantiene la carta
				{
					AudioPlayer.riproduci(EnumFileAudio.cardDraw.toString(), ModelOpzioni.getVolume());
					aggiungiMessaggioLog(modelUtente.getNomeUtente() + " pesca una carta");
					distribuisciCartaInManoGiocatore(cartaPescata);
					model.setTurno(model.getTurnoSuccessivo());
					passaTurno(model.getTurnoAttuale());
				}
				else if(risultato == 0) //Si gioca la carta
				{
					if(cartaPescata.getColore() == model.pilaDegliScarti.get(0).getColore()
							|| cartaPescata.getNumero().equals(model.pilaDegliScarti.get(0).getNumero())  
							|| cartaPescata.getColore().equals(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()))
					{
						aggiungiMessaggioLog(modelUtente.getNomeUtente() + " ha scartato " + cartaPescata.getDescrizione());
						
						if(cartaPescata.getDescrizione().contains(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString())) //Selezione del nuovo colore della carta nera
							{selezioneColore(cartaPescata);}
						
						AudioPlayer.riproduci(EnumFileAudio.cardFlip.toString(), ModelOpzioni.getVolume());
						view.animazioneScartoDaMano(cartaPescata);
						model.pilaDegliScarti.aggiungiCarta(cartaPescata);
						attivaEffettoCarta(cartaPescata, model.getTurnoSuccessivo());
						
						view.labelNumeroCarteMazzoPesca.setText(String.valueOf(model.mazzoPesca.size()) + " Carte");
						view.repaint();
						view.revalidate();
					}
					else //Carta non scartabile 
					{
						aggiungiMessaggioLog(modelUtente.getNomeUtente() + ", non puoi giocare la carta appena pescata!");
						aggiungiMessaggioLog("Quindi "+ cartaPescata.getDescrizione() +" verrà aggiunta alla mano");
						AudioPlayer.riproduci(EnumFileAudio.cardDraw.toString(), ModelOpzioni.getVolume());
						distribuisciCartaInManoGiocatore(cartaPescata);
						model.setTurno(model.getTurnoSuccessivo());
						passaTurno(model.getTurnoAttuale());
					}
					
				}
	        }
		});
		
		
		//Bottone per dichiarare "UNO!"
		this.view.bottoneUNO.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				if(model.manoGiocatore.size() == 1)
				{
					if(model.getDichiarazioneUNO()) //Dichiarazione di UNO! già fatta
					{
						AudioPlayer.riproduci(EnumFileAudio.beep.toString(), ModelOpzioni.getVolume());
						aggiungiMessaggioLog(modelUtente.getNomeUtente() + " hai già dichiarato UNO!");
					}
					else 
					{
						Carta cartaInMano = model.manoGiocatore.get(0);
						if(cartaInMano.getColore().equals(model.pilaDegliScarti.get(0).getColore()) 
								|| cartaInMano.getNumero() == model.pilaDegliScarti.get(0).getNumero()
								|| cartaInMano.getColore() == Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()) //Carta giocabile
						{
							AudioPlayer.riproduci(EnumFileAudio.UNO.toString(), ModelOpzioni.getVolume());
							aggiungiMessaggioLog(modelUtente.getNomeUtente() + " dichiara UNO!");
							model.setDichiarazioneUNO(true);
						}
						else //Carta non giocabile
						{
							AudioPlayer.riproduci(EnumFileAudio.beep.toString(), ModelOpzioni.getVolume());
							aggiungiMessaggioLog("Carta in mano non giocabile");
						}
					}

				}
				else
				{
					AudioPlayer.riproduci(EnumFileAudio.beep.toString(), ModelOpzioni.getVolume());
					aggiungiMessaggioLog(modelUtente.getNomeUtente() + " non puoi dichiarare UNO! Se hai più di una carta in mano");
				}
				

	        }
		});
	}

	
	/**
	 * <p>Aggiunge il giocatore al panel</p>
	 */
	private void aggiungiGiocatore()
	{
		viewUtente.panelProfiloUtente.setLocation(70, 600);
		view.add(viewUtente.panelProfiloUtente);
	}
	
	
	/**
	 * <p>Scarta la carta dal mazzo di pesca alla pila degli scarti</p>
	 */
	private void scartaCartaDalMazzoNellaPila()
	{
		Carta cartaScartata = model.mazzoPesca.rimuoviCarta();
		
		if(cartaScartata.getColore().equals(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()))
			{selezioneColore(cartaScartata);}
		
		model.pilaDegliScarti.aggiungiCarta(cartaScartata);
		view.animazioneScartoDaMazzoPesca(cartaScartata);
		aggiungiMessaggioLog("Scartato " + cartaScartata.getDescrizione());
	}
	

	/**
	 * <p>Aggiunge un messaggio al log</p>
	 * @param messaggio da aggiungere al log
	 */
	private void aggiungiMessaggioLog(String messaggio)
		{view.textAreaLog.append(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + " - "+ messaggio + "\n");}
	
	
	/**
	 * <p>Elimina tutti i messaggi del log</p>
	 */
	private void pulisciLog()
		{view.textAreaLog.setText(null);}
	

	/**
	 * <p>Distribuisce la carta passata in input dal mozzo di pesca alla mano del giocatore</p>
	 * @param cartaDaDistribuire carta da distribuire in mano al giocatore
	 */
	private void distribuisciCartaInManoGiocatore(Carta cartaDaDistribuire)
	{ 
			model.manoGiocatore.add(cartaDaDistribuire);
			view.labelNumeroCarteMazzoPesca.setText(String.valueOf(model.mazzoPesca.size()) +" Carte");
			view.tabellonePuntiPartita.setValueAt(model.manoGiocatore.size(), 0, 2);
			
		    Icon iconaCarta = new ImageIcon("resources/carte/"+ cartaDaDistribuire.getDescrizione() + ".png");
		    JButton bottoneCartaDaAggiungere = new JButton(iconaCarta);
		    bottoneCartaDaAggiungere.setSize(103, 146);
		    bottoneCartaDaAggiungere.setMargin(new Insets(0, 0, 0, 0));
		    if(ModelOpzioni.getDescrizioneCarte())
		    	{bottoneCartaDaAggiungere.setToolTipText(cartaDaDistribuire.getDescrizione());}
		    
		    //Aggiunta dellla carta come bottone
		    bottoneCartaDaAggiungere.addActionListener(new ActionListener()
			{  
				public void actionPerformed(ActionEvent e)
		        {  
					if(cartaDaDistribuire.getColore().equals(model.pilaDegliScarti.get(0).getColore())
							|| cartaDaDistribuire.getNumero().equals(model.pilaDegliScarti.get(0).getNumero())
							|| cartaDaDistribuire.getColore().equals(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()))
					{
						toggleInterfaccia(false);
						aggiungiMessaggioLog(modelUtente.getNomeUtente() + " ha scartato " + cartaDaDistribuire.getDescrizione());
						view.cardPanel.remove(bottoneCartaDaAggiungere);
						
						if(cartaDaDistribuire.getDescrizione().contains(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString())) //Selezione del nuovo colore della carta nera
							{selezioneColore(cartaDaDistribuire);}
						

						
						if(model.manoGiocatore.size() == 1)
						{
							if(model.getDichiarazioneUNO())
								{endRound();}
							else
							{
								AudioPlayer.riproduci(EnumFileAudio.forgotUNO.toString(), ModelOpzioni.getVolume());
								aggiungiMessaggioLog(modelUtente.getNomeUtente() + " ha dimenticato di dire UNO!");
								aggiungiMessaggioLog("Quindi " + modelUtente.getNomeUtente() + " si becca due carte");
								distribuisciCartaInManoGiocatore(model.mazzoPesca.rimuoviCarta());
								distribuisciCartaInManoGiocatore(model.mazzoPesca.rimuoviCarta());
							}	
						}
						
						
						AudioPlayer.riproduci(EnumFileAudio.cardFlip.toString(), ModelOpzioni.getVolume());
						view.animazioneScartoDaMano(cartaDaDistribuire);
						model.manoGiocatore.remove(cartaDaDistribuire);
						model.pilaDegliScarti.aggiungiCarta(cartaDaDistribuire);
						attivaEffettoCarta(cartaDaDistribuire, model.getTurnoSuccessivo());

						view.tabellonePuntiPartita.setValueAt(model.manoGiocatore.size(), 0, 2);
						view.repaint();
						view.revalidate();
						
					}
					else //Carta non scartabile 
					{
						AudioPlayer.riproduci(EnumFileAudio.beep.toString(), ModelOpzioni.getVolume());
						aggiungiMessaggioLog(modelUtente.getNomeUtente() + ", non puoi scartare " + cartaDaDistribuire.getDescrizione());
					}
		        }
			});
		    
	        view.cardPanel.add(bottoneCartaDaAggiungere);
	        view.revalidate();
	}
	
	
	/**
	 * <p>Distribuisce la carta passata in input nella mano di un NPC e aggiorna il numero di carte in mano al partecipante</p>
	 * @param cartaDaDistribuire carta da distribuire dal mazzo di pesca alla mano dell'NPC
	 * @param manoNPC mano dell'NPC a cui aggiungere la carta
	 * @param numeroRiga numero di riga della tabella dei punteggi relativa all'NPC
	 */
	private void distribuisciCartaInManoNPC(Carta cartaDaDistribuire, ArrayList<Carta> manoNPC, int numeroRiga)
	{
		try
		{
			manoNPC.add(cartaDaDistribuire);
			view.labelNumeroCarteMazzoPesca.setText(String.valueOf(model.mazzoPesca.size()) + " Carte");
			view.tabellonePuntiPartita.setValueAt(manoNPC.size(), numeroRiga, 2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			aggiungiMessaggioLog(e.toString());
		}

	}
	
	
	/**
	 * <p>Avvia un round</p>
	 * @param numeroRound numero del round da avviare
	 */
	private void startRound(int numeroRound)
	{
		model.mazzoPesca = new Mazzo();
		model.mazzoPesca.creaMazzo();
		model.mazzoPesca.mischia();
		model.pilaDegliScarti = new Mazzo();
		
		model.setDichiarazioneUNO(false);
		model.setTurno(0);
		model.setTurnoInvertito(false);
		pulisciLog();
		aggiungiMessaggioLog("Round "+ numeroRound +" iniziato, buona fortuna!");
		scartaCartaDalMazzoNellaPila(); //Scarto della prima carta
		
		//Distribuzione delle carte ai partecipanti
		for(int i = 0; i<7; i++)
		{
			distribuisciCartaInManoGiocatore(model.mazzoPesca.rimuoviCarta());
			distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), model.manoNPC1, 1);
			distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), model.manoNPC2, 2);
			distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), model.manoNPC3, 3);
		}

		toggleInterfaccia(true);
	}

	
	/**
	 * <p>Crea la tabella dei punteggi</p>
	 */
	private void creaTabellaPunteggi()
	{
		String[] colonne = new String[] {"Giocatore", "Punteggio", "Carte"};
        Object[][] campi = new Object[][] 
        {
        	{modelUtente.getNomeUtente(), 0, 0},
            {view.profiloNPC1.labelNomeUtente.getText(), 0, 0},
            {view.profiloNPC2.labelNomeUtente.getText(), 0, 0},
            {view.profiloNPC3.labelNomeUtente.getText(), 0, 0}
        };
        view.tabellonePuntiPartita = new DefaultTableModel(campi, colonne);
        view.punteggi = new JTable(view.tabellonePuntiPartita);
        view.punteggi.setDefaultEditor(Object.class, null); 
        view.scrollPanePunteggi = new JScrollPane(view.punteggi);
        view.scrollPanePunteggi.setBounds(1150, 450, 300, 90);
        view.add(view.scrollPanePunteggi);
	}

	
	/**
	 * <p>Attiva/disattiva l'interfaccia<p>
	 * @param set valore vero/falso per rispettivamente attivare/disattivare l'interfaccia
	 */
	private void toggleInterfaccia(boolean set)
	{
		Component[] componenti = view.cardPanel.getComponents();
		for (Component component : componenti) 
		    {component.setEnabled(set);} 
		
		view.bottonePesca.setEnabled(set);
		view.bottoneUNO.setEnabled(set);
	}
	
	
	/**
	 * <p>Avvia una finestra di dialogo per selezionare il colore in seguito allo scarto di una carta nero</p>
	 * @param cartaDaDistribuire carta nera che avvia il dialog e di cui modificare il colore
	 */
	private void selezioneColore(Carta cartaDaDistribuire)
	{
		//Creazione del panel da aggiungere al dialog
        JPanel panelPescaCarta = new JPanel();
        panelPescaCarta.setLayout(new BoxLayout(panelPescaCarta, BoxLayout.Y_AXIS));
        
        //Creazione del label di prompt
        JLabel labelPrompt = new JLabel("Selezionare il nuovo colore");
        labelPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPescaCarta.add(labelPrompt);
        
		//Creazione del label da aggiungere al dialog
        JLabel labelCartaPescata = new ImmagineCarta(cartaDaDistribuire.getDescrizione(), 10, 10);
        labelCartaPescata.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPescaCarta.add(labelCartaPescata);
        
        //Creazione del dialog per la scelta del colore
		
		int risultato = JOptionPane.showOptionDialog(null, panelPescaCarta, "Carta Pescata", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionsCartaPescata, optionsCartaPescata[0]);
		
		if(risultato == -1)
			{risultato = ThreadLocalRandom.current().nextInt(0, 4);}
		aggiungiMessaggioLog(modelUtente.getNomeUtente() + " ha scelto il colore " + optionsCartaPescata[risultato]);
		cartaDaDistribuire.setColore(optionsCartaPescata[risultato]);
		cartaDaDistribuire.setDescrizione(cartaDaDistribuire.getColore(), cartaDaDistribuire.getNumero());
		
	}
	
	
	/**
	 * <p>Ottine il nome del partecipante a seconda del turno fornitogli in input</p>
	 * @param turno turno del giocatore
	 * @return nome del giocatore appartente a quel turno
	 */
	private String getNomeUtenteGiocante(int turno)
	{
		String nomeGiocatore = "";
		switch(turno)
		{
			case 0:
				nomeGiocatore = modelUtente.getNomeUtente();
				break;
			case 1:
				nomeGiocatore = view.profiloNPC1.labelNomeUtente.getText();
				break;
			case 2:
				nomeGiocatore = view.profiloNPC2.labelNomeUtente.getText();
				break;
			case 3:
				nomeGiocatore = view.profiloNPC3.labelNomeUtente.getText();
				break;
		}
		return nomeGiocatore;
	}
	
	/**
	 * <p>Ottine la mano di carte di un giocatore in dipendenza del turno fornito in input</p>
	 * @param turno turno associato al partecipante di cui si vuole ottenere la mano
	 * @return mano di carte di un partecipante
	 */
	private ArrayList<Carta> getManoPartecipante(int turno)
	{
		switch(turno)
		{
			case 0:
				return model.manoGiocatore;
			case 1: 
				return model.manoNPC1;
			case 2: 
				return model.manoNPC2;
			case 3: 
				return model.manoNPC3;
			default:
				return null;
		}
	}
	
	/**
	 * <p>Attiva l'effetto della carta passatagli in input</p>
	 * @param cartaDaAttivare carta di cui attivare l'effetto
	 * @param turnoSuccessivo turno seguente al turno attuale in cui è stata scartata la carta, e sul quale eventualmente attivare un effetto
	 */
	private void attivaEffettoCarta(Carta cartaDaAttivare, int turnoSuccessivo)
	{
		if(cartaDaAttivare != null)
		{
			
			switch(cartaDaAttivare.getNumero())
			{
				case "PESCA2":
					if(turnoSuccessivo == 0)
					{
						for(int i = 0; i<2; i++)
						{
							checkMazzo();
							distribuisciCartaInManoGiocatore(model.mazzoPesca.rimuoviCarta());
						}
					}
					else
					{
						for(int i = 0; i<2; i++)
						{
							checkMazzo();
							distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), getManoPartecipante(turnoSuccessivo), turnoSuccessivo);
						}
					}
					aggiungiMessaggioLog(getNomeUtenteGiocante(turnoSuccessivo) + " pesca 2 carte");
					model.setTurno(turnoSuccessivo);
					break;
					
				case "PESCA4":
					if(turnoSuccessivo == 0)
					{
						for(int i = 0; i<4; i++)
						{
							checkMazzo();
							distribuisciCartaInManoGiocatore(model.mazzoPesca.rimuoviCarta());
						}
					}
					else
					{
						if(model.getTurnoAttuale() != 0)
						{
							int risultato = ThreadLocalRandom.current().nextInt(0, 4);
							aggiungiMessaggioLog(modelUtente.getNomeUtente() + " ha scelto il colore " + optionsCartaPescata[risultato]);
							cartaDaAttivare.setColore(optionsCartaPescata[risultato]);
							cartaDaAttivare.setDescrizione(cartaDaAttivare.getColore(), cartaDaAttivare.getNumero());
						}
						
						for(int i = 0; i<4; i++)
						{
							checkMazzo();
							distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), getManoPartecipante(turnoSuccessivo), turnoSuccessivo);
						}
					}
					aggiungiMessaggioLog(getNomeUtenteGiocante(turnoSuccessivo) + " pesca 4 carte");
					model.setTurno(turnoSuccessivo);
					break;
					
				case "SALTO":
					aggiungiMessaggioLog(getNomeUtenteGiocante(turnoSuccessivo) + " salta il turno");
					model.setTurno(turnoSuccessivo);
					model.setTurno(model.getTurnoAttuale());
					break;
					
				case "INVERTI":
					model.switchTurnoInvertito();
					model.setTurno(model.getTurnoAttuale());
					model.setTurno(model.getTurnoSuccessivo());
					break;
				
				default: //Numero 0-9
					model.setTurno(turnoSuccessivo);
					break;
			}
		}
		
		//Dopo aver attivato l'effeto, è necessario passare il turno
		passaTurno(model.getTurnoAttuale());
	}
	
	
	/**
	 * <p>Effettua tutti i passaggi necessari per terminare il turno e passare a quello successivo</p>
	 * @param turnoAttuale
	 */
	private void passaTurno(int turnoAttuale)
	{
		aggiungiMessaggioLog("È il turno di " + getNomeUtenteGiocante(turnoAttuale));
		
		
		if(model.getTurnoAttuale() == 0) //Se tocca al giocatore è necessario riattivare l'interfaccia
			{toggleInterfaccia(true);}
		else //Altrimenti si attiva l'IA dell'NPC
			{
				ArrayList<Carta> manoNPC = getManoPartecipante(turnoAttuale);
			
				Carta cartaDaScartare = manoNPC
					.stream()
					.filter(carta -> 
						carta.getColore().contains(model.pilaDegliScarti.get(0).getColore())  ||
						carta.getNumero().contains(model.pilaDegliScarti.get(0).getNumero())  ||
						carta.getColore().contains(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()))
					.findFirst()
				    .orElse(null);
				
				if(cartaDaScartare == null) //Nessuna carta scartabile
				{
					//L'NPC pesca una carta e la mantiene
					checkMazzo();
					
					aggiungiMessaggioLog(getNomeUtenteGiocante(turnoAttuale) + " pesca una carta");
					distribuisciCartaInManoNPC(model.mazzoPesca.rimuoviCarta(), manoNPC, turnoAttuale);
					attivaEffettoCarta(null, model.getTurnoSuccessivo());
				}
				else //Carta giocabile
				{
					if(cartaDaScartare.getColore().equals(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString()))
					{
						int risultato = ThreadLocalRandom.current().nextInt(0, 4);
						
						aggiungiMessaggioLog(getNomeUtenteGiocante(turnoAttuale) + " ha scelto il colore " + optionsCartaPescata[risultato]);
						cartaDaScartare.setColore(optionsCartaPescata[risultato]);
						cartaDaScartare.setDescrizione(cartaDaScartare.getColore(), cartaDaScartare.getNumero());
					}
					
					view.animazioneScartoDaMano(cartaDaScartare);
					
					manoNPC.remove(cartaDaScartare);
					model.pilaDegliScarti.aggiungiCarta(cartaDaScartare);
					aggiungiMessaggioLog(getNomeUtenteGiocante(turnoAttuale) + " scarta " + cartaDaScartare.getDescrizione());
					view.tabellonePuntiPartita.setValueAt(manoNPC.size(), turnoAttuale, 2);
					
					if(manoNPC.size() == 0)
					{
						AudioPlayer.riproduci(EnumFileAudio.UNO.toString(), ModelOpzioni.getVolume());
						endRound();
					}
					else
						{attivaEffettoCarta(cartaDaScartare, model.getTurnoSuccessivo());}
				}
			}
	}
	
	/**
	 * <p>Effettua i calcoli necessari da fare durante la fine di un round, poi se nessuno ha ancora vinto la partita ne incomincia un'altro</p>
	 */
	private void endRound()
	{
		//Calcolo e mostra del punteggio
		int punteggio = calcolaPunteggio();
		aggiungiMessaggioLog(getNomeUtenteGiocante(model.getTurnoAttuale()) + " vince il round con un punteggio di" + Integer.toString(punteggio));
		view.dialogVittoria(getNomeUtenteGiocante(model.getTurnoAttuale()), Integer.toString(punteggio));
		view.tabellonePuntiPartita.setValueAt(punteggio + (int) view.tabellonePuntiPartita.getValueAt(model.getTurnoAttuale(), 1), model.getTurnoAttuale(), 1);
		
		//Pulizia delle mani dei giocatori
		model.manoGiocatore = new Mazzo();
		Component[] componenti = view.cardPanel.getComponents();
		for (Component component : componenti)
		    {view.cardPanel.remove(component);}
		model.manoNPC1 = new Mazzo();
		model.manoNPC2 = new Mazzo();
		model.manoNPC3 = new Mazzo();
		model.incrementaNumeroRound();
		
		
		if(model.getPUNTI_NECESSARI_VITTORIA() < (int) view.tabellonePuntiPartita.getValueAt(model.getTurnoAttuale(), 1)) //Qualcuno ha vinto: si termina la partita
		{
			//Avvio del dialogo di vittoria
			view.dialogFinePartita(getNomeUtenteGiocante(model.getTurnoAttuale()));
			
			//Aggiornameto delle statistiche del giocatore
			modelUtente.setPartiteGiocate(modelUtente.getPartiteGiocate() + 1);
			if(model.getTurnoAttuale() == 0) //La vittoria è dell'utente
			{
				modelUtente.setExp(modelUtente.getExp() + (int) view.tabellonePuntiPartita.getValueAt(model.getTurnoAttuale(), 1));
				modelUtente.setPartiteVinte(modelUtente.getPartiteVinte() + 1);
			}
			else //La vittoria è di un NPC
				{modelUtente.setPartitePerse(modelUtente.getPartitePerse() + 1);}
			
			
			//Salvataggio delle statistiche del giocatore
			try
			{
				FileOutputStream fileOs = new FileOutputStream("configs\\Profilo.txt");
				ObjectOutputStream os = new ObjectOutputStream(fileOs);
				os.writeObject(this.modelUtente);
				os.close();
			}
			catch (IOException e) 
			{
				StringBuilder str = new StringBuilder();
				str.append("Eccezione generata durante la scrittura del file di profilo (errore IO):\n");
				str.append(e);
				JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
			}
			catch (Exception e)
			{
				StringBuilder str = new StringBuilder();
				str.append("Eccezione generata durante la scrittura del file di profilo:\n");
				str.append(e);
				JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
			}
			
			//Ritorno al menu
			MainFrame.rimuoviComponent(MainFrame.viewPartita);
			MainFrame.menuDiGioco = new MenuDiGioco();
			MainFrame.aggiungiComponent(MainFrame.menuDiGioco);
		}
		else //Nessuno ha ancora vinto
			{startRound(model.getNumeroRound());} //Si procede al prossimo round
	}
	
	
	/**
	 * <p>Calcola il punteggio di fine round</p>
	 * @return il punteggio di fine round
	 */
	private int calcolaPunteggio()
	{
		punteggio = 0;
		ValoreCarta listaValoreCarta[] = Mazzo.ValoreCarta.values();
		
		//Aggiunta di tutte le carte in una lista per il calcolo
		ArrayList<Carta> carteAvversari = new ArrayList<Carta>();
		for(int i = 0; i<4; i++)
			{carteAvversari.addAll(getManoPartecipante(i));}
		
		//Calcolo del punteggio su tutte le carte
		carteAvversari.stream()
		.forEach(
					carta ->
					{
						String numeroCarta = carta.getNumero();
						if(numeroCarta.equals(Mazzo.ValoreCarta.INVERTI.toString()) 
								|| numeroCarta.equals(Mazzo.ValoreCarta.PESCA2.toString())
								|| numeroCarta.equals(Mazzo.ValoreCarta.SALTO.toString()))
							{punteggio += PUNTEGGIO_CARTE_STANDARD;}
						else if (numeroCarta.equals(Mazzo.ValoreCarta.ValoreCartaNera.JOLLY.toString())  
								|| numeroCarta.equals(Mazzo.ValoreCarta.ValoreCartaNera.PESCA4.toString()))
							{punteggio += PUNTEGGIO_CARTE_JOLLY;}
						else
						{
							for (int i=0; i<10; i++) 
							{
								if(listaValoreCarta[i].toString().equals(numeroCarta))
								{
									punteggio += i;
									break;
								}
					        }
						}
					}
				);
		return punteggio;
	}
	
	
	/**
	 * <p>Controlla che il mazzo abbia 0 carte. In tal caso lo scambia con la pila degli scarti</p>
	 */
	private void checkMazzo()
	{
		//Caso di fine mazzo - rimescolamento della pila degli scarti come nuovo mazzo di pesca
		if(model.mazzoPesca.size() == 0)
		{
			AudioPlayer.riproduci(EnumFileAudio.cardShuffle.toString(), ModelOpzioni.getVolume());
			aggiungiMessaggioLog("Carte terminate, rimescolo della pila in corso");
			
			//Cambio a colore nero le carte JOLLY
			for (Carta cartaPila : model.pilaDegliScarti)
			{
				if(cartaPila.getNumero().equals(Mazzo.ValoreCarta.ValoreCartaNera.JOLLY.toString())  || cartaPila.getNumero().equals(Mazzo.ValoreCarta.ValoreCartaNera.PESCA4.toString()))
					{
						cartaPila.setColore(Mazzo.ColoreCarta.ColoreCartaJolly.NERO.toString());
						cartaPila.setDescrizione(cartaPila.getColore(), cartaPila.getNumero());
					}
			}

			//Scambio la pila con il mazzo
			model.mazzoPesca = new Mazzo(model.pilaDegliScarti);
			model.mazzoPesca.mischia();
			model.pilaDegliScarti = new Mazzo();
			view.remove(view.labelPilaScarti);
			view.aggiungiPilaDegliScarti("");
			scartaCartaDalMazzoNellaPila();
			
			view.repaint();
			view.revalidate();
		}
	}
	

	
}