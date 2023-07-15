package view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 * Progetto Juno - Classe immagei carta
 * <p>È il label che rappresenta la carta</p>
 * <p>Estende JLabel</p>
 * @see JLabel
 */
public class ImmagineCarta extends JLabel
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>Contiene il percorso della cartella dove sono contenute tutte le immagini delle carte</p>
	 */
	private String pathCarte = "resources/carte/";
	
	/**
	 * <p>Contiene l'estensione dei file immagine usati per il label</p>
	 */
	private String estensione = ".png";
	
	/**
	 * Costruttore della classe, si occupa di creare il label con immagine della carta
	 * @param descrizione la descrizione della carta (serve per caricare l'immagine)
	 * @param x posizione in pixel dell'immagine sull'asse x
	 * @param y posizione in pixel dell'immagine sull'asse y
	 */
	public ImmagineCarta(String descrizione, int x, int y)
	{
		new JLabel();
        setBounds(x, y, 103, 146);
        ImageIcon iconaRetroCartaPescata = new ImageIcon(pathCarte + descrizione + estensione);
        Image imgCartaPescata = iconaRetroCartaPescata.getImage();
        Image imgScaleCartaPescata= imgCartaPescata.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIconCartaPescata = new ImageIcon(imgScaleCartaPescata);
        setIcon(scaledIconCartaPescata); 
	}
}
