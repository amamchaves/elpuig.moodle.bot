package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Usuario;
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

public class NotesCommand extends BotCommand {
    public static final String LOGTAG = "NOTESCOMMAND";

    public NotesCommand() {
        super("notes", "Obtens un llistat de les notes del curs");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Llistat de notes ").append("\n");

        Usuario usuario = Database.get().selectUsuarioPorTelegramName(user

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

