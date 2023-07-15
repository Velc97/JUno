package juno;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Progetto Juno - 
 * <p> Utilizza il pattern <b>singleton</b></p>
 * @author Cosmo Vellucci 1760067
 */
public class AudioPlayer 
{
	 /**
	  *  <p>
	  *  	Enumerazione per i file audio <br/>
	  *  	Ogni enumerazione è una stringa che punta alla destinazione del file audio
	  *  </p>
	  */
	  public enum EnumFileAudio
	  {
		  /**
		  * Audio di riproduzione per il click dei bottoni
		  */
		  buttonClick("resources\\audio\\click beep.wav"), 
		  
		  /**
		  * Audio di riproduzione per la pesca di una carta
		  */
		  cardDraw("resources\\audio\\card draw.wav"),
		  
		  /**
		  * Audio di riproduzione per lo scarto della carta
		  */
		  cardFlip("resources\\audio\\card flip.wav"),
		  
		  /**
		   * Audio di riproduzione per il mescolamento del mazzo
		   */
		  cardShuffle("resources\\audio\\card shuffle.wav"),
		  
		  /**
		   * Audio di riproduzione per la dichiarazione di "UNO!"
		   */
		  UNO("resources\\audio\\UNO.wav"),
		  
		  /**
		   * Audio di riproduzione per indisponibilità sulle funzioni dei bottoni
		   */
		  beep("resources\\audio\\glass beep.wav"),
		  
		  /**
		   * Audio di riproduzione per quando il giocatore dimentica di dichiarare "UNO!"
		   */
		  forgotUNO("resources\\audio\\delusion.wav"),
		  
		  /**
		   * Audio di riproduzione per la fine del round
		   */
		  roundWin("resources\\audio\\applause.wav"),
		  
		  /**
		   * Audio di riproduzione per la fine della partita
		   */
		  trumpets("resources\\audio\\trumpets.wav");
		 
		  private String stringaAudio;
		  EnumFileAudio(String stringaAudio) 
		   	{this.stringaAudio = stringaAudio;}

		  @Override
		  public String toString() 
		    {return stringaAudio;}
	  }
	
	  private static AudioPlayer instance = new AudioPlayer();
	  private static File file;
	  private static Clip clip;
	  private static AudioInputStream audioStream;
	  
	  /**
	   * <p>Ottiene l'istanza della classe</p>
	   * @return se non esiste crea l'istanza della classe, altrimenti la restituisce
	   */
	  public static AudioPlayer getInstance() 
	  {
		  if (instance == null) 
		  	{instance = new AudioPlayer();}
		  return instance;
	  } 
	  
	  
	  private AudioPlayer() {}
	  
	  /**
	   * <p>
	   * 	Riproduce una sola volta un file audio a un certo livello di volume <br/>
	   * 	Nota: i file utilizzati sono tutti in formato .wav
	   * </p>
	   * @param pathFileAudio è il path audio del file, specificato nell'enumerazione
	   * @param volumeImpostato è il file di riproduzione
	   */
	  public static void riproduci(String pathFileAudio, float volumeImpostato) 
	  {
		  file = new File(pathFileAudio);

		  try 
		  {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			setVolumeFileAudio(clip, volumeImpostato);
			clip.start();
		  }
		  catch (UnsupportedAudioFileException | LineUnavailableException | IOException e1) 
		  {
			  e1.printStackTrace();
		  }
	  }
	  
	  /**
	   * <p>Regola il volume di una clip</p>
	   * @param clip è la clip di cui regolare il volume
	   * @param volumeDaImpostare è il volume che va da 0 a 100 impostato nelle opzioni del gioco
	   * @exception IllegalArgumentException viene lanciata nel caso in cui il volume/100 non è compreso o uguale tra 0 e 1.
	   * @see IllegalArgumentException
	   */
	  private static void setVolumeFileAudio(Clip clip, float volumeDaImpostare) 
	  {
		  volumeDaImpostare = volumeDaImpostare/100;
	      if (volumeDaImpostare < 0 || volumeDaImpostare > 1)
	          {throw new IllegalArgumentException("Valore volume da impostare non valido: " + volumeDaImpostare + " (deve essere compreso tra 0 e 1)");}
	      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	      gainControl.setValue(20 * (float) Math.log10(volumeDaImpostare));
	  }
}


