package elpuig.moodle.bot.commands;

import elpuig.moodle.bot.Missatges;
import elpuig.moodle.bot.utils.HttpUtils;
import elpuig.moodle.bot.utils.MoodleAPI;
import elpuig.moodle.bot.utils.RAMDB;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

public class LoginCommand extends BotCommand {

    public static final String LOGTAG = "LOGINCOMMAND";

    public LoginCommand() {
        super("login", "Permet entrar l'usuari del Moodle");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        StringBuilder messageBuilder = new StringBuilder();

        String token = "";

        // busca el token de l'usuari
        if(RAMDB.userTokens.containsKey(user.getUserName())){
            token = RAMDB.userTokens.get(user.getUserName());
            messageBuilder.append(Missatges.loginActive);

        } else {

            if(arguments == null || arguments.length == 0) {
                messageBuilder.append(Missatges.loginHelp);

            }else if(arguments.length >= 2) {

                // emmagatzema l'usuari
                int result = MoodleAPI.login(user.getUserName(), arguments[0], arguments[1]);

                if(result == 1){
                    messageBuilder.append(Missatges.loginOk);
                }else{
                    messageBuilder.append(Missatges.loginError);
                }

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
