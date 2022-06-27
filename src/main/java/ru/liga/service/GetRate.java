package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

public interface GetRate {
    List<Rate> parseRateFromFile(String path);
}
