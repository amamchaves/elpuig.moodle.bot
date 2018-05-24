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
        super("examens", "Obtens un llistat dels teus examens");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append(Missatges.getString("triaAsignatura")).append("\n");

        Usuario usuario = Database.get().selectUsuarioPorTelegramId(user.getId());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Menus.MenuInlineButtonsAssignaturesDAM("examen", user));

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());
        answer.setReplyMarkup(markup);

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

}
