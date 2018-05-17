package elpuig.moodle.bot.utils;

import elpuig.moodle.bot.model.Course;
import elpuig.moodle.bot.model.Entrega;
import elpuig.moodle.bot.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoodleAPI {

    static String moodleUrl = "http://192.168.22.138/moodle/";

    public static int login(int telegramId, String username, String password){

        String response = HttpUtils.get(moodleUrl + "login/token.php?username=" + username + "&password=" + password + "&service=moodle_mobile_app");

        String token = new JSONObject(response).getString("token");

        response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_user_get_users_by_field&wstoken="+token+"&field=username&values[0]="+username+"&moodlewsrestformat=json");

        JSONObject jsonObject = new JSONArray(response).getJSONObject(0);

        String email = jsonObject.getString("email");
        String id = String.valueOf(jsonObject.getInt("id"));

        Database.get().insertUsuario(telegramId, username, token, email, id);

        return 1;
    }

//    public static String[] getCourses(String telegramUsername, String token){
//
//        String response = HttpUtils.get(moodleUrl + "webservice/rest/server.php?wsfunction=core_course_get_courses&wstoken="+token+"&moodlewsrestformat=json");
//
//        JSONObject cursos = new JSONArray(response).getJSONObject(0);
//        String id_curs = cursos.getString("id");
//        String nom_curs = cursos.getString("fullname");
//
//
//        return ;
//    }

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
            entrega.entreguesNom = entregaJSON.getString("name");
            entrega.entreguesEstat = entregaJSON.getInt("completionsubmit");
            entrega.duedate = entregaJSON.getInt("duedate");
            entrega.grade = entregaJSON.getInt("grade");
            entregues.add(entrega);

        });

        return entregues;
    }

}
