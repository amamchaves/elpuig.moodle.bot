package elpuig.moodle.bot;

import java.util.Locale;
import java.util.ResourceBundle;

public class Missatges {
  static String language = "ca";
  static String country = "CA";
  static Locale currentLocale = new Locale(language, country);
  static ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

  public static void seleccionarIdioma(String language, String country){
    Missatges.language = language;
    Missatges.country = country;

    Missatges.currentLocale = new Locale(language, country);
    Missatges.messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

  }

  public static String getString(String key){
    return messages.getString(key);
  }


  public static abstract class Idioma {
    public static String loginHelp = "Introdueix el teu usuari i contrasenya. \nExemple: /login usuari contrasenya";
    public static String logoutHelp = "Introdueix l'usuari. \nExemple: /logout usuari";
    public static String loginActive = "a";
    public static String loginOk = "Login realitzat amb èxit";
    public static String logoutOk = "Logout realitzat amb èxit";
    public static String loginError = "Login incorrecte";
    public static String logoutError = "Logout incorrecte";
    public static String obtenirLlistaEntregues = "Obtens un llistat de les teves entregues";
    public static String triaAsignatura = "Tria de quina assignatura vols veure l'entrega";
    public static String LlistaComandes = "Llista totes les comandes del bot";
    public static String LlistaComandes2 = "Aquesta és la llista de comandes del bot:";
  }



    public static class Catala extends Idioma {
    // LoginCommand
    public static String loginHelp = "Introdueix el teu usuari i contrasenya. \nExemple: /login usuari contrasenya";
    public static String loginActive = "Ja estàs loggeat";
    public static String loginOk = "Login realitzat amb èxit";
    public static String loginError = "Login incorrecte";
    public static String obtenirLlistaEntregues = "Obtens un llistat de les teves entregues";
    public static String triaAsignatura = "Tria de quina assignatura vols veure l'entrega";
    public static String LlistaComandes = "Llista totes les comandes del bot";
    public static String LlistaComandes2 = "Aquesta és la llista de comandes del bot:";
  }

  public static class Castellano extends Idioma {
    // LoginCommand
    public static String loginHelp = "Introdueix el teu usuari i contrasenya. \nExemple: /login usuari contrasenya";
   public static String loginActive = "Ja estàs loggeat";
    public static String loginOk = "Login realitzat amb èxit";
    public static String loginError = "Login incorrecte";
    public static String obtenirLlistaEntregues = "Obtens un llistat de les teves entregues";
    public static String triaAsignatura = "Tria de quina assignatura vols veure l'entrega ";
    public static String LlistaComandes = "Llista totes les comandes del bot";
  }
}