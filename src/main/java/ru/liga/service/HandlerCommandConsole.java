package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.util.ConstantsRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandlerCommandConsole {
    private CalcRate calcRate;

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
            String path = null;
            String method = null;
            if (commandWords.length == 3 && commandWords[0].equals("rate")) {
                switch (commandWords[1]) {
                    case "TRY":
                        path = ConstantsRate.PATH_TRY;
                        break;
                    case "EUR":
                        path = ConstantsRate.PATH_EUR;
                        break;
                    case "USD":
                        path = ConstantsRate.PATH_USD;
                        break;
                }
                if (path != null) {
                    switch (commandWords[2]) {
                        case "tomorrow":
                            List<Rate> listRate = new ArrayList<>();
                            listRate.add(calcRate.oneDayRate(((HandlerListRateAVG) calcRate).parseRateFromFile(path)));
                            ((HandlerListRateAVG) calcRate).printRatesToConsole(listRate);
                            break;
                        case "week":
                            ((HandlerListRateAVG) calcRate).printRatesToConsole(calcRate.weekRate(((HandlerListRateAVG) calcRate).parseRateFromFile(path)));
                            break;
                    }
                }
            }
        } while (!strInputs.equals("exit"));
    }
}
