package ru.liga.model;

import java.time.LocalDate;
import java.util.List;

//"rate TRY -date tomorrow -alg mist -output list"
public class Command {
    private List<Currency> currencyList;
    private LocalDate date;
}
