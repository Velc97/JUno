package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ControllerOpzioni;
import controller.ControllerPartita;
import controller.ControllerProfiloUtente;
import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.ModelOpzioni;
import model.ModelPartita;
import model.ModelProfiloUtente;

/**
 * Progetto Juno - Classe Menu di gioco
 * <p>È il menu principale del gioco</p>
 * @see ViewPartita
 * @see ViewOpzioni
 * @see ViewProfiloUtente
 */
public class MenuDiGioco extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Bottone per effettuare una nuova partita</p>
	 */
	private JButton bottoneGioca = new JButton("Gioca");
	
	/**
	 * <p>Bottone per mostrare il panel delle opzioni</p>
	 */
	private JButton bottoneOpzioni = new JButton("Opzioni");
	
	/**
	 * <p>Bottone per mostrare i crediti di gioco</p>
	 */
	private JButton bottoneCrediti = new JButton("Crediti");
	
	/**
	 * <p>Bottone per uscire dal gioco</p>
	 */
	private JButton bottoneEsci = new JButton("Esci");
	
	/**
	 * <p>Bottone per mostrare il panel per il profilo utente</p>
	 */
	private JButton bottoneProfiloUtente = new JButton("Profilo utente"); 
	
	/**
	 * <p>Bottone per mostrare il manuale per il profilo utente</p>
	 */
	private JButton bottoneManuale = new JButton("Come Giocare");
	
	/**
	 * <p>Label contente l'immagine del logo</p>
	 */
	private JLabel labelLogo;
	
	/**
	 * <p>Label per il testo sotto il logo del gioco</p>
	 */
	private JLabel labelSubText = new JLabel("Made with Java");
	
	/**
	 * <p>Label per la versione del gioco</p>
	 */
	private JLabel labelVersione = new JLabel("Versione 1.0");
	
	/**
	 * <p>Stringa contente i crediti di gioco</p>
	 */
	private StringBuilder crediti = new StringBuilder();
	
	
	/**
	 * Costruttore della classe
	 * <p>Si occupa di mostrare il menu di gioco</p>
	 */
	public MenuDiGioco()
	{
		this.setBounds(600, 250, 700, 400);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		this.setLayout(null);
		
		setCrediti();
		setBoundsBottoni();
		setLayoutBottoni();
		setActionBottoni();
		setLogo();
		setLabel();
		
		addComponents();
	}
	
	
	
	private void setActionBottoni()
	{
		//Bottone per iniziare una nuova partita
		bottoneGioca.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  		
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				MainFrame.rimuoviComponent(MainFrame.menuDiGioco);
				ControllerProfiloUtente controllerProfiloUtente = new ControllerProfiloUtente(new ModelProfiloUtente(), new ViewProfiloUtente());
				ControllerPartita controllerPartita = new ControllerPartita(new ModelPartita(), controllerProfiloUtente.getModel(), new ViewPartita(), controllerProfiloUtente.getView());
				MainFrame.viewPartita = controllerPartita.getView();
				MainFrame.aggiungiComponent(MainFrame.viewPartita);
	        }
		});
		
		//Bottone per mostrare il panel delle opzioni
		bottoneOpzioni.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				MainFrame.rimuoviComponent(MainFrame.menuDiGioco);
				ControllerOpzioni controllerOpzioni = new ControllerOpzioni(new ViewOpzioni());
				MainFrame.viewOpzioni = controllerOpzioni.getView();
				MainFrame.aggiungiComponent(MainFrame.viewOpzioni);
	        }
		});
		
		//Bottone per mostrare i crediti
		bottoneCrediti.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				JOptionPane.showMessageDialog(null, crediti,"Crediti",JOptionPane.PLAIN_MESSAGE); 
	        }
		});
		
		//Bottone per uscire dal gioco
		bottoneEsci.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        	{System.exit(0);}
		});
		
		//Bottone per il manuale
		bottoneManuale.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
			{  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				try 
				{
				     File myFile = new File("resources/Manuale.pdf");
				     Desktop.getDesktop().open(myFile);
				} 
				catch (IOException ex) 
				{
				     StringBuilder str = new StringBuilder();
				     str.append("Eccezione I/O generata durante l'apertura del manuale:\n");
				     str.append(ex);
				     str.append("\nReinstallare il gioco");
				     JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
				}
				catch(Exception ex)
				{
				     StringBuilder str = new StringBuilder();
				     str.append("Eccezione generata durante l'apertura del manuale:\n");
				     str.append(ex);
				     str.append("\nReinstallare il gioco");
				     JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
				}
				        
	        }
		});
		
		//Bottone per il profilo utente
		bottoneProfiloUtente.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				MainFrame.rimuoviComponent(MainFrame.menuDiGioco);
				ControllerProfiloUtente controllerProfiloUtente = new ControllerProfiloUtente(new ModelProfiloUtente() , new ViewProfiloUtente());
				MainFrame.viewProfiloUtente = controllerProfiloUtente.getView();
				MainFrame.aggiungiComponent(MainFrame.viewProfiloUtente);
	        }
		});
		
	}
	
	/**
	 * <p>Imposta il layout dei bottoni</p>
	 */
	private void setLayoutBottoni()
	{
		bottoneGioca.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneOpzioni.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneCrediti.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneEsci.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneProfiloUtente.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		bottoneProfiloUtente.setBackground(Color.YELLOW);
		bottoneManuale.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		bottoneManuale.setBackground(Color.ORANGE);
	}
	
	/**
	 * <p>Imposta locazione e dimensione dei bottoni</p>
	 */
	private void setBoundsBottoni()
	{
		bottoneGioca.setBounds(50, 30, 200, 65);
		bottoneOpzioni.setBounds(50, 120, 200, 65);
		bottoneCrediti.setBounds(50, 210, 200, 65);
		bottoneEsci.setBounds(50, 300, 200, 65);
		bottoneProfiloUtente.setBounds(310, 320, 165, 30);
		bottoneManuale.setBounds(490, 320, 165, 30);
	}
	
	/**
	 * <p>Imposta il logo JUno del menu</p>
	 */
	private void setLogo()
	{
		labelLogo = new JLabel();
		labelLogo.setBounds(300, 10, 350, 250);
	    ImageIcon icon = new ImageIcon("resources/logo/logo.png");
	    Image img = icon.getImage();
	    Image imgScale = img.getScaledInstance(labelLogo.getWidth(), labelLogo.getHeight(), Image.SCALE_SMOOTH);
	    ImageIcon imageIcon = new ImageIcon(imgScale);
	    labelLogo.setIcon(imageIcon);
	}
	
	/**
	 * <p>Regola le impostazioni dei label (font, posizione, dimensioni)</p>
	 */
	private void setLabel()
	{
		labelSubText.setBounds(370, 270, 230, 30);
		labelSubText.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));
		
		labelVersione.setBounds(610, 370, 230, 30);
		labelVersione.setFont(new Font(Font.SERIF, Font.BOLD, 15));
	}
	
	/**
	 * <p>Imposta la stringa dei crediti</p>
	 */
	private void setCrediti()
	{
		crediti.append("Software interamente realizzato da\n");
		crediti.append("Cosmo Vellucci - 1760067 - Sapienza/Unitelma\n");
		crediti.append("Tutti gli asset utilizzati sono a licenza gratuita\n");
		crediti.append("JUno non è un prodotto registrato");
	}
	
	/**
	 * <p>Aggiunge le componenti</p>
	 */
	private void addComponents()
	{
		this.add(bottoneGioca);
		this.add(bottoneOpzioni);
		this.add(bottoneCrediti);
		this.add(bottoneEsci);
		this.add(bottoneManuale);
		this.add(bottoneProfiloUtente);
		this.add(labelLogo); 
		this.add(labelSubText);
		this.add(labelVersione);
	}
	

	
}
