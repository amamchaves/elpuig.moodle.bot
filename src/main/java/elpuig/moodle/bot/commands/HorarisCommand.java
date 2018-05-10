package elpuig.moodle.bot.commands;

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

public class HorarisCommand extends BotCommand {
    private static final String LOGTAG = "HORARISCOMMAND";


    public HorarisCommand() {
        super("horaris", "Obtens els horaris de grups i professors");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Tria de qui vols veure horaris ").append("\n");

        Usuario usuario = Database.get().selectUsuarioPorTelegramName(user.getUserName());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Menus.MenuInlineButtonsHoraris());

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