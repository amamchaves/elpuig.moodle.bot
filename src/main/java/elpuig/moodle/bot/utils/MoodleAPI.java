package elpuig.moodle.bot.utils;

import elpuig.moodle.bot.model.Course;
import elpuig.moodle.bot.model.Entrega;
import elpuig.moodle.bot.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MoodleAPI {

    static String moodleUrl = "http://192.168.22.138/moodle/";

    public static int login(int telegramId, String username, String password){

        String response = HttpUtils.get(moodleUrl + "login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app");

        try {
            String token = new JSONObject(response).getString("token");

            response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_user_get_users_by_field&wstoken=" + token + "&field=username&values[0]=" + username + "&moodlewsrestformat=json");

            JSONObject jsonObject = new JSONArray(response).getJSONObject(0);

            String email = jsonObject.getString("email");
            String id = String.valueOf(jsonObject.getInt("id"));

            Database.get().insertUsuario(telegramId, username, token, email, id);
        } catch (Exception e){
            return 0;
        }
        return 1;
    }

    public static int logout(int telegramId, String username, String password){

        String response = HttpUtils.get(moodleUrl + "login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app");

        try {
            //Database.get(). (telegramId, username, token, email, id);


        } catch (Exception e){
            return 0;
        }
        return 1;
    }


    public static List<Course> getCourses(int telegramId){

        Usuario usuario = Database.get().selectUsuarioPorTelegramId(telegramId);

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_enrol_get_users_courses&wstoken="+usuario.token+"&userid="+usuario.id+"&moodlewsrestformat=json");


        List<Course> courses = new ArrayList<>();

        new JSONArray(response).forEach(item -> {
            JSONObject courseJSON = (JSONObject) item;

            Course course = new Course();
            course.fullname = courseJSON.getString("fullname");
            course.shortname = courseJSON.getString("shortname");
            course.id = String.valueOf(courseJSON.getInt("id"));
            courses.add(course);

        });

        return courses;

    }

    public static List<Entrega> getEntregues(int telegramId, String courseId){

        Usuario usuario = Database.get().selectUsuarioPorTelegramId(telegramId);

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=mod_assign_get_assignments&wstoken="+usuario.token+"&courseids[0]="+courseId+"&moodlewsrestformat=json");

        List<Entrega> entregues = new ArrayList<>();

        JSONArray entreguesJSON = new JSONObject(response).getJSONArray("courses").getJSONObject(0).getJSONArray("assignments");

        entreguesJSON.forEach(item -> {
            JSONObject entregaJSON = (JSONObject) item;

            Entrega entrega = new Entrega();
            entrega.id = entregaJSON.getInt("id");
            entrega.nom = entregaJSON.getString("name");
            entrega.duedate = entregaJSON.getInt("duedate");
            entregues.add(entrega);

        });

        //Per cada entrega agafem l'id de la entrega i amb l'id busquem el grade
        for (Entrega entrega : entregues) {
            String response2 = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=mod_assign_get_submission_status&wstoken="+usuario.token+"&assignid="+entrega.id +"&moodlewsrestformat=json");

            try {
                new JSONObject(response2).getJSONObject("lastattempt").getJSONObject("submission");

                entrega.entregada = true;
            } catch (Exception e){
                entrega.entregada = false;
            }

            try {
                //Si no hi ha feedback, es que no està calificada
                entrega.grade = new JSONObject(response2).getJSONObject("feedback").getJSONObject("grade").getString("grade");
            } catch (Exception e){
                entrega.grade = "Sense calificar";
            }

        }

        return entregues;
    }

    public static List<Entrega> getNotes(int telegramId, String courseId){

        Usuario usuario = Database.get().selectUsuarioPorTelegramId(telegramId);

        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=mod_assign_get_assignments&wstoken="+usuario.token+"&courseids[0]="+courseId+"&moodlewsrestformat=json");

        List<Entrega> entregues = new ArrayList<>();

        JSONArray entreguesJSON = new JSONObject(response).getJSONArray("courses").getJSONObject(0).getJSONArray("assignments");

        entreguesJSON.forEach(item -> {
            JSONObject entregaJSON = (JSONObject) item;

            Entrega entrega = new Entrega();
            entrega.id = entregaJSON.getInt("id");
            entrega.nom = entregaJSON.getString("name");
            entrega.duedate = entregaJSON.getInt("duedate");
            entregues.add(entrega);

        });

        //Per cada entrega agafem l'id de la entrega i amb l'id busquem el grade
        for (Entrega entrega : entregues) {
            String response2 = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=mod_assign_get_submission_status&wstoken="+usuario.token+"&assignid="+entrega.id +"&moodlewsrestformat=json");

            try {
                entrega.linkNotes = new JSONObject(response2).getJSONObject("feedback").getJSONArray("plugins").getJSONObject(2).getJSONArray("fileareas").getJSONObject(0).getJSONArray("files").getJSONObject(0).getString("fileurl");
                entrega.linkNotes = entrega.linkNotes.replace("pluginfile.php", "pluginfile.php?file=");
                entrega.linkNotes += "&token=" + usuario.token;
                System.out.println(entrega.linkNotes);
            } catch (Exception e){
                entrega.linkNotes = "No disponibles";
            }
        }

        return entregues;
    }
}
