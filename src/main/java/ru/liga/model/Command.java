package ru.liga.model;

import ru.liga.repository.RateParser;
import ru.liga.service.ParserCommands;

import java.time.LocalDate;
import java.util.List;

//"rate TRY -date tomorrow -alg mist -output list"
public class Command {
    private final List<Currency> currencyList;
    private final WordCommand dateFormat;
    private final LocalDate startDate;
    private final LocalDate toDate;
    private final Algorithm algorithm;
    private final WordCommand output;

    public Command(List<Currency> currencyList, WordCommand dateFormat, LocalDate startDate, LocalDate toDate, Algorithm algorithm, WordCommand output) {
        this.currencyList = currencyList;
        this.dateFormat = dateFormat;
        this.startDate = startDate;
        this.toDate = toDate;
        this.algorithm = algorithm;
        this.output = output;
    }

    public Command(String command) {
        RateParser rateParser = new RateParser();
        ParserCommands parserCommands = new ParserCommands();
        String[] commands = parserCommands.parseCommandTrim(command);
        currencyList = parserCommands.parseCurrency(commands);
        dateFormat = parserCommands.parseDateFormat(commands);
        algorithm = parserCommands.parseAlgorithmParams(commands);
        output = parserCommands.parseOutputParams(commands);
        startDate = rateParser.getLastDateFromFile(Currency.EUR.getPath());
        toDate = parserCommands.parseDateParams(commands, dateFormat, startDate);
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public WordCommand getDateFormat() {
        return dateFormat;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public WordCommand getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "Command{" +
                "currencyList=" + currencyList +
                ", toDate=" + toDate +
                ", startDate=" + startDate +
                ", dateFormat=" + dateFormat +
                ", algorithm=" + algorithm +
                ", output=" + output +
                '}';
    }
}
