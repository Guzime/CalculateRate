package ru.liga;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.model.Algorithm;
import ru.liga.model.Command;
import ru.liga.model.Rate;
import ru.liga.model.WordCommand;
import ru.liga.repository.RateParser;
import ru.liga.service.*;
import ru.liga.util.ConstantsBot;
import ru.liga.util.GraphUtils;

import java.util.List;

import static ru.liga.App.logger;


public class HandlerTelegramBot extends TelegramLongPollingBot {
    GraphUtils graphUtils = new GraphUtils();
    ShowRate showRate = new ShowRate();

    public HandlerTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return ConstantsBot.USER_NAME;
    }

    @Override
    public String getBotToken() {
        return ConstantsBot.TOREN_NAME;
    }

    /**
     * Обработка входящих сообщений
     *
     * @param update
     */
    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            ValidateCommands validateCommands = new ValidateCommands(message.getText());
            try {
                validateCommands.validate();
            } catch (IllegalStateException e) {
                logger.error(e.getMessage());
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("ERROR: \n\n" + e.getMessage())
                                .build());
                return;
            }
            Command command = new Command(message.getText().split("-"));

            HandlerCommand handlerCommand = new HandlerCommand(
                    getHandlerListRate(command.getAlgorithm()),
                    new RateParser(),
                    command);

            List<List<Rate>> listRate = handlerCommand.call();
            sendResultMessage(message, command, listRate);
        }

    }

    /**
     * Отсылка ответа
     *
     * @param message  Входящее сообщение
     * @param command  Спарсеная команда
     * @param listRate Результат
     * @throws org.telegram.telegrambots.meta.exceptions.TelegramApiException
     */
    private void sendResultMessage(Message message, Command command, List<List<Rate>> listRate) throws org.telegram.telegrambots.meta.exceptions.TelegramApiException {
        graphUtils.setData(listRate, command.getCurrencyList());
        InputFile inputFile = new InputFile(graphUtils.getCurrencyRatesAsGraph());
        if (message.hasText()) {
            logger.info("Обрабатываю сообщение - " + message.getText());
            if (command.getOutput().equals(WordCommand.LIST)) {
                execute(
                        SendMessage.builder()
                                .chatId(message.getChatId().toString())
                                .text("Result: \n\n" + showRate.convertListRatesToString(listRate))
                                .build());
            }
            if (command.getOutput().equals(WordCommand.GRAPH)) {
                execute(
                        SendPhoto.builder()
                                .chatId(message.getChatId().toString())
                                .photo(inputFile)
                                .build());
            }
        }
    }

    /**
     * Инициализация нужного алгоритма
     *
     * @param algorithm
     * @return
     */
    private HandlerListRate getHandlerListRate(Algorithm algorithm) {
        switch (algorithm) {
            case MIST:
                return new HandlerListRateMist();
            case AVG:
                return new HandlerListRateAVG();
            case LASTYEAR:
                return new HandlerListRateLastYear();
            case LINREG:
                return new HandlerListRateLinearRegression();
        }
        return new HandlerListRateAVG();
    }
}
