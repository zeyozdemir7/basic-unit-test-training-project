package lv.bootcamp.shelter.task4;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

/**
 * Task 4: Collection and sorting tests
 *
 * Practice:
 * - AssertJ list assertions (extracting, containsExactly)
 * - Testing sort order
 * - Testing null/empty input handling
 *
 * Instructions:
 * Write tests for AnimalSorter. Use AssertJ's extracting() and containsExactly()
 * to verify the order of results.
 */
@DisplayName("AnimalSorter")
class AnimalSorterTest {

    private AnimalSorter sorter;

    private Animal buddy;
    private Animal luna;
    private Animal max;
    private Animal bella;

    @BeforeEach
    void setUp() {
        sorter = new AnimalSorter();
        buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        bella = new Animal("Bella", "Cat", 1, true, LocalDate.of(2026, 1, 5));
    }

    // --- sortByAge ---

    @Test
    @DisplayName("sortByAge: returns animals ordered youngest to oldest")
    void shouldSortByAgeAscending() {

        List<Animal> sorted = sorter.sortByAge(List.of(buddy, luna, max, bella));

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Bella", "Luna", "Buddy", "Max");
    }

    @Test
    @DisplayName("sortByAge: returns empty list for null input")
    void shouldReturnEmptyForNullInput() {
        // TODO: Call sorter.sortByAge(null)
        assertThat(sorter.sortByAge(null)).isEmpty();


    }

    @Test
    @DisplayName("sortByAge: returns empty list for empty input")
    void shouldReturnEmptyForEmptyInput() {

        assertThat(sorter.sortByAge(List.of())).isEmpty();
    }

    @Test
    @DisplayName("sortByAge: does not modify the original list")
    void shouldNotModifyOriginalList() {

        List<Animal> original = new ArrayList<>(List.of(buddy, luna, max, bella));

        sorter.sortByAge(original);

        assertThat(original)
                .extracting(Animal::getName)
                .containsExactly("Buddy", "Luna", "Max", "Bella");
    }

    // --- sortByName ---

    @Test
    @DisplayName("sortByName: returns animals in alphabetical order")
    void shouldSortByNameAlphabetically() {
        List<Animal> sorted = sorter.sortByName(List.of(buddy, luna, max, bella));

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Bella", "Buddy", "Luna", "Max");
    }

    @Test
    @DisplayName("sortByName: is case-insensitive")
    void shouldSortNamesCaseInsensitively() {

        Animal alpha = new Animal("Alpha", "Dog", 1, true, LocalDate.now());
        Animal zebra = new Animal("zebra", "Cat", 2, true, LocalDate.now());

        List<Animal> sorted = sorter.sortByName(List.of(zebra, alpha));

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Alpha", "zebra");
    }

    // --- sortByIntakeDate ---

    @Test
    @DisplayName("sortByIntakeDate: returns animals from earliest to latest")
    void shouldSortByIntakeDateAscending() {

        List<Animal> sorted = sorter.sortByIntakeDate(List.of(buddy, luna, max, bella));

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Bella", "Luna", "Buddy", "Max");
    }

    // --- sortBySpeciesThenAgeDescending ---

    @Test
    @DisplayName("sortBySpeciesThenAgeDescending: groups by species then orders by age desc")
    void shouldSortBySpeciesThenAgeDesc() {

        List<Animal> sorted = sorter.sortBySpeciesThenAgeDescending(List.of(buddy, luna, max, bella));

        assertThat(sorted)
                .extracting(Animal::getName)
                .containsExactly("Luna", "Bella", "Max", "Buddy");
    }
}
