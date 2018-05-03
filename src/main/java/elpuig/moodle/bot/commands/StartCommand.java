package elpuig.moodle.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;

import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";

    public StartCommand() {
        super("start", "Amb aquesta comanda comences a utilitzar el bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        // DatabaseManager databseManager = DatabaseManager.getInstance();
        StringBuilder messageBuilder = new StringBuilder();

        String userName = user.getFirstName();// + " " + user.getLastName();

        //if (databseManager.getUserStateForCommandsBot(user.getId())) {
        messageBuilder.append("Benvingut "+userName).append("\n");
        messageBuilder.append("Aquest és el bot de proves de El Puig");
        // } else {
        //     databseManager.setUserStateForCommandsBot(user.getId(), true);
        //     messageBuilder.append("Welcome ").append(userName).append("\n");
        //    messageBuilder.append("this bot will demonstrate you the command feature of the Java TelegramBots API!");
        //}

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