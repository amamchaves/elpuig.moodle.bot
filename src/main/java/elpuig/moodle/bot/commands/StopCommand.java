package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.services.Menus;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class StopCommand extends BotCommand {
    private static final String LOGTAG = "STOPCOMMAND";


    public StopCommand() {
        super("stop", "Amb aquesta comanda atures el bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        //DatabaseManager dbManager = DatabaseManager.getInstance();

        //if (dbManager.getUserStateForCommandsBot(user.getId())) {
          //  dbManager.setUserStateForCommandsBot(user.getId(), false);
            String userName = user.getFirstName() + " " + user.getLastName();

            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Adéu " + userName + "\n");

            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        //}
    }

}