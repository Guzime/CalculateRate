package ru.liga;

import ru.liga.model.Rate;

import java.util.Scanner;

/**
 * Hello world!
 * раз два три
 */
public class App {
    private static final String pathUSD = "src/main/resources/RC_F01_06_2002_T17_06_2022_USD.csv";
    private static final String pathTRY = "src/main/resources/RC_F01_06_2002_T17_06_2022_TRY.csv";
    private static final String pathEUR = "src/main/resources/RC_F01_06_2002_T17_06_2022_EUR.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strInputs;
        System.out.println("Для завершения введите exit");
        do {
            strInputs = scanner.nextLine();
            if (strInputs.equals("rate TRY tomorrow")) {
                displayRate(pathTRY, true);
            }
            if (strInputs.equals("rate TRY week")) {
                displayRate(pathTRY, false);
            }
            if (strInputs.equals("rate USD tomorrow")) {
                displayRate(pathUSD, true);
            }
            if (strInputs.equals("rate USD week")) {
                displayRate(pathUSD, false);
            }
            if (strInputs.equals("rate EUR tomorrow")) {
                displayRate(pathEUR, true);
            }
            if (strInputs.equals("rate EUR week")) {
                displayRate(pathEUR, false);
            }
        } while (!strInputs.equals("exit"));
    }

    public static void displayRate(String path, boolean isDay) {
        Rate calculateRate = new Rate(path);
        if (isDay) {
            System.out.println(calculateRate.oneDayRate());
        } else {
            System.out.println(calculateRate.weekRate());
        }
    }
}
