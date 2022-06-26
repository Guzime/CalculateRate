package ru.liga;

import ru.liga.service.HandlerListRate;
import ru.liga.util.ConstantsRate;

import static ru.liga.util.CommandConsole.commandHandler;

public class App {
    public static void main(String[] args) {
        HandlerListRate handlerListRate = new HandlerListRate(ConstantsRate.PATH_USD);
        handlerListRate.printRatesToConsole();
        System.out.println(handlerListRate.oneDayRate());

        commandHandler();
    }

}
