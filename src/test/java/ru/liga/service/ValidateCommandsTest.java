package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.util.ConstantsRate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ValidateCommandsTest extends TestCase {


    @Test
    public void testCountCom() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY -date tomorrow -alg avg -asdsad -asdasd");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testValidCommand() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY -date tomorrow -alg avg");
        validateCommands.validate();
    }

    @Test
    public void testUnValidCommand() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY -date -alg avg");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testUnValidCommandRate() {
        ValidateCommands validateCommands = new ValidateCommands("srate TRY -date tomorrow -alg avg");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testValidCommandCurrency() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -date tomorrow -alg avg");
        validateCommands.validate();
    }

    @Test
    public void testUnValidCommandCurrency() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD,RUB -date tomorrow -alg avg");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testUnValidDate() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -date 22.02.2023 -alg avg");
        String[] str = "rate TRY,USD -date 22.02.2023 -alg avg".split(ConstantsRate.REGEX_COMMAND_DATE);
        for (String s : str) {
            System.out.println(s);
        }
    }

    @Test
    public void testValidDate() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -date 22.02.2023 -alg avg");
        validateCommands.validate();
    }

    @Test
    public void testUnValidPeriod() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -period 2year -alg avg");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testUnValidAlg() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -period week -alg avgs");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testValidOutput() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -period week -alg avg -output graph");
        validateCommands.validate();
    }

    @Test
    public void testUnValidOutput() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY,USD -period week -alg avg -output graphs");
        assertThatThrownBy(() -> validateCommands.validate()).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testValidCurrency() {
        ValidateCommands validateCommands = new ValidateCommands("rate usd -period week -alg avg -output graph");
        validateCommands.validate();
    }

    @Test
    public void testValidMonth() {
        ValidateCommands validateCommands = new ValidateCommands("rate TRY -period month -alg avg");
        validateCommands.validate();
    }

    @Test
    public void testValidTrim() {
        ValidateCommands validateCommands = new ValidateCommands("rate  TRY,usd - period    month -    alg AVG");
        validateCommands.validate();
    }

}