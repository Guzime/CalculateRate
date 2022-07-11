package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Algorithm;
import ru.liga.model.Command;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RateParser;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerListRateMistTest extends TestCase {
    private final RateParser rateParser = new RateParser();
    private final HandlerListRateMist handlerListRateMist = new HandlerListRateMist();

    @Test
    public void testToDateRateTomorrow() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.MIST.getCountReadRates());
        String commandInput = "rate USD -date 06.03.2022 -alg mist";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateMist.toDateRate(listRate, new Command(commandInput.split("-")));
        assertThat(listRate.stream()
                .map(Rate::getRate)
                .collect(Collectors.toList()))
                .contains(resultRate.get(0).getRate());
    }

    @Test
    public void testPeriodRateWeek() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.MIST.getCountReadRates());
        String commandInput = "rate USD -period week -alg mist";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateMist.periodRate(listRate, new Command(commandInput.split("-")));
        System.out.println(resultRate);
        assertThat(listRate.stream()
                .map(Rate::getRate)
                .collect(Collectors.toList()))
                .contains(resultRate.get(2).getRate())
                .contains(resultRate.get(0).getRate())
                .contains(resultRate.get(1).getRate())
                .contains(resultRate.get(5).getRate());
    }
}