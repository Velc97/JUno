package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Progetto Juno - Classe Panel profilo Utente
 * <p>È il panel che si occupa di mostrare le informazioni relative all'utente</p>
 * <p>Estende JPanel</p>
 * @see JPanel
 */
public class PanelProfiloUtente extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Label "avatar"</p>
	 */
	public JLabel labelAvatar;
	
	/**
	 * <p>label "livello"</p>
	 */
	private JLabel labelLivello;
	
	/**
	 * <p>Label contente il livello del giocatore</p>
	 */
	private JLabel labelValoreLivello;
	
	/**
	 * <p>Label contente il nome dell'utente</p>
	 */
	public JLabel labelNomeUtente;
	
	/**
	 * Costruttore della classe
	 * <p>Crea il panel del profilo utente</p>
	 * @param pathAvatar percorso al file avatar dell'utente
	 * @param exp valore dell'esperiezna dell'utente
	 * @param nomeutente nome dell'utente
	 */
	public PanelProfiloUtente(String pathAvatar, int exp, String nomeutente)
	{
		this.setSize(450, 205);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.setLayout(null);
		
		setLabel(pathAvatar, exp, nomeutente);
	}
	
	/**
	 * <p>Imposta il livello del giocatore</p>
	 * @param exp valore dell'esperienza da impostare
	 * @return livello del giocatore calcolato dal valore dell'esperienza
	 */
	private String setLivello(int exp)
	{
		String livello = String.valueOf(exp);
		
		//Il livello è calcolato come exp/2
		if(livello.length() > 2)
			{return livello = livello.substring(0, livello.length()-2);}
		else //Sotto 100 exp il livello è 0
			{return livello = "0";}
	}
	
	/**
	 * <p>Imposta il percorso all'immagien avatar del giocatore</p>
	 * @param pathAvatar percorso all'immagine avatar
	 */
	public void setAvatar(String pathAvatar)
	{
		try
		{
			labelAvatar = new JLabel();
			labelAvatar.setBounds(10, 10, 180, 180);
		    ImageIcon icon = new ImageIcon(pathAvatar);
		    Image img = icon.getImage();
		    Image imgScale = img.getScaledInstance(labelAvatar.getWidth(), labelAvatar.getHeight(), Image.SCALE_SMOOTH);
		    ImageIcon imageIcon = new ImageIcon(imgScale);
		    labelAvatar.setIcon(imageIcon);
		    this.add(labelAvatar);
		}
		catch(Exception e)
			{e.printStackTrace();}
	}
	
	/**
	 * <p>Imposta il nome dell'utente</p>
	 * @param nomeutente nome dell'utente da impostare
	 */
	private void setNomeUtente(String nomeutente)
	{
		labelNomeUtente = new JLabel(nomeutente, SwingConstants.LEFT);
		labelNomeUtente.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		labelNomeUtente.setBounds(240, 20, 190, 40);
		
		this.add(labelNomeUtente);
	}
	
	/**
	 * <p>Imposta i label necessari per le informazioni del profilo utente</p>
	 * @param pathAvatar percorso all'immagine avatar
	 * @param exp exp valore dell'esperienza da impostare
	 * @param nomeutente nomeutente nome dell'utente da impostare
	 */
	private void setLabel(String pathAvatar, int exp, String nomeutente)
	{
		setAvatar(pathAvatar);
		setNomeUtente(nomeutente);
		
		labelLivello = new JLabel("Livello:", SwingConstants.LEFT);
		labelLivello.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		labelLivello.setBounds(240, 60, 150, 40);
		this.add(labelLivello);
		
		labelValoreLivello = new JLabel(setLivello(exp), SwingConstants.RIGHT);
		labelValoreLivello.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		labelValoreLivello.setBounds(350, 60, 80, 40);
		this.add(labelValoreLivello);
	}
}
