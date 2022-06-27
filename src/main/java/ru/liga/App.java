package ru.liga;

import ru.liga.service.HandlerCommandConsole;
import ru.liga.service.HandlerListRateAVG;


public class App {
    public static void main(String[] args) {
        HandlerCommandConsole handlerCommandConsole = new HandlerCommandConsole(new HandlerListRateAVG());
        handlerCommandConsole.commandHandler();
    }
}
