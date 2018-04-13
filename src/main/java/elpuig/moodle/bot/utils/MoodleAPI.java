package elpuig.moodle.bot.utils;

import org.json.JSONObject;

public class MoodleAPI {

    static String moodleUrl = "http://192.168.22.138/moodle/";

    public static int login(String telegramUsername, String username, String password){
        String response = HttpUtils.get(moodleUrl + "login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app");

        String token = new JSONObject(response).get("token").toString();

        response = HttpUtils.get(moodleUrl + "wsfunction=core_user_get_users_by_field&wstoken="+token+"&field=email&values[0]=snavarrod@elpuig.xeill.net&moodlewsrestformat=json");

        RAMDB.userTokens.put(telegramUsername, token);

        return 1;
    }

    //public static String[] getCourses(String telegramUsername){


    //}
}
