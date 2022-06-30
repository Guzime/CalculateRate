package ru.liga;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.URI;
import java.util.logging.Logger;

public class TestBot extends TelegramLongPollingBot {
    final static Logger logger = Logger.getLogger(String.valueOf(TestBot.class));

    public TestBot(DefaultBotOptions options) {
        super(options);
    }

    @SneakyThrows
    public static void main(String[] args) {
        URI url = ClassLoader.getSystemResource("log.out").toURI();
        logger.info("Запустился бот");
        TestBot bot = new TestBot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public String getBotUsername() {
        return "@CalculateRateBot";
    }

    @Override
    public String getBotToken() {
        return "5402010749:AAH8xMjcFbRH0qw6Qso6XCUurB-ST93Ngag";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                logger.info("Отправили сообщение - " + message.getText());
                execute(
                        SendMessage.builder()
                                .chatId("231852649")
                                .text("You sent: \n\n" + message.getText())
                                .build());
            }
        }
    }
}
