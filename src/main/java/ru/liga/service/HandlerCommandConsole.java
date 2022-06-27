package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.repository.RateParser;
import ru.liga.util.ConstantsRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandlerCommandConsole {
    private final CalcRate calcRate;
    private final RateParser rateParser = new RateParser();
    private final DisplayConsole displayConsole = new DisplayConsole();

    public HandlerCommandConsole(CalcRate calcRate) {
        this.calcRate = calcRate;
    }

    /**
     * Обработчик команд из консоли
     */
    public void commandHandler() {
        Scanner scanner = new Scanner(System.in);
        String strInputs = "";
        System.out.println("Для завершения введите exit!");
        do {
            try {
                strInputs = parseCommand(scanner);
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } while (!strInputs.equals("exit"));
    }

    /**
     * Парсинг команд построчно
     *
     * @param scanner Сканер, считывающий строку из консоли
     * @return Считанная строка
     */
    private String parseCommand(Scanner scanner) {
        String strInputs;
        strInputs = scanner.nextLine();
        String[] commandWords = strInputs.split(" ");
        String path;
        if (commandWords.length == 3) {
            if (!commandWords[0].equals("rate")) {
                throw new IllegalStateException("Unexpected value: " + commandWords[0]);
            }
            path = getPath(commandWords[1]);
            if (path != null) {
                switch (commandWords[2]) {
                    case "tomorrow":
                        callOneDayRate(path);
                        break;
                    case "week":
                        callWeekRate(path);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + commandWords[2]);
                }
            }
        } else {
            throw new IllegalStateException("Unexpected count command!");
        }
        return strInputs;
    }

    /**
     * Вызов расчета курса на неделю
     *
     * @param path путь до файла
     */
    private void callWeekRate(String path) {
        displayConsole.printRatesToConsole(calcRate.weekRate(rateParser.parseRateFromFile(path)));
    }

    /**
     * Вызов расчета курса на день
     *
     * @param path путь до файла
     */
    private void callOneDayRate(String path) {
        List<Rate> listRate = new ArrayList<>();
        listRate.add(calcRate.oneDayRate(rateParser.parseRateFromFile(path)));
        displayConsole.printRatesToConsole(listRate);
    }

    /**
     * Получение пути до файла из консоли
     *
     * @param commandWord входная строка
     * @return путь до файла
     */
    private String getPath(String commandWord) {
        String path;
        switch (commandWord) {
            case "TRY":
                path = ConstantsRate.PATH_TRY;
                break;
            case "EUR":
                path = ConstantsRate.PATH_EUR;
                break;
            case "USD":
                path = ConstantsRate.PATH_USD;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandWord);
        }
        return path;
    }
}
