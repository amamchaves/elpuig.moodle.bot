package elpuig.moodle.bot;

import java.util.Locale;
import java.util.ResourceBundle;

public class Missatges {
  static String language = "es";
  static String country = "ES";
  static Locale currentLocale = new Locale(language, country);
  static ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

  public static void seleccionarIdioma(String language, String country) {
    Missatges.language = language;
    Missatges.country = country;

    Missatges.currentLocale = new Locale(language, country);
    Missatges.messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

  }

  public static String getString(String key) {

    return messages.getString(key);
  }

  public static abstract class Idioma {
    public static String loginHelp = "a";
    public static String loginActive = "a";
    public static String loginOk = "Login realitzat amb èxit";
    public static String loginError = "Login incorrecte";
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
    public static String logOutOk = "Logout realitzat amb èxit";
    public static String logOutError = "Logout incorrecte";
    public static String obtenirLlistaEntregues = "Obtens un llistat de les teves entregues";
    public static String triaAsignatura = "Tria de quina assignatura vols veure l'entrega";
    public static String LlistaComandes = "Llista totes les comandes del bot";
    public static String LlistaComandes2 = "Aquesta és la llista de comandes del bot:";
    public static String LlistaEntregas = "Llistat de les entregues";
    public static String ComandesBot = "Llista totes les comandes del bot";
    public static String Veurehoraris = "Tria de qui vols veure horaris";
    public static String login = "Permet entrar l'usuari del Moodle";
    public static String TriaIdioma = "Tria l'idioma";
    public static String LlistatNota = "Llistat de notes";
    public static String BotDeProva = "Aquest és el bot de proves de El Puig";
    public static String ComençaUtilBot= "Amb aquesta comanda comences a utilitzar el bot";
    public static String AturaBot = "Amb aquesta comanda atures el bot";

  }

  public static class Castellano extends Idioma {
    // LoginCommand
    public static String loginHelp = "Introduce el usuario y la contraseña. \nEjemplo: /login usuario contraseña";
    public static String loginActive = "Ya estás loggeado";
    public static String loginOk = "Login realitzado con éxito";
    public static String loginError = "Login incorrecto";
    public static String logOutOk = "Logout realizado con éxito";
    public static String logOutError = "Logout incorrecto";
    public static String logOutHelp = "Introduce el usuario. \nEjemplo: /logout usuario";
    public static String obtenirLlistaEntregues = "Obtienes un listado de las tus entregas";
    public static String triaAsignatura = "Elije de que asignatura  uieres ver la entrega ";
    public static String LlistaComandes = "Lista todos los comandos del bot";
  }
}
