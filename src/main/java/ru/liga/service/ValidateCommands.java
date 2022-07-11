package ru.liga.service;

import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.WordCommand;
import ru.liga.util.ConstantsRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class ValidateCommands {
    private final String[] commands;

    public ValidateCommands(String commands) {
        this.commands = commands.split("-");
    }

    public void validate() {
        if (commands.length < 3 || commands.length > 4) {
            throw new IllegalStateException("Unexpected count command!");
        }
        for (String command : commands) {
            validateCountParams(command);
        }

        String[] temp = commands[0].split(" ");
        validateCurrencyParams(temp);

        temp = commands[1].split(" ");
        validateDateParams(temp);

        temp = commands[2].split(" ");
        validteAlgorithmParams(temp);

        validateOutputParams(temp);
    }

    private void validateOutputParams(String[] temp) {
        if (commands.length > 3) {
            temp = commands[3].split(" ");
            if (!temp[0].equals(WordCommand.OUTPUT.name().toLowerCase())) {
                throw new IllegalStateException("Unexpected value: " + temp[0]);
            } else {
                if (!temp[1].equals(WordCommand.LIST.name().toLowerCase()) && !temp[1].equals(WordCommand.GRAPH.name().toLowerCase())) {
                    throw new IllegalStateException("Unexpected value: " + temp[1]);
                }
            }
        }
    }

    private void validteAlgorithmParams(String[] temp) {
        if (!temp[0].equals(WordCommand.ALG.name().toLowerCase())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        } else {
            if (!temp[1].equals(Algorithm.MIST.name().toLowerCase())
                    && !temp[1].equals(Algorithm.AVG.name().toLowerCase())
                    && !temp[1].equals(Algorithm.LINREG.name().toLowerCase())
                    && !temp[1].equals(Algorithm.LASTYEAR.name().toLowerCase())) {
                throw new IllegalStateException("Unexpected value: " + temp[1]);
            }
        }
    }

    private void validateDateParams(String[] temp) {
        if (!temp[0].equals(WordCommand.DATE.name().toLowerCase()) && !temp[0].equals(WordCommand.PERIOD.name().toLowerCase())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        }

        if (temp[0].equals(WordCommand.DATE.name().toLowerCase())) {
            if (!temp[1].equals(WordCommand.TOMORROW.name().toLowerCase())) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.IN_PATTERN_DT);
                    LocalDate.parse(temp[1], formatter);
                } catch (DateTimeParseException e) {
                    throw new IllegalStateException("Unexpected value: " + temp[1]);
                }
            }
        }

        if (temp[0].equals(WordCommand.PERIOD.name().toLowerCase())) {
            if (!temp[1].equals(WordCommand.WEEK.name().toLowerCase()) && !temp[1].equals(WordCommand.MONTH.name().toLowerCase())) {
                throw new IllegalStateException("Unexpected value: " + temp[1]);
            }
        }
    }

    private void validateCurrencyParams(String[] temp) {
        if (!temp[0].equals(WordCommand.RATE.name().toLowerCase())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        }
        String[] tempListCurrency = temp[1].split(",");
        for (String strCurr : tempListCurrency) {
            if (!Arrays.stream(Currency.values()).anyMatch(cur -> strCurr.equals(cur.name()))) {
                throw new IllegalStateException("Unexpected value: " + strCurr);
            }
        }
    }


    private void validateCountParams(String command) {
        String[] temp = command.split(" ");
        if (temp.length != 2) {
            throw new IllegalStateException("Unexpected value: " + command);
        }
    }
}
