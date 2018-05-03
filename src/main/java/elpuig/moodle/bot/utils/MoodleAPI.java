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
    public static String[] getCourses(String telegramUsername){

    }

    */

}
