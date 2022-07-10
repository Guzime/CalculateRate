package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Algorithm;
import ru.liga.model.Command;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RateParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerListRateLastYearTest extends TestCase {
    private RateParser rateParser = new RateParser();
    private HandlerListRateLastYear handlerListRateLastYear = new HandlerListRateLastYear();
    private ShowRate showRate = new ShowRate();

    @Test
    public void testToDateRate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 18.06.2022 -alg lastyear";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLastYear.toDateRate(listRate, new Command(commandInput.split("-")));
        System.out.println(resultRate);
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("СБ 18.06.2022 - 72,5048\n");
    }


    @Test
    public void testToDateRateLate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 27.06.2022 -alg lastyear";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLastYear.toDateRate(listRate, new Command(commandInput.split("-")));
        System.out.println(resultRate);
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("ПН 27.06.2022 - 72,1694\n");
    }

    @Test
    public void testPeriodRate() {
    }
}