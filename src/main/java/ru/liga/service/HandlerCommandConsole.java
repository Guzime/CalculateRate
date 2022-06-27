package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.util.ConstantsRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandlerCommandConsole {
    private final CalcRate calcRate;


    public HandlerCommandConsole(CalcRate calcRate) {
        this.calcRate = calcRate;
    }

    /**
     * Обработчик команд из консоли
     */
    public void commandHandler() {
        Scanner scanner = new Scanner(System.in);
        String strInputs;
        System.out.println("Для завершения введите exit!");
        do {
            strInputs = scanner.nextLine();
            String[] commandWords = strInputs.split(" ");
            String path;
            if (commandWords.length == 3 && commandWords[0].equals("rate")) {
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
                            System.out.println("Unexpected value: " + commandWords[2]);
                    }
                }
            } else {
                System.out.println("Unexpected count command!");
            }
        } while (!strInputs.equals("exit"));
    }

    /**
     * Вызов расчета курса на неделю
     *
     * @param path
     */
    private void callWeekRate(String path) {
        ((HandlerListRateAVG) calcRate).printRatesToConsole(calcRate.weekRate(((HandlerListRateAVG) calcRate).parseRateFromFile(path)));
    }

    /**
     * Вызов расчета курса на день
     *
     * @param path
     */
    private void callOneDayRate(String path) {
        List<Rate> listRate = new ArrayList<>();
        listRate.add(calcRate.oneDayRate(((HandlerListRateAVG) calcRate).parseRateFromFile(path)));
        ((HandlerListRateAVG) calcRate).printRatesToConsole(listRate);
    }

    /**
     * Получение пути до файла из консоли
     *
     * @param commandWord входная строка
     * @return путь до файла
     */
    private String getPath(String commandWord) {
        String path = null;
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
                System.out.println("Unexpected value: " + commandWord);
        }
        return path;
    }
}
