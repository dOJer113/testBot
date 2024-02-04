package org.testbot.testbot.setvice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.testbot.testbot.config.BotConfig;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    public String getBotToken(){
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            switch (message){
                case "/start":
                    startCommanfReceived(update.getMessage().getChatId(),update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(update.getMessage().getChatId(),"Залупа");
            }

        }

    }
    private void startCommanfReceived(long chatId,String name){
        String answer = "Hi," + name;
        sendMessage(chatId,answer);
        log.info("Запущено для пользователя: " + name);


    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);

        } catch (TelegramApiException e){
            log.error("Error: " + e.getMessage());

        }
    }



}
