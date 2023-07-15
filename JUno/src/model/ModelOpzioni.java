package model;

/**
 * Progetto Juno - Classe model opzioni
 * <p>Utilizza il pattern singleton, è coinvolta in un pattern MVC</p>
 * @author Cosmo Vellucci 1760067
 * @see view.ViewOpzioni
 * @see controller.ControllerOpzioni
 */
public class ModelOpzioni
{
	private static ModelOpzioni instance;
	private static int volume;
	private static boolean descrizioneCarte;
	
	/**
	 * <p>Ottiene l'istanza della classe</p>
	 * @return istanza della classe
	 */
	public static ModelOpzioni getInstance() 
	{
		if (instance == null) 
		  {instance = new ModelOpzioni();}
		return instance;
	} 
	
	/**
	 * <p>Regola il volume degli effetti audio</p>
	 * @param volumeDaSettare valore del volume regolare
	 */
	public static void setVolume(int volumeDaSettare)
		{volume = volumeDaSettare;}
	
	/**
	 * <p>Regola il volume degli effetti audio</p>
	 * @param descrizioneCarteDaSettare è il valore booleano da impostare per la visibilità della descrizione delle carte
	 */
	public static void setDescrizioneCarte(boolean descrizioneCarteDaSettare)
		{descrizioneCarte = descrizioneCarteDaSettare;}
	
	/**
	 * <p>Restituisce il valore del volume attualmente configurato</p>
	 * @return il valore del volume attualmente configurato
	 */
	public static int getVolume() 
		{return volume;}
	
	/**
	 * <p>Restituisce il valodere di impostazione della mostra a video </p>
	 * @return il valore di descrizioneCarte attualmente settato
	 */
	public static boolean getDescrizioneCarte() 
		{return descrizioneCarte;}
	
	/**
	 * <p>Ripristina le opzioni ai valori di default</p>
	 */
	public static void ripristina()
	{
		volume = 100;
		descrizioneCarte = true;
	}
}
