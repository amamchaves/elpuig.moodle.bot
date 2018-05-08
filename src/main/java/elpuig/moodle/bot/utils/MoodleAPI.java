package elpuig.moodle.bot.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class MoodleAPI {

    static String moodleUrl = "http://192.168.22.138/moodle/";

    public static int login(String telegramUsername, String username, String password){

        String response = HttpUtils.get(moodleUrl + "login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app");

        String token = new JSONObject(response).getString("token");

        response = HttpUtils.get(moodleUrl + "wsfunction=core_user_get_users_by_field&wstoken="+token+"&field=username&values[0]="+username+"&moodlewsrestformat=json");

        JSONObject jsonObject = new JSONArray(response).getJSONObject(0);

        String email = jsonObject.getString("email");

        String id = jsonObject.getString("id");


        Database.get().insertUsuario(username, token, email, id);

        return 1;
    }


    /*
    public static String[] getCourses(String telegramUsername, String token){

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_course_get_courses&wstoken="+token+"&moodlewsrestformat=json");

        JSONObject cursos = new JSONArray(response).getJSONObject(0);
        String id_curs = cursos.getString("id");
        String nom_curs = cursos.getString("fullname");

        // return ;
    }

    public static String[] getAssignatura(String telegramUsername, String token, String id){

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_enrol_get_users_courses&wstoken="+token+"&userid="+id+"&moodlewsrestformat=json");

        JSONObject assignatura = new JSONArray(response).getJSONObject(0);
        String id_assignatura = assignatura.getString("id");
        String nom_assignatura = assignatura.getString("shortname");

        // return ;
    }

    public static String[] getEntregues(String telegramUsername, String token, String id_assignatura){

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=mod_assign_get_assignments&wstoken="+token+"&courseids[0]="+id_assignatura+"&moodlewsrestformat=json");

        JSONObject entregues = new JSONArray(response).getJSONObject(0).getJSONObject("assignments");
        //String id_entrega = entregues.getString("id");
        String nom_entrega = entregues.getString("name");

        // return ;

    }
    */

}
