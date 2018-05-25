package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
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

public class LogOutCommand extends BotCommand {

    public static final String LOGTAG = "LOGINCOMMAND";

    public LogOutCommand() {
        super("logout", "Permet sortir l'usuari del Moodle");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();



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