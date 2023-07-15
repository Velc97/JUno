package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import juno.AudioPlayer;
import model.ModelOpzioni;

/**
 * Progetto Juno - Classe vista opzioni
 * <p>Contiene il panel delle opzioni impostate dal giocatore</p>
 * <p>Estende JPanel</p>
 * <p>È coinvolta nel pattern MVC</p>
 * @see JPanel
 * @see controller.ControllerOpzioni
 * @see model.ModelOpzioni
 */
public class ViewOpzioni extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Memorizza il bottone per tornare indietro
	 */
	private JButton bottoneIndietro = new JButton("Indietro");
	
	/**
	 * Memorizza il bottone per salvare le impostazioni
	 */
	public JButton bottoneSalva = new JButton("Salva");
	
	/**
	 * Memorizza il bottone per ripristinare le impostazioni al valore di default
	 */
	public JButton bottoneRipristina = new JButton("Ripristina");
	
	/**
	 * Memorza il label "OPZIONI" (titolo del panel)
	 */
	private JLabel labelOpzioni = new JLabel("OPZIONI");
	
	/**
	 * Memorizza il label "Volume"
	 */
	private JLabel labelVolume = new JLabel("Volume");
	
	/**
	 * Memorizza il label contente il valore del volume
	 */
	private JLabel labelValoreVolume;
	
	/**
	 * Memorizza il label per la spiegazione del volume
	 */
	private JLabel labelSpiegazioneVolume = new JLabel("Regola il volume dell'audio di gioco");
	
	/**
	 * Memorizza il checBox per attivare o meno la descrizione della carta 
	 */
	public JCheckBox checkDescrizioneCarte = new JCheckBox("", true);
	
	/**
	 * Memorizza il label "Descrizione Carte"
	 */
	private JLabel labelDescrizioneCarte = new JLabel("Descrizione Carte");
	
	/**
	 * Memorizza la spiegazi della descrizione delle carte
	 */
	private JLabel labelSpiegazioneDescrizioneCarte = new JLabel("Fa apparire la descrizione delle carte passandoci il puntatore sopra");
	
	/**
	 * Slider per regolare il volume
	 */
	public JSlider sliderVolume;
	
	/**
	 * Memorizza se una modifica è stata effettuata senza salvare
	 */
	public boolean modificaEffettuata;
	
	/**
	 * Costruttore della classe
	 * <p>Si occupa di costruire il panel delle opzioni</p>
	 */
	public ViewOpzioni()
	{
		this.setBounds(600, 250, 700, 400);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		this.setLayout(null);
		
		setBoundsBottoni();
		setLayoutBottoni();
		setActionBottoni();
		setInteragibili();
		setLabel();
		
		addComponents();
	}
	
	
	/**
	 * <p>Imposta le azioni sui bottoni</p>
	 */
	private void setActionBottoni()
	{
		//Pulsante che porta al menu principale
		bottoneIndietro.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        {  
				AudioPlayer.riproduci("resources\\audio\\click beep.wav", ModelOpzioni.getVolume());
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
					MainFrame.rimuoviComponent(MainFrame.viewOpzioni);
					MainFrame.menuDiGioco = new MenuDiGioco();
					MainFrame.aggiungiComponent(MainFrame.menuDiGioco);
				}
	        }
		});
	}
	
	/**
	 * <p>Imoposta il layout dei bottoni</p>
	 */
	private void setLayoutBottoni()
	{
		bottoneSalva.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneIndietro.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		bottoneRipristina.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
	}
	
	/**
	 * <p>Imposta posizione e dimensione dei bottoni</p>
	 */
	private void setBoundsBottoni()
	{
		bottoneSalva.setBounds(30, 300, 200, 65);
		bottoneIndietro.setBounds(250, 300, 200, 65);
		bottoneRipristina.setBounds(470, 300, 200, 65);
	}
	
	/**
	 * <p>Imposta font, dimensione, posizione dei label</p>
	 */
	private void setLabel()
	{
		labelOpzioni.setBounds(270, 30, 150, 25);
		labelOpzioni.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 35));
		labelOpzioni.setOpaque(true);
		labelOpzioni.setBackground(Color.cyan);
		
		labelVolume.setBounds(30, 70, 130, 30);
		labelVolume.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
		
		labelValoreVolume.setBounds(450, 70, 200, 30);
		labelValoreVolume.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
		
		labelSpiegazioneVolume.setBounds(135, 120, 200, 30);
		labelSpiegazioneVolume.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		
		labelDescrizioneCarte.setBounds(30, 170, 200, 30);
		labelDescrizioneCarte.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
		
		labelSpiegazioneDescrizioneCarte.setBounds(140, 200, 370, 30);
		labelSpiegazioneDescrizioneCarte.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
	}
	
	/**
	 * <p>Imposta i componenti interagibili delle opzioni</p>
	 */
	private void setInteragibili()
	{
		sliderVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
        sliderVolume.setMinorTickSpacing(2);
        sliderVolume.setMajorTickSpacing(10);
        sliderVolume.setPaintTicks(true);
        sliderVolume.setPaintLabels(true);
        sliderVolume.setBounds(130, 70, 300, 45);
        labelValoreVolume = new JLabel(String.valueOf(sliderVolume.getValue()));
        sliderVolume.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e) 
            	{
            	  labelValoreVolume.setText(String.valueOf(sliderVolume.getValue()));
            	  setModificaEffettuata(true);
            	}
        });
        
        checkDescrizioneCarte.setBounds(220, 170, 200, 30);
        checkDescrizioneCarte.addActionListener(new ActionListener()
		{  
			public void actionPerformed(ActionEvent e)
	        	{setModificaEffettuata(true);}
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
		this.add(labelOpzioni);
		this.add(labelVolume);
		this.add(labelValoreVolume);
		this.add(labelSpiegazioneVolume);
		this.add(labelDescrizioneCarte);
		this.add(labelSpiegazioneDescrizioneCarte);
		this.add(sliderVolume);
		this.add(checkDescrizioneCarte);
	}
	
	/**
	 * <p>Imposta il valore delle opzioni</p>
	 * @param volume valore del volume impostato per gli effetti audio
	 * @param descrizioneCarte indica se mostrare o meno la descrizione delle carte
	 */
	public void setOpzioni(int volume, boolean descrizioneCarte)
	{
		sliderVolume.setValue(volume);
		checkDescrizioneCarte.setSelected(descrizioneCarte);
	}
	
	/**
	 * <p>Imposta il valore della variabile modificaeffettuata</p>
	 * @param valoreDaSettare valore vero/falso da impostare per la modifica effettuata
	 */
	public void setModificaEffettuata(boolean valoreDaSettare)
		{modificaEffettuata = valoreDaSettare;}
}
