package ru.liga;


import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class App {
    final static Logger logger = LoggerFactory.getLogger(App.class);

    @SneakyThrows
    public static void main(String[] args) {
        logger.info("Запустился бот");
        HandlerTelegramBot bot = new HandlerTelegramBot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);

    }
}
