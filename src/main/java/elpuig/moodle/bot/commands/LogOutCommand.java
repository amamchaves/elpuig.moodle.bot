package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
import elpuig.moodle.bot.model.Usuario;
import elpuig.moodle.bot.utils.MoodleAPI;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class LogOutCommand extends BotCommand {

    public static final String LOGTAG = "LOGOUTCOMMAND";
    public static final Usuario usuari = new Usuario();


    public LogOutCommand() {
        super("logout", "Permet sortir l'usuari del Moodle");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        if (arguments == null || arguments.length == 0) {
            messageBuilder.append(Missatges.Idioma.logoutHelp);

        } else if (arguments.length >= 1) {

            int result = MoodleAPI.logout(user.getId(), arguments[0]);

            if (result == 1) {
                messageBuilder.append(Missatges.Idioma.logoutOk);

            } else {
                messageBuilder.append(Missatges.Idioma.logoutError);
            }
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

}