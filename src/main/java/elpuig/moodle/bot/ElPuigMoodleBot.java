package elpuig.moodle.bot;

import elpuig.moodle.bot.commands.*;

import elpuig.moodle.bot.services.Emoji;
import elpuig.moodle.bot.services.Menus;
import elpuig.moodle.bot.services.dataVars;

import elpuig.moodle.bot.utils.MoodleAPI;
import jdk.nashorn.internal.codegen.CompilerConstants;
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
                //System.out.println(tria);

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
//                    case "SMX1A" :  enviarResposta(answerPhoto,dataVars.HSMX1A); break;
//                    case "SMX1B":   enviarResposta(answerPhoto,dataVars.HSMX1B); break;
//                    case "SMC1C":   enviarResposta(answerPhoto,dataVars.HSMX1C); break;
//                    case "SMX2A":   enviarResposta(answerPhoto,dataVars.HSMX2A); break;
//                    case "SMX2B":   enviarResposta(answerPhoto,dataVars.HSMX2B); break;
//                    case "SMX2C":   enviarResposta(answerPhoto,dataVars.HSMX2C); break;
//                    case "GS1B":    enviarResposta(answerPhoto,dataVars.HGS1B); break;
//                    case "GS1A":    enviarResposta(answerPhoto,dataVars.HGS1A); break;
//                    case "ASIX2":   enviarResposta(answerPhoto,dataVars.HASIX2A); break;
//                    case "DAM2A":   enviarResposta(answerPhoto,dataVars.HDAM2A); break;
//                    case "DAM2B":   enviarResposta(answerPhoto,dataVars.HDAM2B); break;
                    default:
                        String[] partsTria = tria.split(":");
                        //callbackQuery.getId();
                        respondreText(answer, "estas las entregas ");
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

        private void respondreText(SendMessage resp, String msg){
            resp.setText(msg);
            try {
                sendMessage(resp);
            } catch (TelegramApiException e) {
                BotLogger.info(LOGTAG, e.getMessage());
            }

        }
        /*
        String buildStringEntregues(String telegramId, String courseId){
            MoodleAPI.getCourses(telegramId);
            // generar string con todas las asignaturas

        }*/
}