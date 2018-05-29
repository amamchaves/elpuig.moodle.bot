package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
import elpuig.moodle.bot.model.Usuario;
import elpuig.moodle.bot.utils.Database;
import elpuig.moodle.bot.utils.MoodleAPI;
import elpuig.moodle.bot.utils.RAMDB;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class LoginCommand extends BotCommand {

    public static final String LOGTAG = "LOGINCOMMAND";
    public static final Usuario usuari = new Usuario();

    public LoginCommand() {
        super("login", "Permet entrar l'usuari del Moodle");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        String token = "";

        if (arguments == null || arguments.length == 0) {
            messageBuilder.append(Missatges.Idioma.loginHelp);

        } else if (arguments.length >= 2) {

            // emmagatzema l'usuari
            System.out.println(user.getUserName() + " " + user.getId());

            int result = MoodleAPI.login(user.getId(), arguments[0], arguments[1]);

            if (result == 1) {
                messageBuilder.append(Missatges.Idioma.loginOk);

            } else {
                messageBuilder.append(Missatges.Idioma.loginError);
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