package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Command;
import ru.liga.repository.RateParser;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerCommandTest extends TestCase {

    private static final ShowRate showRate = new ShowRate();


    @Test
    public void testHandlerTomorrow() {
        String commandInput = "rate try -date tomorrow -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВС 06.03.2022 - 68,8605\n");
    }

    @Test
    public void testHandlerWeek() {
        String commandInput = "rate TRY -period week -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("СБ 12.03.2022 - 71,7079\n" +
                        "ПТ 11.03.2022 - 72,6237\n" +
                        "ЧТ 10.03.2022 - 72,7475\n" +
                        "СР 09.03.2022 - 71,9224\n" +
                        "ВТ 08.03.2022 - 71,4047\n" +
                        "ПН 07.03.2022 - 69,9045\n" +
                        "ВС 06.03.2022 - 68,8605\n");
    }

    @Test
    public void testHandlerAfterTomorrow() {
        String commandInput = "rate TRY -date 20.09.2022 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВТ 20.09.2022 - 71,8573\n");
    }

    @Test
    public void testHandlerAfterVeryLate() {
        String commandInput = "rate TRY -date 20.06.2023 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВТ 20.06.2023 - 71,8573\n");
    }

    @Test
    public void testHandlerMonth() {
        String commandInput = "rate TRY -period month -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВТ 05.04.2022 - 71,8579\n" +
                        "ПН 04.04.2022 - 71,8584\n" +
                        "ВС 03.04.2022 - 71,8577\n" +
                        "СБ 02.04.2022 - 71,8559\n" +
                        "ПТ 01.04.2022 - 71,8550\n" +
                        "ЧТ 31.03.2022 - 71,8564\n" +
                        "СР 30.03.2022 - 71,8595\n" +
                        "ВТ 29.03.2022 - 71,8627\n" +
                        "ПН 28.03.2022 - 71,8619\n" +
                        "ВС 27.03.2022 - 71,8527\n" +
                        "СБ 26.03.2022 - 71,8434\n" +
                        "ПТ 25.03.2022 - 71,8485\n" +
                        "ЧТ 24.03.2022 - 71,8664\n" +
                        "СР 23.03.2022 - 71,8810\n" +
                        "ВТ 22.03.2022 - 71,8848\n" +
                        "ПН 21.03.2022 - 71,8567\n" +
                        "ВС 20.03.2022 - 71,7884\n" +
                        "СБ 19.03.2022 - 71,7783\n" +
                        "ПТ 18.03.2022 - 71,8840\n" +
                        "ЧТ 17.03.2022 - 71,9919\n" +
                        "СР 16.03.2022 - 71,9832\n" +
                        "ВТ 15.03.2022 - 71,9109\n" +
                        "ПН 14.03.2022 - 71,6601\n" +
                        "ВС 13.03.2022 - 71,3102\n" +
                        "СБ 12.03.2022 - 71,7079\n" +
                        "ПТ 11.03.2022 - 72,6237\n" +
                        "ЧТ 10.03.2022 - 72,7475\n" +
                        "СР 09.03.2022 - 71,9224\n" +
                        "ВТ 08.03.2022 - 71,4047\n" +
                        "ПН 07.03.2022 - 69,9045\n" +
                        "ВС 06.03.2022 - 68,8605\n");
    }

    @Test
    public void testSomeCurrencyHandlerWeek() {
        String commandInput = "rate TRY,usd -period week -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("СБ 12.03.2022 - 71,7079\n" +
                        "ПТ 11.03.2022 - 72,6237\n" +
                        "ЧТ 10.03.2022 - 72,7475\n" +
                        "СР 09.03.2022 - 71,9224\n" +
                        "ВТ 08.03.2022 - 71,4047\n" +
                        "ПН 07.03.2022 - 69,9045\n" +
                        "ВС 06.03.2022 - 68,8605\n" +
                        "\n" +
                        "СБ 12.03.2022 - 100,9051\n" +
                        "ПТ 11.03.2022 - 102,2615\n" +
                        "ЧТ 10.03.2022 - 102,3849\n" +
                        "СР 09.03.2022 - 101,0550\n" +
                        "ВТ 08.03.2022 - 100,1180\n" +
                        "ПН 07.03.2022 - 98,0468\n" +
                        "ВС 06.03.2022 - 96,6571\n");
    }

    @Test
    public void testSomeCurrencyHandlerToDay() {
        String commandInput = "rate TRY,USD -date 22.06.2022 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo(
                        "СР 22.06.2022 - 71,8573\n" +
                                "\n" +
                                "СР 22.06.2022 - 101,0413\n");
    }

}