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

import javax.swing.JOptionPane;

import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.ModelOpzioni;
import view.ViewOpzioni;

/**
 * Progetto Juno - Classe controller opzioni
 * <p>Controller tra ViewOpzioni e ModelOpzioni</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see view.ViewOpzioni
 * @see model.ModelOpzioni
 */
public class ControllerOpzioni 
{
	/**
	 * Vista del controller
	 * @see view.ViewOpzioni
	 */
	private ViewOpzioni view;
	
	/**
	 * Percorso della cartella contente il file di opzioni
	 */
	private String pathCartellaOpzioni = "configs";
	
	/**
	 * Percorso del file di opzioni
	 */
	private String pathFileOpzioni = pathCartellaOpzioni + "\\Opzioni.txt";
	
	/**
	 * Cartella contente il file di opzioni
	 */
	private File cartellaOpzioni = new File(pathCartellaOpzioni);   
	
	/**
	 * File contente i dati delle opzioni impostate dall'utente
	 */
	private File fileOpzioni = new File(pathFileOpzioni);

	
	/**
	 * Costruttore della classe
	 * <p>Controlla l'esistenza della carte e del file di opzioni, li crea (se necessario) e li legge</p>
	 * @param view Vista opzioni
	 * @see view.ViewOpzioni
	 */
	public ControllerOpzioni(ViewOpzioni view)
	{
		this.view = view;
		
		if(!controllaEsistenzaFile())
		{
			JOptionPane.showMessageDialog(null, "File o cartella di opzioni inesistente. Ne verranno creati di nuovi", "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		     
			ModelOpzioni.ripristina();
		    creaCartellaOpzioni();
		    creaFileOpzioni();
		}
		
		leggiFileOpzioni();
		setActionBottoni();
	}
	
	
	/**
	 * <p>Restituisce la vista opzioni</p>
	 * @return vista opzioni
	 * @see view.ViewOpzioni
	 */
	public ViewOpzioni getView()
		{return this.view;}
	
	/**
	 * <p>Controlla l'esistenza del file di opzioni</p>
	 * @return true o false rispettivamente a seconda dell'esistenza o meno del file
	 */
	private boolean controllaEsistenzaFile()
	{
		if(fileOpzioni.exists() && !fileOpzioni.isDirectory()) 
			{return true;}
		else
			{return false;}
	}
	
	/**
	 * <p>Crea la cartella contente il file di opzioni, se non esiste</p>
	 */
	private void creaCartellaOpzioni()
	{
	     if(!cartellaOpzioni.exists()) 
	     	{cartellaOpzioni.mkdirs();}
	}
	
	/**
	 * <p>Crea il file di opzioni</p>
	 */
	private void creaFileOpzioni()
	{
		 try
		 {
			FileOutputStream fileOs = new FileOutputStream(pathFileOpzioni);
			ObjectOutputStream os = new ObjectOutputStream(fileOs);

			os.writeInt(ModelOpzioni.getVolume());
			os.writeBoolean(ModelOpzioni.getDescrizioneCarte());
			os.close();
		 }
		 catch (IOException e) 
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la scrittura del file di opzioni (errore IO):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 }
		 catch (Exception e)
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la scrittura del file di opzioni:\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 }
	}
	
	/**
	 * <p>Legge il file di opzioni</p>
	 */
	private void leggiFileOpzioni()
	{
		 try
		 {
			FileInputStream fileOs = new FileInputStream(pathFileOpzioni);
			ObjectInputStream os = new ObjectInputStream(fileOs);
			int volumeDaSettare = os.readInt();
			boolean DescrizioneDaSettare = os.readBoolean();
			
			os.close();
			
			ModelOpzioni.setVolume(volumeDaSettare); 
			ModelOpzioni.setDescrizioneCarte(DescrizioneDaSettare);
			view.setOpzioni(ModelOpzioni.getVolume(), ModelOpzioni.getDescrizioneCarte());
		 }
		 catch (FileNotFoundException e)
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la lettura del file di opzioni (file non trovato):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 } 
		 catch (IOException e) 
		 {
			StringBuilder str = new StringBuilder();
			str.append("Eccezione generata durante la lettura del file di opzioni (errore IO):\n");
			str.append(e);
			JOptionPane.showMessageDialog(null, str, "Attenzione!", JOptionPane.WARNING_MESSAGE); 
		 } 
		 finally
		 	{view.setModificaEffettuata(false);}
	}
	
	/**
	 * <p>Aggiunge gli eventi ai bottoni</p>
	 */
	private void setActionBottoni()
	{
		//Aggiunge l'evento di ripristino sul bottone
		this.view.bottoneRipristina.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				String[] testoBottoni = {"Continua","Annulla"};
				int risposta = JOptionPane.showOptionDialog(null,
        	        		"Vuoi davvero ripristinare le impostazioni ai valori di default?", 
        	        		"Attenzione!", 
        	        		JOptionPane.YES_NO_OPTION, 
        	        		JOptionPane.QUESTION_MESSAGE,
        	        		null,
        	        		testoBottoni,
        	        		testoBottoni[1]);
				if(risposta == 0)
				{
					ModelOpzioni.ripristina();
					view.setOpzioni(ModelOpzioni.getVolume(), ModelOpzioni.getDescrizioneCarte());
				}
	        }
		});
		
		//Aggiunge l'evento per salvare le impostazioni nel file di opzioni
		this.view.bottoneSalva.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				ModelOpzioni.setVolume(view.sliderVolume.getValue());
				ModelOpzioni.setDescrizioneCarte(view.checkDescrizioneCarte.isSelected());
				creaCartellaOpzioni();
				creaFileOpzioni();
				view.setModificaEffettuata(false);
	        }
		});
	}
	
}
