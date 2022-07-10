package ru.liga.service;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.WordCommand;
import ru.liga.util.ConstantsRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ParserCommands {

    public List<Currency> parseCurrency(String[] command) {
        String[] strCurrency = command[0].split(" ")[1].split(",");
        List<Currency> currencyList = new ArrayList<>();
        for (String str : strCurrency) {
            currencyList.add(Currency.valueOf(str));
        }
        return currencyList;
    }

    public WordCommand parseOutputParams(String[] command) {
        WordCommand output;
        if (command.length == 3) {
            output = WordCommand.LIST;
        } else {
            String[] temp = command[3].split(" ");
            output = WordCommand.valueOf(temp[1].toUpperCase());
        }
        return output;
    }

    public Algorithm parseAlgorithmParams(String[] command) {
        return Algorithm.valueOf(command[2].split(" ")[1].toUpperCase());
    }

    public WordCommand parseDateFormat(String[] command) {
        return WordCommand.valueOf(command[1].split(" ")[0].toUpperCase());
    }


    public LocalDate parseDateParams(String[] command, WordCommand dateFormat, LocalDate startDate) {
        String[] temp = command[1].split(" ");
        LocalDate toDate = LocalDate.now();

        if (dateFormat == WordCommand.DATE) {
            if (temp[1].equals(WordCommand.TOMORROW.name().toLowerCase())) {
                toDate = startDate.plusDays(1);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.IN_PATTERN_DT);
                toDate = LocalDate.parse(temp[1], formatter);
            }
        } else if (dateFormat == WordCommand.PERIOD) {
            if (temp[1].equals(WordCommand.WEEK.name().toLowerCase())) {
                toDate = startDate.plusWeeks(1);
            } else if (temp[1].equals(WordCommand.MONTH.name().toLowerCase())) {
                toDate = startDate.plusMonths(1);
            }
        }
        return toDate;
    }
}
