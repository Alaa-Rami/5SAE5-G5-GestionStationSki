package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;
    private List<Foyer> listFoyers;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Central Foyer");
        foyer.setCapaciteFoyer(200);

        Foyer foyer1 = new Foyer();
        foyer1.setIdFoyer(2L);
        foyer1.setNomFoyer("East Foyer");
        foyer1.setCapaciteFoyer(150);

        Foyer foyer2 = new Foyer();
        foyer2.setIdFoyer(3L);
        foyer2.setNomFoyer("West Foyer");
        foyer2.setCapaciteFoyer(250);

        listFoyers = Arrays.asList(foyer1, foyer2);
    }

    @Test
    void testRetrieveAllFoyers() {
        when(foyerRepository.findAll()).thenReturn(listFoyers);

        List<Foyer> result = foyerService.retrieveAllFoyers();

        assertEquals(2, result.size());
        assertEquals("East Foyer", result.get(0).getNomFoyer());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Central Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        // Arrange
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Central Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals(200, result.getCapaciteFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        Long foyerId = 1L;
        doNothing().when(foyerRepository).deleteById(foyerId);

        // Act
        foyerService.removeFoyer(foyerId);

        // Assert
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
