package lv.bootcamp.shelter.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 1: Pure logic tests
 *
 * Practice:
 * - Arrange-Act-Assert pattern
 * - Good test naming
 * - assertEquals for return values
 * - assertThrows for invalid input
 *
 * Instructions:
 * Write tests for AgeCalculator. Each TODO describes one test to write.
 * Remove the TODO comments as you implement each test.
 */
@DisplayName("AgeCalculator")
class AgeCalculatorTest {

    private AgeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new AgeCalculator();
    }

    // --- toMonths() ---

    @Test
    @DisplayName("toMonths: 0 years returns 0 months")
    void shouldReturnZeroMonthsForZeroYears() {

        int result = calculator.toMonths(0);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("toMonths: positive years returns correct months")
    void shouldConvertPositiveYearsToMonths() {

        int result = calculator.toMonths(3);
        assertEquals(36, result);
    }

    @Test
    @DisplayName("toMonths: negative years throws IllegalArgumentException")
    void shouldThrowForNegativeYears() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> calculator.toMonths(-1)
        );
        assertTrue(exception.getMessage().contains("negative"));
    }

    // --- dogToHumanYears() ---

    @Test
    @DisplayName("dogToHumanYears: age 0 returns 0")
    void shouldReturnZeroHumanYearsForPuppy() {
        // TODO: Test that dogToHumanYears(0) returns 0
        assertEquals(0, calculator.dogToHumanYears(0));

    }

    @Test
    @DisplayName("dogToHumanYears: age 1 returns 15")
    void shouldReturnFifteenForOneYearOldDog() {

        assertEquals(15, calculator.dogToHumanYears(1));
    }

    @Test
    @DisplayName("dogToHumanYears: age 2 returns 24")
    void shouldReturnTwentyFourForTwoYearOldDog() {

        assertEquals(24, calculator.dogToHumanYears(2));
    }

    @Test
    @DisplayName("dogToHumanYears: age 5 returns 39")
    void shouldCalculateCorrectlyForOlderDog() {

        assertEquals(39, calculator.dogToHumanYears(5));
    }

    @Test
    @DisplayName("dogToHumanYears: negative age throws IllegalArgumentException")
    void shouldThrowForNegativeDogAge() {

        assertThrows(IllegalArgumentException.class, () -> calculator.dogToHumanYears(-1));
    }

    // --- isBaby() ---

    @Test
    @DisplayName("isBaby: age 0 returns true")
    void shouldReturnTrueForAgZero() {

        assertTrue(calculator.isBaby(0));
    }

    @Test
    @DisplayName("isBaby: age 1 returns false")
    void shouldReturnFalseForAgeOne() {
        // TODO: Test that isBaby(1) returns false
        assertFalse(calculator.isBaby(1));
    }
}
