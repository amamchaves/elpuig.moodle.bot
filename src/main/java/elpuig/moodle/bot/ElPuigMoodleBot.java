package elpuig.moodle.bot;

import elpuig.moodle.bot.commands.*;
import elpuig.moodle.bot.model.Entrega;
import elpuig.moodle.bot.services.Emoji;
import elpuig.moodle.bot.services.Menus;
import elpuig.moodle.bot.services.dataVars;
import elpuig.moodle.bot.utils.MoodleAPI;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.net.URL;
import java.time.Instant;
import java.util.List;

public class ElPuigMoodleBot extends TelegramLongPollingCommandBot {

        private static final String LOGTAG = "COMMANDSHANDLER";

        public ElPuigMoodleBot(String botName) {
            super(botName);
            // registrar totes les comandes que té el bot
            register(new StopCommand());
            register(new StartCommand());
            register(new HorarisCommand());
            register(new EntreguesCommand());
            register(new ExamenCommand());
            register(new NotesCommand());
            register(new LoginCommand());
            register(new IdiomaCommand());
            HelpCommand helpCommand = new HelpCommand(this);
            register(helpCommand);

            registerDefaultAction((absSender, message) -> {
                SendMessage commandUnknownMessage = new SendMessage();
                commandUnknownMessage.setChatId(message.getChatId());
                commandUnknownMessage.setText("La comanda '" + message.getText() + "' encara no està implementada o no és d'aquest bot. Aquí tens una ajuda " + Emoji.AMBULANCE);
                try {
                    absSender.sendMessage(commandUnknownMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
                helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
            });

        }

        @Override
        public void processNonCommandUpdate(Update update) {

            if (update.hasMessage()) {
                Message message = update.getMessage();

                if (message.hasText()) {
                    SendMessage echoMessage = new SendMessage();
                    echoMessage.setChatId(message.getChatId());
                    echoMessage.setText("El teu missatge:\n" + message.getText());

                    try {
                        sendMessage(echoMessage);
                    } catch (TelegramApiException e) {
                        BotLogger.error(LOGTAG, e);
                    }
                }
            } else if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String tria = callbackQuery.getData();

                SendMessage answer = new SendMessage();
                SendPhoto answerPhoto = new SendPhoto();
                answer.setChatId(callbackQuery.getMessage().getChatId());
                answerPhoto.setChatId(callbackQuery.getMessage().getChatId());

                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                InlineKeyboardMarkup markup2 = null;


                switch (tria) {
                    case "Professors":
                        markup.setKeyboard(Menus.MenuInlineButtonsHorarisProfes());
                        enviarResposta(callbackQuery, markup, "Tria els horaris de:");
                        break;
                    case "Grups":
                        markup.setKeyboard(Menus.MenuInlineButtonsHorariGrups());
                        enviarResposta(callbackQuery, markup, "Tria el grup:");
                        break;
                    case "<---":
                        markup.setKeyboard(Menus.MenuInlineButtonsHoraris());
                        enviarResposta(callbackQuery, markup, "Tria de qui vols veure horaris:");
                        break;
                    case "Dilluns": enviarResposta(answerPhoto, dataVars.HPDilluns); break;
                    case "Dimarts": enviarResposta(answerPhoto, dataVars.HPDimarts); break;
                    case "Dimecres":    enviarResposta(answerPhoto, dataVars.HPDimecres); break;
                    case "Dijous":  enviarResposta(answerPhoto, dataVars.HPDijous); break;
                    case "Divendres":   enviarResposta(answerPhoto, dataVars.HPDivendres); break;
                    case "SMX1A" :  enviarResposta(answerPhoto,dataVars.HSMX1A); break;
                    case "SMX1B":   enviarResposta(answerPhoto,dataVars.HSMX1B); break;
                    case "SMC1C":   enviarResposta(answerPhoto,dataVars.HSMX1C); break;
                    case "SMX2A":   enviarResposta(answerPhoto,dataVars.HSMX2A); break;
                    case "SMX2B":   enviarResposta(answerPhoto,dataVars.HSMX2B); break;
                    case "SMX2C":   enviarResposta(answerPhoto,dataVars.HSMX2C); break;
                    case "GS1B":    enviarResposta(answerPhoto,dataVars.HGS1B); break;
                    case "GS1A":    enviarResposta(answerPhoto,dataVars.HGS1A); break;
                    case "ASIX2":   enviarResposta(answerPhoto,dataVars.HASIX2A); break;
                    case "DAM2A":   enviarResposta(answerPhoto,dataVars.HDAM2A); break;
                    case "DAM2B":   enviarResposta(answerPhoto,dataVars.HDAM2B); break;
                    default:
                        String[] partsTria = tria.split(":");
                        String tipus = partsTria[0]; // tipus: entregues, examen, notes
                        String courseId = partsTria[1];
                        int uid = Integer.parseInt(partsTria[2]);

                        System.out.println("Id Usuari: " +uid);
                        System.out.println("Tipus: " +tipus);
                        System.out.println("Curs: " + courseId);

                        if (tipus.equals("entregues")){
                            System.out.println("Mostrant entregues...");
                            respondreText(answer, "Aquestes són les entregues: \n" + buildStringEntregues(uid, courseId));
                        }

                        if (tipus.equals("examen")){
                            System.out.println("Mostrant examens...");
                            respondreText(answer, "Aquests són els examens: \n" + buildStringExamens(uid, courseId));
                        }


                }
            }
        }

        @Override
        public String getBotToken() {
            return BotConfig.TOKEN_COMMAND;
        }

        /* Envia una foto d'una URL com a resposta */
        private void enviarResposta(SendPhoto resp, String url) {
            resp.setPhoto(url);
            try {
                sendPhoto(resp);
            } catch (TelegramApiException e2) {
                e2.printStackTrace();
            }
        }

        /* Envia un menu i un text nou com a resposta */
        private void enviarResposta2(SendMessage resp, InlineKeyboardMarkup rkm, String msg) {
            resp.setReplyMarkup(rkm);
            resp.setText(msg);
            try {
                sendMessage(resp);
            } catch (TelegramApiException e) {
                BotLogger.info(LOGTAG, e.getMessage());
            }

        }

        /* Canvia el menu i el text de resposta en el mateix menu del missatge anterior*/
        private void enviarResposta(CallbackQuery resp, InlineKeyboardMarkup rkm, String msg) {
            EditMessageText editMarkup = new EditMessageText();
            editMarkup.setChatId(resp.getMessage().getChatId().toString());
            editMarkup.setInlineMessageId(resp.getInlineMessageId());
            editMarkup.setText(msg);
            editMarkup.enableMarkdown(true);
            editMarkup.setMessageId(resp.getMessage().getMessageId());
            editMarkup.setReplyMarkup(rkm);
            try {
                editMessageText(editMarkup);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        /* Envia una resposta en forma de text */
        private void respondreText(SendMessage resp, String msg){
            resp.setText(msg);
            try {
                resp.setParseMode("HTML");
                sendMessage(resp);
            } catch (TelegramApiException e) {
                BotLogger.info(LOGTAG, e.getMessage());
            }

        }

        void establirIdioma(String idioma){
            System.out.println("ESTOY AQUI");

            if(idioma.equals("catala")){
                Missatges.seleccionarIdioma("ca", "CA");
            } else {
                Missatges.seleccionarIdioma("es", "ES");
            }
        }

        /* Genera una cadena de text amb totes les entregues */
        String buildStringEntregues(int telegramId, String courseId){

            List<Entrega> entregues = MoodleAPI.getEntregues(telegramId, courseId);
            StringBuilder sb = new StringBuilder();

            for(Entrega entrega : entregues){
                System.out.println(entrega.nom + entrega.id);
                if (entrega.nom.contains("Examen") || entrega.nom.contains("Prova"))  {

                }
                else {
                    Instant data = Instant.ofEpochSecond(entrega.duedate);
                    String dataString = data.toString();
                    String[] dataFormat = dataString.split("T");

                    String estat;
                    if (entrega.entregada == true) {
                        estat = "Entregat";
                    } else {
                        estat = "No entregat";
                    }

                    sb.append("\n<b>" + Emoji.CLIPBOARD + " " + entrega.nom + "</b> \n");
                    sb.append("Data d'entrega: " + dataFormat[0] + " \n");
                    sb.append("Estat: " + estat + " \n");
                    sb.append("Nota: " + entrega.grade + " \n");
                }
            }
            return sb.toString();
        }

    /* Genera una cadena de text amb tots els examens */
    String buildStringExamens(int telegramId, String courseId){

        List<Entrega> entregues = MoodleAPI.getEntregues(telegramId, courseId);
        StringBuilder sb = new StringBuilder();

        for(Entrega entrega : entregues) {
            Instant data = Instant.ofEpochSecond(entrega.duedate);
            String dataString = data.toString();
            String[] dataFormat = dataString.split("T");

            String estat;
            if (entrega.entregada == true) {
                estat = "Realitzat";
            } else {
                estat = "No realitzat";
            }

            if (entrega.nom.contains("Examen") || entrega.nom.contains("Prova")) {
                sb.append("\n<b>" + Emoji.HEAVY_EXCLAMATION_MARK_SYMBOL + " " + entrega.nom + "</b> \n");
                sb.append("Data: " + dataFormat[0] + " \n");
                sb.append("Estat: " + estat + " \n");
                sb.append("Nota: " + entrega.grade + " \n");
            }
        }
        return sb.toString();
    }



}