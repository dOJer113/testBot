package org.testbot.testbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.testbot.testbot.setvice.TelegramBot;

@Slf4j //аннотация для логов

@Component
public class BotInitializer {
    @Autowired
    TelegramBot bot;
    @EventListener({ContextRefreshedEvent.class})
    public void init()throws TelegramApiException{
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e){
            log.error("Error: " + e.getMessage()); // Для отображения ошибок нужен метод error,
            // Для отображения каких-то действий пользователя импользуют info

        }
    }


}
