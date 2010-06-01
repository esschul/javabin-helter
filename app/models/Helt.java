package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

/**
 * Representerer en javaBin-helt.
 */
@Entity
public class Helt extends Model {
	
	/**
	 * Heltens fulle navn.
	 */
	@Required
	public String fulltNavn;
		
	/**
	 * Heltens twitterbruker. Skal brukes for å hente ut feeds.
	 */
	public String twitterBrukernavn;
		
	/**
	 * Bildet som har blitt lastet opp.
	 */
	public FileAttachment bilde = new FileAttachment();
	
	public Integer heltepoeng = 0;
	
	public void leggTilHeltepoeng(){
		this.heltepoeng = heltepoeng + 1;
	}
	
	public void trekkFraHeltepoeng(){
		this.heltepoeng = heltepoeng - 1;
	}
		
}

