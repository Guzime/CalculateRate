package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RateParser;

import java.util.ArrayList;
import java.util.List;

public class HandlerCommand {
    private final CalcRate calcRate;
    private final RateParser rateParser;

    public HandlerCommand(CalcRate calcRate, RateParser rateParser) {
        this.calcRate = calcRate;
        this.rateParser = rateParser;
    }

    //"rate TRY -date tomorrow -alg mist -output list"
    public List<Rate> parseCommand(String command) {
        List<Rate> resultList = new ArrayList<>();
        Currency currency;
        String[] commandWords = command.split(" ");
        String path;
        if (commandWords.length == 6) {
            if (!commandWords[0].equals("rate")) {
                throwError(commandWords[0]);
            }
            currency = getCurrency(commandWords[1]);
            path = currency.getPath();

            if (commandWords[2].equals("-date")) {
                if (commandWords[3].equals("tomorrow")) {
                    if (commandWords[4].equals("-alg")) {
                        if (commandWords[5].equals("avg")) {
                            return callOneDayRate(path);
                        } else {
                            throwError(commandWords[5]);
                        }
                    } else {
                        throwError(commandWords[4]);
                    }
                } else {
                    throwError(commandWords[3]);
                }
            }
            if (commandWords[2].equals("-period")) {
                if (commandWords[3].equals("week")) {
                    if (commandWords[4].equals("-alg")) {
                        if (commandWords[5].equals("avg")) {
                            return callWeekRate(path);
                        } else {
                            throwError(commandWords[5]);
                        }
                    } else {
                        throwError(commandWords[4]);
                    }
                } else {
                    throwError(commandWords[3]);
                }
            }

            throwError(commandWords[2]);

        } else {
            throw new IllegalStateException("Unexpected count command!");
        }
        return resultList;
    }

    private boolean throwError(String inputString) {
        throw new IllegalStateException("Unexpected value: " + inputString);
    }

    private List<Rate> callWeekRate(String path) {
        return calcRate.weekRate(rateParser.parseRateFromFile(path));
    }

    private List<Rate> callOneDayRate(String path) {
        List<Rate> listRate = new ArrayList<>();
        listRate.add(calcRate.oneDayRate(rateParser.parseRateFromFile(path)));
        return listRate;
    }

    private Currency getCurrency(String commandWord) {
        Currency currency;
        switch (commandWord) {
            case "TRY":
                currency = Currency.TRY;
                break;
            case "EUR":
                currency = Currency.EUR;
                break;
            case "USD":
                currency = Currency.USD;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandWord);
        }
        return currency;
    }

}
