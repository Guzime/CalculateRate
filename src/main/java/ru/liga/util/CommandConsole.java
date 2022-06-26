package ru.liga.util;

import ru.liga.model.OldRate;

import java.util.Scanner;

public class CommandConsole {

    public static void commandHandler() {
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
                    case "EUR":
                        path = ConstantsRate.PATH_EUR;
                    case "USD":
                        path = ConstantsRate.PATH_USD;
                }
                switch (commandWords[2]) {
                    case "tomorrow":
                    case "week":
                        method = commandWords[2];
                }

                if (path != null && method != null) {
                    displayRate(path, method);
                }
            }
        } while (!strInputs.equals("exit"));
    }

    public static void displayRate(String path, String method) {
        OldRate calculateRate = new OldRate(path);
        if (method.equals("tomorrow")) {
            System.out.println(calculateRate.oneDayRate());
        } else {
            System.out.println(calculateRate.weekRate());
        }
    }
}
