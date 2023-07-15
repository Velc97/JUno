package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.ModelOpzioni;
import model.ModelProfiloUtente;
import view.MainFrame;
import view.PanelProfiloUtente;
import view.PanelStatisticheUtente;
import view.ViewProfiloUtente;

/**
 * Progetto Juno - Classe controller profilo utente
 * <p>Controller tra ViewProfiloUtente e ModelProfiloUtente</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see view.ViewProfiloUtente
 * @see model.ModelProfiloUtente
 */
public class ControllerProfiloUtente 
{
	/**
	 * Vista del profilo utente
	 * @see view.ViewProfiloUtente
	 */
	private ViewProfiloUtente view;
	
	/**
	 * Model del profilo utente
	 * @see model.ModelProfiloUtente
	 */
	private ModelProfiloUtente model;
	
	/**
	 * Percorso alla cartella contenete i file di configurazione
	 */
	private String pathCartellaOpzioni = "configs";
	
	/**
	 * Percorso al file contentente le informazioni di profilo
	 */
	private String pathFileProfilo = pathCartellaOpzioni + "\\Profilo.txt";
	
	/**
	 * Cartella contente i file di opzioni
	 */
	private File cartellaOpzioni = new File(pathCartellaOpzioni);    
	
	/**
	 * Percorso contente il file contente le informazioni del profilo del giocatore
	 */
	private File fileProfilo = new File(pathFileProfilo);
	
	/**
	 * Cartella contente le immagini avatar
	 */
	private File pathDirAvatar = new File("resources\\avatar");

	
	/**
	 * Costruttore della classe
	 * <p>Costruisce la vista per modificare le impostazioni del profilo utente</p>
	 * @param model modello del controller
	 * @param view vista del controller
	 * @see view.ViewProfiloUtente
	 * @see model.ModelProfiloUtente
	 */
	public ControllerProfiloUtente(ModelProfiloUtente model, ViewProfiloUtente view) 
	{
		this.view = view;
		this.model = model;
		
		if(!controllaEsistenzaFile())
		{
			JOptionPane.showMessageDialog(null, "File profilo o cartella di opzioni inesistente. Ne verranno creati di nuovi", "Attenzione!", JOptionPane.WARNING_MESSAGE); 
			this.model.ripristina();
		    creaCartellaOpzioni();
		    creaFileProfilo();
		}
		
		leggiFileProfilo();
		aggiungiPanel();
		setActionBottoni();
	}

	/**
	 * <p>Restituisce il modello del controller</p>
	 * @return il modello del controller
	 * @see model.ModelProfiloUtente
	 */
	public ModelProfiloUtente getModel() 
		{return this.model;}
	
	/**
	 * <p>Restituisce la vista del controller</p>
	 * @return la vista del controller
	 * @see view.ViewProfiloUtente
	 */
	public ViewProfiloUtente getView()
		{return this.view;}

	/**
	 * <p>Controlla l'esistenza del file di profilo del giocatore</p>
	 * @return true/false a seconda dell'esistenza o meno del file di profilo del giocatore
	 */
	private boolean controllaEsistenzaFile()
	{
		if(fileProfilo.exists() && !fileProfilo.isDirectory()) 
			{return true;}
		else
			{return false;}
	}
	
