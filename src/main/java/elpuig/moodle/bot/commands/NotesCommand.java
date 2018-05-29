package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
import elpuig.moodle.bot.model.Course;
import elpuig.moodle.bot.model.Entrega;
import elpuig.moodle.bot.model.Usuario;
import elpuig.moodle.bot.services.Emoji;
import elpuig.moodle.bot.services.Menus;
import elpuig.moodle.bot.utils.Database;
import elpuig.moodle.bot.utils.MoodleAPI;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.List;

public class NotesCommand extends BotCommand {
    public static final String LOGTAG = "NOTESCOMMAND";

    public NotesCommand() {
        super("notes", "Obtens un llistat de les notes del curs");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder messageBuilder2 = new StringBuilder();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        SendMessage answer = new SendMessage();

        try{
        Usuario usuario = Database.get().selectUsuarioPorTelegramId(user.getId());

        String tutoriaId = "";
        for(Course course: MoodleAPI.getCourses(user.getId())){
            if(course.fullname.toLowerCase().contains("tutoria")){
                tutoriaId = course.id;
                break;
            }
        }

        for(Entrega entrega : MoodleAPI.getNotes(user.getId(), tutoriaId)){
            if(entrega.nom.contains("Notes") ) {
                messageBuilder.append("\n" + Emoji.HUNDRED_POINTS_SYMBOL + " " + entrega.nom + " \n");
                messageBuilder.append(entrega.linkNotes + " \n\n");
            }
        }



        //SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
        }catch (Exception e){

            messageBuilder2.append(Missatges.getString("loginHelp")).append("\n");

            answer.setChatId(chat.getId().toString());
            answer.setText(messageBuilder2.toString());
            answer.setReplyMarkup(markup);
            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e1) {
                e1.printStackTrace();
            }
        }
    }

}

