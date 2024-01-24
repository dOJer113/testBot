package org.testbot.testbot.setvice;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.testbot.testbot.config.BotConfig;

@Component
public class TelegramBot extends TelegramLongPollingBot {
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
                default:
                    sendMessage(update.getMessage().getChatId(),"Дурак");
            }

        }

    }
    private void startCommanfReceived(long chatId,String name){
        String answer = "Hi," + name;
        sendMessage(chatId,answer);


    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);

        } catch (TelegramApiException e){

        }
    }



}
