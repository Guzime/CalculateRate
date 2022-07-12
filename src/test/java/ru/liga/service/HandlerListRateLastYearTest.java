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
    private final RateParser rateParser = new RateParser();
    private final HandlerListRate handlerListRateLastYear = new HandlerListRateLastYear();
    private final ShowRate showRate = new ShowRate();

    @Test
    public void testToDateRate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 18.06.2022 -alg lastyear";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLastYear.toDateRate(listRate, new Command(commandInput));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("СБ 18.06.2022 - 72,5048\n");
    }


    @Test
    public void testToDateRateLate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 27.06.2022 -alg lastyear";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLastYear.toDateRate(listRate, new Command(commandInput));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("ПН 27.06.2022 - 72,1694\n");
    }

    @Test
    public void testToDateRateWeek() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -period week -alg lastyear";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLastYear.periodRate(listRate, new Command(commandInput));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo(
                "СБ 12.03.2022 - 73,4996\n" +
                        "ПТ 11.03.2022 - 74,0393\n" +
                        "ЧТ 10.03.2022 - 74,2640\n" +
                        "СР 09.03.2022 - 74,4275\n" +
                        "ВТ 08.03.2022 - 74,4275\n" +
                        "ПН 07.03.2022 - 74,4275\n" +
                        "ВС 06.03.2022 - 74,4275\n");
    }
}