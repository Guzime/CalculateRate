package ru.liga;

import ru.liga.service.HandlerCommandConsole;


public class App {
    public static void main(String[] args) {
        HandlerCommandConsole handlerCommandConsole = new HandlerCommandConsole();
        handlerCommandConsole.commandHandler();
    }

}
