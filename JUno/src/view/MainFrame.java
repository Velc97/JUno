package view;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import controller.ControllerOpzioni;

/**
 * Progetto Juno - Classe frame principale
 * <p>È la finestra principale del videogioco, che al suo interno contiene i panel creati dinamicamente ad-hoc</p>
 * <p>Utilizza il pattern singleton</p>
 * @see MenuDiGioco
 * @see ViewPartita
 * @see ViewOpzioni
 * @see ViewProfiloUtente
 */
public class MainFrame 
{
	/**
	 * <p>Memorizza l'istanza della classe</p>
	 */
	private static MainFrame instance;
	
	/**
	 * <p>Memorizza la finestra principale del gioco</p>
	 */
	public static JFrame framePrincipale;
	
	/**
	 * <p>Memorizza il panel contente il menu di gioco</p>
	 */
	public static MenuDiGioco menuDiGioco; 
	
	/**
	 * <p>Memorizza il panel della partita</p>
	 */
	public static ViewPartita viewPartita;
	
	/**
	 * <p>Memorizza il panel delle opzioni</p>
	 */
	public static ViewOpzioni viewOpzioni;
	
	/**
	 * <p>Memorizza il panel del profilo utente</p>
	 */
	public static ViewProfiloUtente viewProfiloUtente;
	
	/**
	 * <p>Ottine l'istanza della classe </p>
	 * @return istanza della classe
	 */
    public static MainFrame getInstance() 
    {
        if (instance == null) 
        	{instance = new MainFrame();}
        return instance;
    }
	
    /**
     * <p>Costruttore della classe</p>
     * <p>Imposta le caratteristiche della finestra e controlla l'esistenza del file di opzioni</p>
     */
	private MainFrame()
	{
	    framePrincipale = new JFrame("JUno"); 
	    ImageIcon img = new ImageIcon("resources\\logo\\icona.png");
	    framePrincipale.setIconImage(img.getImage());
	    framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    framePrincipale.setLayout(null);
	    framePrincipale.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	    framePrincipale.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    framePrincipale.setVisible(true);
	    
	    //Caricamento del file di opzioni all'avvio del gioco
	    new ControllerOpzioni(new ViewOpzioni());
	    
	    menuDiGioco = new MenuDiGioco();
	    aggiungiComponent(menuDiGioco);
	}		
	
	/**
	 * <p>Aggiunge un panel alla finestra principale</p>
	 * @param component panel da aggiungere
	 */
	public static void aggiungiComponent(JComponent component)
	{
		framePrincipale.getContentPane().add(component);
		framePrincipale.revalidate();
		framePrincipale.repaint();
	}
	
	/**
	 * <p>Rimuove un panel alla finestra principale</p>
	 * @param component panel da rimuovere
	 */
	public static void rimuoviComponent(JComponent component)
	{
		framePrincipale.remove(component);
		framePrincipale.repaint();
	}

}
