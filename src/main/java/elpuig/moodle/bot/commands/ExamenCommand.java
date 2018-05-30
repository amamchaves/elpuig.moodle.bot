package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
import elpuig.moodle.bot.model.Usuario;
import elpuig.moodle.bot.services.Menus;
import elpuig.moodle.bot.utils.Database;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class ExamenCommand extends BotCommand {
    public static final String LOGTAG = "EXAMENCOMMAND";

    public ExamenCommand() {
        super("examens", Missatges.getString("LlistaExamens"));
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder messageBuilder2 = new StringBuilder();
        SendMessage answer = new SendMessage();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();


        try{

        messageBuilder.append(Missatges.getString("LlistaExamens")).append("\n");

        Usuario usuario = Database.get().selectUsuarioPorTelegramId(user.getId());

        //InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Menus.MenuInlineButtonsAssignaturesDAM("examen", user));

        //SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        answer.setReplyMarkup(markup);

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