	/**
	 * <p>Crea la cartella contente i file di opzioni</p>
	 */
	private void creaCartellaOpzioni()
	{
	     if(!cartellaOpzioni.exists()) 
	     	{cartellaOpzioni.mkdirs();}
	}
	
	
	/**
	 * <p>Aggiunge le azioni ai bottoni</p>
	 */
	private void setActionBottoni()
	{
		//Bottone per cambiare immagine di profilo
		view.bottoneCambiaAvatar.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				creaCartellaOpzioni();
				
				
    	        JFileChooser fileChooser=new JFileChooser();    
    	        fileChooser.setDialogTitle("Selezione Avatar");
    	        fileChooser.setCurrentDirectory(pathDirAvatar);
    	        if(fileChooser.showOpenDialog(MainFrame.framePrincipale) == JFileChooser.APPROVE_OPTION)
    	        {    
    	            File fileLetto=fileChooser.getSelectedFile();
    	            String filepath=fileLetto.getPath();
    	            filepath = filepath.replace("\\", "\\\\");
    	            view.panelProfiloUtente.remove(view.panelProfiloUtente.labelAvatar);
    	            model.setPathAvatar(filepath);
    	            view.panelProfiloUtente.setAvatar(filepath);
    	            view.panelProfiloUtente.repaint();
					
    	            view.setModificaEffettuata(true);
    	        }
    	        
				
	        }
		});
		
		//Bottone per cambiare nome utente
		view.bottoneCambiaNome.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				String nuovoNomeUtente = JOptionPane.showInputDialog(null,"Inserire il nuovo nome del profilo");
				
				if(nuovoNomeUtente != null) //Premuto ok
				{
					if(nuovoNomeUtente == null || nuovoNomeUtente.isEmpty() || nuovoNomeUtente.isBlank()) //Nome Non valido
						{JOptionPane.showMessageDialog(null, "Nome utente non valido, inserirne un'altro", "Attenzione!", JOptionPane.WARNING_MESSAGE);}
					else
					{
						model.setNomeUtente(nuovoNomeUtente);
						view.panelProfiloUtente.labelNomeUtente.setText(nuovoNomeUtente);
						view.setModificaEffettuata(true);
					}
				}
	        }
		});
		
		//Bottone per ripristinare le impostazioni del profilo ai valori di default
		view.bottoneRipristina.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				String[] testoBottoni = {"Continua","Annulla"};
				int risposta = JOptionPane.showOptionDialog(null,
        	        		"Vuoi davvero ripristinare il profilo e le sue statistiche ai valori di default?", 
        	        		"Attenzione!", 
        	        		JOptionPane.YES_NO_OPTION, 
        	        		JOptionPane.QUESTION_MESSAGE,
        	        		null,
        	        		testoBottoni,
        	        		testoBottoni[1]);
				if(risposta == 0)
				{
					model.ripristina();
					view.remove(view.panelProfiloUtente);
					view.remove(view.panelStatisticheUtente);
					view.repaint();
					aggiungiPanel();
					view.setModificaEffettuata(true);
				}
	        }
		});
		
		//Bottone per salvare le impostazioni del profilo
		view.bottoneSalva.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				creaCartellaOpzioni();
				creaFileProfilo();
				view.setModificaEffettuata(false);
	        }
		});
	}
	
	/**
	 * <p>Crea il file di profilo</p>
	 */
	private void creaFileProfilo()
	{
		 try
		 {
			FileOutputStream fileOs = new FileOutputStream(pathFileProfilo);
			ObjectOutputStream os = new ObjectOutputStream(fileOs);
			os.writeObject(this.model);
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
	}

	/**
	 * <p>Legge le informazioni contenute nel file di profilo</p>
	 */
	private void leggiFileProfilo()
	{
		 try
		 {
			FileInputStream fileOs = new FileInputStream(pathFileProfilo);
			ObjectInputStream os = new ObjectInputStream(fileOs);
			Object obj = os.readObject();
			ModelProfiloUtente modelProfiloUtenteLetto = ModelProfiloUtente.class.cast(obj);
			os.close();
			
			
			this.model.setPathAvatar(modelProfiloUtenteLetto.getPathAvatar());
			this.model.setExp(modelProfiloUtenteLetto.getExp());
			this.model.setNomeUtente(modelProfiloUtenteLetto.getNomeUtente());
			this.model.setPartiteGiocate(modelProfiloUtenteLetto.getPartiteGiocate());
			this.model.setPartiteVinte(modelProfiloUtenteLetto.getPartiteVinte());
			this.model.setPartitePerse(modelProfiloUtenteLetto.getPartitePerse());
		 }
		 catch (FileNotFoundException e)
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la lettura del file di profilo (file non trovato):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 } 
		 catch (IOException e) 
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la lettura del file di profilo (errore IO):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 } 
		 catch (ClassNotFoundException e) 
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la scrittura del file di profilo (classe non trovata):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 } 
		 finally
		 	{view.setModificaEffettuata(false);}
	}
	
	/**
	 * <p>Aggiunge i panel alla vista</p>
	 */
	private void aggiungiPanel()
	{
	    this.view.panelProfiloUtente = new PanelProfiloUtente(this.model.getPathAvatar(), this.model.getExp(), this.model.getNomeUtente());
	    this.view.panelProfiloUtente.setLocation(30, 20);
	    this.view.add(this.view.panelProfiloUtente);
		
	    this.view.panelStatisticheUtente = new PanelStatisticheUtente(this.model.getPartiteGiocate(), this.model.getPartiteVinte(), this.model.getPartitePerse(), this.model.calcolaRateo());
	    this.view.panelStatisticheUtente.setLocation(30, 230);
	    this.view.add(this.view.panelStatisticheUtente);
	}
}
