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
    private final ParserCommands parserCommands = new ParserCommands();

    public ValidateCommands(String commands) {
        this.commands = parserCommands.parseCommandTrim(commands);
    }

    /**
     * Валидация команды
     */
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

        validateOutputParams();
    }

    /**
     * Валидация аутпут аргумента
     */
    private void validateOutputParams() {
        if (commands.length > 3) {
            String[] temp = commands[3].split(" ");
            if (!temp[0].toUpperCase().equals(WordCommand.OUTPUT.name())) {
                throw new IllegalStateException("Unexpected value: " + temp[0]);
            } else {
                if (!temp[1].toUpperCase().equals(WordCommand.LIST.name()) && !temp[1].toUpperCase().equals(WordCommand.GRAPH.name())) {
                    throw new IllegalStateException("Unexpected value: " + temp[1]);
                }
            }
        }
    }

    /**
     * Валидация аутпут аргумента
     *
     * @param temp Разбитая команда на массив
     */
    private void validteAlgorithmParams(String[] temp) {
        if (!temp[0].toUpperCase().equals(WordCommand.ALG.name())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        } else {
            if (!temp[1].toUpperCase().equals(Algorithm.MIST.name())
                    && !temp[1].toUpperCase().equals(Algorithm.AVG.name())
                    && !temp[1].toUpperCase().equals(Algorithm.LINREG.name())
                    && !temp[1].toUpperCase().equals(Algorithm.LASTYEAR.name())) {
                throw new IllegalStateException("Unexpected value: " + temp[1]);
            }
        }
    }

    /**
     * Валидация аргумента даты
     *
     * @param temp Разбитая команда на массив
     */
    private void validateDateParams(String[] temp) {
        if (!temp[0].toUpperCase().equals(WordCommand.DATE.name()) && !temp[0].toUpperCase().equals(WordCommand.PERIOD.name())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        }

        if (temp[0].toUpperCase().equals(WordCommand.DATE.name())) {
            if (!temp[1].toUpperCase().equals(WordCommand.TOMORROW.name())) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.IN_PATTERN_DT);
                    LocalDate.parse(temp[1], formatter);
                } catch (DateTimeParseException e) {
                    throw new IllegalStateException("Unexpected value: " + temp[1]);
                }
            }
        }

        if (temp[0].toUpperCase().equals(WordCommand.PERIOD.name())) {
            if (!temp[1].toUpperCase().equals(WordCommand.WEEK.name()) && !temp[1].toUpperCase().equals(WordCommand.MONTH.name())) {
                throw new IllegalStateException("Unexpected value: " + temp[1]);
            }
        }
    }

    /**
     * Валидация аргумента валюты
     *
     * @param temp Разбитая команда на массив
     */
    private void validateCurrencyParams(String[] temp) {
        if (!temp[0].toUpperCase().equals(WordCommand.RATE.name())) {
            throw new IllegalStateException("Unexpected value: " + temp[0]);
        }
        String[] tempListCurrency = temp[1].split(",");
        for (String strCurr : tempListCurrency) {
            if (Arrays.stream(Currency.values()).noneMatch(cur -> strCurr.toUpperCase().equals(cur.name()))) {
                throw new IllegalStateException("Unexpected value: " + strCurr);
            }
        }
    }

    /**
     * Валидация количества аргументов в команде
     *
     * @param command Команда целиком
     */
    private void validateCountParams(String command) {
        String[] temp = command.split(" ");
        if (temp.length != 2) {
            throw new IllegalStateException("Unexpected value: " + command);
        }
    }
}
