package controllers;

import play.mvc.*;
import play.libs.*;
import play.cache.*;
import play.data.validation.*;
import java.io.*;
import java.util.*;
import models.*;


public class Application extends Controller {

    public static void index() {
		List<Helt> helter = Helt.find("order by heltepoeng desc").fetch();
		String randomId = Codec.UUID();
	    render(helter, randomId);
    }

	public static void opprett(String fulltNavn, String twitterBrukernavn, File photo, String code, String randomId, String epostAdresseMottaker) {
		validation.required(fulltNavn).message("Du må oppgi navnet til helten. En helt uten et navn?");
		validation.equals(
		        code, Cache.get(randomId)
		    ).message("Feil kode! Vennligst prøv igjen :)");
	
	
		if(epostAdresseMottaker != null && !epostAdresseMottaker.isEmpty() && !validation.hasErrors()){
			validation.email(epostAdresseMottaker).message("Det er noe galt med e-post adressen du har oppgitt.");
			Mail.send("espen.schulstad@gmail.com",epostAdresseMottaker,"Bli en helt i dag!","Les mer på java.no! :D");
		}
	
		if(!validation.hasErrors()){
			Helt helt = new Helt();
			helt.fulltNavn = fulltNavn;
			helt.twitterBrukernavn = twitterBrukernavn;
			helt.bilde.set(photo);
			helt.save();
		} else {
			params.flash(); 
			validation.keep();
		}
		Cache.delete(randomId);		
		index();
	}

	public static void leggTilHeltepoeng(Long id) {
		Helt helt = Helt.findById(id);
		helt.leggTilHeltepoeng();
		helt.save();
		index();
	}
	
	public static void trekkFraHeltepoeng(Long id) {
		Helt helt = Helt.findById(id);
		helt.trekkFraHeltepoeng();
		helt.save();
		index();
	}
	
	public static void captcha(String id) {
	    Images.Captcha captcha = Images.captcha();
	    String code = captcha.getText("#FF0000");
	    Cache.set(id, code, "10mn");
	    renderBinary(captcha);
	}

	
}