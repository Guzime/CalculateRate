package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.repository.RateParser;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerCommandTest extends TestCase {

    @Test
    public void testParseCommand() {
        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser());
        ShowRate showRate = new ShowRate();
        System.out.println(showRate.convertRatesToString(handlerCommand.parseCommand("rate TRY -date tomorrow -alg avg")));
        assertThat(showRate.convertRatesToString(handlerCommand.parseCommand("rate TRY -date tomorrow -alg avg")))
                .isEqualTo("СБ 18.06.2022 - 34,1940\n");
    }

    @Test
    public void testParseCommandWeek() {
        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser());
        ShowRate showRate = new ShowRate();
        assertThat(showRate.convertRatesToString(handlerCommand.parseCommand("rate TRY -period week -alg avg")))
                .isEqualTo("ПТ 24.06.2022 - 33,5328\n" +
                        "ЧТ 23.06.2022 - 33,4472\n" +
                        "СР 22.06.2022 - 33,4035\n" +
                        "ВТ 21.06.2022 - 33,4236\n" +
                        "ПН 20.06.2022 - 33,5119\n" +
                        "ВС 19.06.2022 - 33,8247\n" +
                        "СБ 18.06.2022 - 34,1940\n");
    }
}