package lv.bootcamp.shelter.task6;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

/**
 * Task 7: Mocking a dependency
 *
 * Practice:
 * - @Mock and @InjectMocks
 * - when(...).thenReturn(...)
 * - verify(...) and verify(..., never())
 * - Testing with mocked dependencies
 *
 * Instructions:
 * Write tests for IntakeService. The AnimalRepository is mocked — you control what it returns.
 * Focus on verifying that IntakeService calls the repository correctly.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("IntakeService")
class IntakeServiceTest {

    @Mock
    private AnimalRepository repository;

    @InjectMocks
    private IntakeService service;

    private final Animal buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
    private final Animal luna = new Animal("Luna", "Cat", 4, true, LocalDate.of(2026, 1, 15));
    private final Animal max = new Animal("Max", "Dog", 7, false, LocalDate.of(2026, 1, 15));

    // ==================== intake() ====================

    @Nested
    @DisplayName("intake")
    class Intake {

        @Test
        @DisplayName("saves valid animal and returns it")
        void shouldSaveValidAnimal() {

            when(repository.save(buddy)).thenReturn(buddy);

            Animal result = service.intake(buddy);

            assertThat(result.getName()).isEqualTo("Buddy");
            verify(repository, times(1)).save(buddy);
        }

        @Test
        @DisplayName("throws for null animal without calling repository")
        void shouldThrowForNullAnimal() {

            assertThrows(NullPointerException.class, () -> service.intake(null));
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("throws for invalid animal without calling repository")
        void shouldThrowForInvalidAnimal() {

            Animal invalid = new Animal("", "Dog", 3, true, LocalDate.now());

            assertThrows(IllegalArgumentException.class, () -> service.intake(invalid));
            verify(repository, never()).save(any());
        }
    }

    // ==================== findByName() ====================

    @Nested
    @DisplayName("findByName")
    class FindByName {

        @Test
        @DisplayName("returns animal when repository finds it")
        void shouldReturnAnimalWhenFound() {

            when(repository.findByName("Buddy")).thenReturn(Optional.of(buddy));

            Animal result = service.findByName("Buddy");

            assertNotNull(result);
            assertEquals("Buddy", result.getName());
        }

        @Test
        @DisplayName("returns null when repository does not find it")
        void shouldReturnNullWhenNotFound() {

            when(repository.findByName("Ghost")).thenReturn(Optional.empty());

            assertNull(service.findByName("Ghost"));
        }

        @Test
        @DisplayName("throws for blank name without calling repository")
        void shouldThrowForBlankName() {

            assertThrows(IllegalArgumentException.class, () -> service.findByName(""));
            verify(repository, never()).findByName(any());
        }
    }

    // ==================== findBySpecies() ====================

    @Nested
    @DisplayName("findBySpecies")
    class FindBySpecies {

        @Test
        @DisplayName("returns list from repository for valid species")
        void shouldReturnAnimalsForValidSpecies() {


            when(repository.findBySpecies("Dog")).thenReturn(List.of(buddy));

            List<Animal> result = service.findBySpecies("Dog");

            assertThat(result).hasSize(1).contains(buddy);
        }

        @Test
        @DisplayName("returns empty list for blank species without calling repository")
        void shouldReturnEmptyForBlankSpecies() {


            List<Animal> result = service.findBySpecies("");

            assertThat(result).isEmpty();
            verify(repository, never()).findBySpecies(any());
        }

        @Test
        @DisplayName("returns empty list for null species without calling repository")
        void shouldReturnEmptyForNullSpecies() {


            List<Animal> result = service.findBySpecies(null);

            assertThat(result).isEmpty();
            verify(repository, never()).findBySpecies(any());
        }
    }

    // ==================== count() ====================

    @Nested
    @DisplayName("count")
    class Count {

        @Test
        @DisplayName("returns the size of all animals from repository")
        void shouldReturnCountFromRepository() {

            when(repository.findAll()).thenReturn(List.of(buddy, luna, max));
            int result = service.count();
            assertEquals(3, result);

        }

        @Test
        @DisplayName("returns 0 when repository is empty")
        void shouldReturnZeroWhenEmpty() {

            when(repository.findAll()).thenReturn(List.of());

            assertEquals(0, service.count());
        }
    }
}
