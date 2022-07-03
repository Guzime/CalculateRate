package ru.liga;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RateParser;
import ru.liga.service.CalcRate;
import ru.liga.service.HandlerListRateAVG;
import ru.liga.service.ShowRate;
import ru.liga.util.GraphUtils;

import java.util.List;


public class TestBot extends TelegramLongPollingBot {
    final static Logger logger = LoggerFactory.getLogger(TestBot.class);

    public TestBot(DefaultBotOptions options) {
        super(options);
    }

    @SneakyThrows
    public static void main(String[] args) {
        logger.info("Запустился бот");
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
        GraphUtils graphUtils = new GraphUtils();
        CalcRate calcRate = new HandlerListRateAVG();
        RateParser rateParser = new RateParser();
        ShowRate showRate = new ShowRate();


        List<Rate> listRate = calcRate.weekRate(rateParser.parseRateFromFile(Currency.USD.getPath()));
        //listRate.add(calcRate.oneDayRate(rateParser.parseRateFromFile(ConstantsRate.PATH_USD)));
        graphUtils.setData(listRate);
        InputFile inputFile = new InputFile(graphUtils.getCurrencyRatesAsGraph());

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                logger.info("Отправили сообщение - " + message.getText());
                execute(
                        SendMessage.builder()
                                .chatId("231852649")
                                .text("You sent: \n\n" + showRate.convertRatesToString(listRate))
                                .build());
                execute(
                        SendPhoto.builder()
                                .chatId("231852649")
                                .photo(inputFile)
                                .build());
            }
        }
    }
}
