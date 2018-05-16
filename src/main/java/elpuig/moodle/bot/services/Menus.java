package elpuig.moodle.bot.services;

import elpuig.moodle.bot.model.Course;
import elpuig.moodle.bot.utils.MoodleAPI;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class Menus {

    /* Crea el menu de buttons per escollir l'horaris de professor o de grups*/
    public static List<KeyboardRow> MenuHoraris() {

        List<KeyboardRow> lkbHoraris = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("Grups");
        row.add("Professors");
        lkbHoraris.add(row);
        return lkbHoraris;
    }

    public static List<List<InlineKeyboardButton>> MenuInlineButtonsHoraris() {
        List<List<InlineKeyboardButton>> lkbHoraris2 = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(new InlineKeyboardButton()
                .setText("Grups")
                .setCallbackData("Grups"));
        row.add(new InlineKeyboardButton()
                .setText("Professors")
                .setCallbackData("Professors"));
        lkbHoraris2.add(row);

        return lkbHoraris2;
    }

    /* Crea el menu de buttons per escollir horaris d'un dia de la setmana dels professors */
    public static List<List<InlineKeyboardButton>> MenuInlineButtonsHorarisProfes() {
        List<List<InlineKeyboardButton>> lkbProfes = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();


        row.add(new InlineKeyboardButton()
                .setText("Dilluns")
                .setCallbackData("Dilluns"));
        row.add(new InlineKeyboardButton()
                .setText("Dimarts")
                .setCallbackData("Dimarts"));
        row.add(new InlineKeyboardButton()
                .setText("Dimecres")
                .setCallbackData("Dimecres"));
        row2.add(new InlineKeyboardButton()
                .setText("Dijous")
                .setCallbackData("Dijous"));
        row2.add(new InlineKeyboardButton()
                .setText("Divendres")
                .setCallbackData("Divendres"));
        row2.add(new InlineKeyboardButton()
                .setText("<---")
                .setCallbackData("<---"));
        lkbProfes.add(row);
        lkbProfes.add(row2);

        return lkbProfes;
    }

    /* Crea el menu de buttons per escollir horaris dels grups */
    public static List<List<InlineKeyboardButton>> MenuInlineButtonsHorariGrups() {
        List<List<InlineKeyboardButton>> lkbGrups = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();

        row.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX1A.toString())
                .setCallbackData(dataVars.GRUPS.SMX1A.toString()));
        row.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX1B.toString())
                .setCallbackData(dataVars.GRUPS.SMX1B.toString()));
        row.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX1C.toString())
                .setCallbackData(dataVars.GRUPS.SMX1C.toString()));
        row2.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX2A.toString())
                .setCallbackData(dataVars.GRUPS.SMX1A.toString()));
        row2.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX2B.toString())
                .setCallbackData(dataVars.GRUPS.SMX1B.toString()));
        row2.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.SMX2C.toString())
                .setCallbackData(dataVars.GRUPS.SMX1C.toString()));
        row3.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.GS1A.toString())
                .setCallbackData(dataVars.GRUPS.GS1A.toString()));
        row3.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.GS1B.toString())
                .setCallbackData(dataVars.GRUPS.GS1B.toString()));
        row4.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.DAM2A.toString())
                .setCallbackData(dataVars.GRUPS.DAM2A.toString()));
        row4.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.DAM2B.toString())
                .setCallbackData(dataVars.GRUPS.DAM2B.toString()));
        row4.add(new InlineKeyboardButton()
                .setText(dataVars.GRUPS.ASIX2.toString())
                .setCallbackData(dataVars.GRUPS.ASIX2.toString()));
        row3.add(new InlineKeyboardButton()
                .setText("<---")
                .setCallbackData("<---"));
        lkbGrups.add(row);
        lkbGrups.add(row2);
        lkbGrups.add(row4);
        lkbGrups.add(row3);

        return lkbGrups;

    }

    /* Crea el menu de buttons per escollir asignatures de DAM */
    public static List<List<InlineKeyboardButton>> MenuInlineButtonsAssignaturesDAM(String tipo, User user) {
        List<List<InlineKeyboardButton>> lkb = new ArrayList<>();

        List<Course> cursos = MoodleAPI.getCourses(user.getId());

        for (Course course : cursos) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton()
                    .setText(course.shortname)
                    .setCallbackData(tipo + ":" + course.id + ":" + user.getId()));
            lkb.add(row);
        }
//
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        List<InlineKeyboardButton> row2 = new ArrayList<>();
//        List<InlineKeyboardButton> row3 = new ArrayList<>();
//
//        row.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP03.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP03.toString()));
//        row.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP05.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP05.toString()));
//        row.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP06.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP06.toString()));
//        row2.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP07.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP07.toString()));
//        row2.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP08.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP08.toString()));
//        row2.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP09.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP09.toString()));
//        row3.add(new InlineKeyboardButton()
//                .setText(dataVars.ASSIGNATURES_DAM.MP13.toString())
//                .setCallbackData(dataVars.ASSIGNATURES_DAM.MP13.toString()));
//        row3.add(new InlineKeyboardButton()
//                .setText("<---")
//                .setCallbackData("<---"));
//        lkbDAM.add(row);
//        lkbDAM.add(row2);
//        lkbDAM.add(row3);

        return lkb;

    }

}