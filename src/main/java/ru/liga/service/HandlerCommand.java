package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.model.WordCommand;
import ru.liga.repository.RateParser;

import java.util.ArrayList;
import java.util.List;

public class HandlerCommand {
    private final HandlerListRate handlerListRate;
    private final RateParser rateParser;
    private final Command command;

    public HandlerCommand(HandlerListRate handlerListRate, RateParser rateParser, Command command) {
        this.handlerListRate = handlerListRate;
        this.rateParser = rateParser;
        this.command = command;
    }

    public List<List<Rate>> call() {
        List<List<Rate>> resultRate = new ArrayList<>();
        for (Currency currency : command.getCurrencyList()) {
            if (!command.getDateFormat().equals(WordCommand.DATE)) {
                resultRate.add(handlerListRate.periodRate(rateParser.parseRateFromFile(currency.getPath(), command.getAlgorithm().getCountReadRates()), command));
            } else {
                resultRate.add(handlerListRate.toDateRate(rateParser.parseRateFromFile(currency.getPath(), command.getAlgorithm().getCountReadRates()), command));
            }
        }
        return resultRate;
    }
}
