package lv.bootcamp.shelter.task23;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import lv.bootcamp.shelter.model.Animal;
import java.time.LocalDate;

/**
 * Tasks 2 & 3: Parameterized tests and exception tests
 *
 * Practice:
 * - @ParameterizedTest with @CsvSource
 * - @ValueSource and @NullAndEmptySource
 * - assertThrows with message checks
 * - AssertJ assertThatThrownBy
 *
 * Instructions:
 * Write tests for AnimalValidator. Each TODO describes one test to write.
 */
@DisplayName("AnimalValidator")
class AnimalValidatorTest {

    private AnimalValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AnimalValidator();
    }

    // ==================== Task 2: Parameterized tests ====================

    @Nested
    @DisplayName("validateName")
    class ValidateName {

        @ParameterizedTest
        @ValueSource(strings = {"Buddy", "Luna", "Mr. Whiskers", "X"})
        @DisplayName("accepts valid names")
        void shouldAcceptValidNames(String name) {

            assertDoesNotThrow(() -> validator.validateName(name));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("rejects blank or null names")
        void shouldRejectBlankNames(String name) {

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class, () -> validator.validateName(name));
            assertTrue(exception.getMessage().contains("must not be blank"));
        }

        @Test
        @DisplayName("rejects name longer than 100 characters")
        void shouldRejectOverlyLongName() {

            String longString = "z".repeat(101);

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class, () -> validator.validateName(longString));

            assertTrue(exception.getMessage().contains("100 characters"));
        }
    }

    @Nested
    @DisplayName("validateAge")
    class ValidateAge {

        @ParameterizedTest
        @CsvSource({
                "0",
                "1",
                "10",
                "50"
        })
        @DisplayName("accepts valid ages")
        void shouldAcceptValidAges(int age) {

            assertDoesNotThrow(() -> validator.validateAge(age));
        }

        @ParameterizedTest
        @CsvSource({
                "-1, must not be negative",
                "-100, must not be negative",
                "51, seems unrealistic",
                "999, seems unrealistic"
        })
        @DisplayName("rejects invalid ages with correct message")
        void shouldRejectInvalidAges(int age, String expectedMessagePart) {

            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class, () -> validator.validateAge(age)
            );

            assertTrue(exception.getMessage().contains(expectedMessagePart));
        }
    }

    // ==================== Task 3: Exception tests ====================

    @Nested
    @DisplayName("validate (full animal)")
    class ValidateFullAnimal {

        @Test
        @DisplayName("throws NullPointerException for null animal")
        void shouldThrowForNullAnimal() {

            NullPointerException exception = assertThrows(
                    NullPointerException.class, () -> validator.validate(null)
            );

            assertTrue(exception.getMessage().contains(" must not be null"));
        }

        @Test
        @DisplayName("throws for animal with blank name")
        void shouldThrowForBlankName() {

            Animal animal = new Animal("", "Cat", 5, true, LocalDate.now());

            assertThrows(IllegalArgumentException.class, () -> validator.validate(animal));
        }

        @Test
        @DisplayName("throws for animal with blank species")
        void shouldThrowForBlankSpecies() {

            Animal animal = new Animal("Yulaf", "", 7, true, LocalDate.now());

            assertThrows(IllegalArgumentException.class, () -> validator.validate(animal));

        }

        @Test
        @DisplayName("throws for animal with negative age")
        void shouldThrowForNegativeAge() {
            Animal animal = new Animal("Luna", "Dog", -1, true, LocalDate.now());

            assertThrows(IllegalArgumentException.class, () -> validator.validate(animal));
        }

        @Test
        @DisplayName("does not throw for fully valid animal")
        void shouldPassForValidAnimal() {
            Animal animal = new Animal("Karam", "Cat", 6, true, LocalDate.now());

            assertDoesNotThrow(() -> validator.validate(animal));
        }
    }
}
