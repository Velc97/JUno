package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import juno.AudioPlayer;
import juno.AudioPlayer.EnumFileAudio;
import model.ModelOpzioni;

/**
 * Progetto Juno - Classe vista profilo utente
 * <p>Contiene il panel del profilo utente</p>
 * <p>Estende JPanel</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see JPanel
 * @see controller.ControllerProfiloUtente
 * @see model.ModelProfiloUtente
 */
public class ViewProfiloUtente  extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bottone per tornare al menu principale
	 */
	private JButton bottoneIndietro = new JButton("Indietro");
	
	/**
	 * Bottone per salvare eventuali modifiche
	 */
	public JButton bottoneSalva = new JButton("Salva");
	
	/**
	 * Bottone per ripristinare il profilo ai valori principali
	 */
	public JButton bottoneRipristina = new JButton("Ripristina");
	
	/**
	 * Bottone per cambiare immagine avatar
	 */
	public JButton bottoneCambiaAvatar = new JButton("Cambia Avatar");
	
	/**
	 * Bottone per cambiare nome
	 */
	public JButton bottoneCambiaNome = new JButton("Cambia Nome");
	
	/**
	 * Panel che mostra il profilo dell'utente
	 */
	public PanelProfiloUtente panelProfiloUtente;
	
	/**
	 * Panel che mostra le statistiche dell'utente
	 */
	public PanelStatisticheUtente panelStatisticheUtente;
	
	/**
	 * Memorizza se è stata fatta una modifica
	 */
	private boolean modificaEffettuata;
	
	/**
	 * Costruttore della classe
	 * <p>Costruisce il panel per il profilo utente</p>
	 */
	public ViewProfiloUtente()
	{
		this.setBounds(600, 250, 700, 400);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		this.setLayout(null);
		
		setLayoutBottoni();
		setBoundsBottoni();
		setActionBottoni();
		addComponents();
	}
	
	/**
	 * <p>Imposta il layout dei bottoni</p>
	 */
	private void setLayoutBottoni()
	{
		bottoneSalva.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneIndietro.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneRipristina.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneCambiaAvatar.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		bottoneCambiaNome.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
	}
	
	/**
	 * <p>Imposta dimensione e posizione dei bottoni</p>
	 */
	private void setBoundsBottoni()
	{
		bottoneSalva.setBounds(485, 20, 200, 65);
		bottoneIndietro.setBounds(485, 90, 200, 65);
		bottoneRipristina.setBounds(485, 160, 200, 65);
		bottoneCambiaAvatar.setBounds(485, 230, 200, 65);
		bottoneCambiaNome.setBounds(485, 300, 200, 65);
	}
	
	/**
	 * <p>Aggiunge gli eventi ai bottoni</p>
	 */
	private void setActionBottoni()
	{
		//Bottone per tornare indietro al menu principale
		bottoneIndietro.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci(EnumFileAudio.buttonClick.toString(), ModelOpzioni.getVolume());
				int risposta = 0;
				
				if(modificaEffettuata)
				{
					String[] testoBottoni = {"Continua","Annulla"};
					risposta = JOptionPane.showOptionDialog(null,
	        	        		"Ci sono dei cambiamenti non salvati. Continuando verranno persi.", 
	        	        		"Attenzione!", 
	        	        		JOptionPane.YES_NO_OPTION, 
	        	        		JOptionPane.QUESTION_MESSAGE,
	        	        		null,
	        	        		testoBottoni,
	        	        		testoBottoni[1]);
				}
       	        
				if(risposta == 0)
				{
					setModificaEffettuata(false);
					MainFrame.rimuoviComponent(MainFrame.viewProfiloUtente);
					MainFrame.menuDiGioco = new MenuDiGioco();
					MainFrame.aggiungiComponent(MainFrame.menuDiGioco);
				}
	        }
		});
	}
	
	/**
	 * <p>Aggiunge i componenti al panel</p>
	 */
	private void addComponents()
	{
		this.add(bottoneIndietro);
		this.add(bottoneSalva);
		this.add(bottoneRipristina);
		this.add(bottoneCambiaAvatar);
		this.add(bottoneCambiaNome);
	}
	
	/**
	 * <p>Modifica il valore di modificaEffettuata quando si effettuano evenutali modifiche al profilo utente</p>
	 * @param valoreDaSettare valore booleano da assegnare a modificaEffettuata
	 */
	public void setModificaEffettuata(boolean valoreDaSettare)
		{modificaEffettuata = valoreDaSettare;}
}
