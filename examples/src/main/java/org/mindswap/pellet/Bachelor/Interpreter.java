package org.mindswap.pellet.Bachelor;

import java.util.*;

public class Interpreter {

public static void main(String[] args) {

	String lang;
	String country;
	
	if(args.length != 2){
		lang = new String ("en");
		country = new String ("US");
		
	} else {
        lang = new String(args[0]);
        country = new String(args[1]);
    }
	
	Locale currentLocale;
	ResourceBundle messages;
	
	currentLocale = new Locale(lang, country);
	
	messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	System.out.println(messages.getString("hasTuna"));
	System.out.println(messages.getString("tunasalad"));
	
}
}
