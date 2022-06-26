package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.util.ConstantsRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandlerCommandConsole {

    public void commandHandler() {
        Scanner scanner = new Scanner(System.in);
        String strInputs;
        System.out.println("Для завершения введите exit");
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
                switch (commandWords[2]) {
                    case "tomorrow":
                    case "week":
                        method = commandWords[2];
                }

                if (path != null && method != null) {
                    CalcRate calcRate = new HandlerListRateAVG();
                    calculateRate(calcRate, path, method);
                }
            }
        } while (!strInputs.equals("exit"));
    }

    public void calculateRate(CalcRate calcRate, String path, String method) {
        switch (method) {
            case "week":
                calcRate.printRatesToConsole(calcRate.weekRate(calcRate.getRateFromFile(path)));
                break;
            case "tomorrow":
                List<Rate> listRate = new ArrayList<>();
                listRate.add(calcRate.oneDayRate(calcRate.getRateFromFile(path)));
                calcRate.printRatesToConsole(listRate);
                break;
        }
    }
}
