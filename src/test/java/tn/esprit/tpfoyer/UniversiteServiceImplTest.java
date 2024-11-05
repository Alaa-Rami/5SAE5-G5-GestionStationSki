package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private List<Universite> listUniversites;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Université de Test");
        universite.setAdresse("123 Test Street");

        Universite universite2 = new Universite();
        universite2.setIdUniversite(2L);
        universite2.setNomUniversite("Université de Test 2");
        universite2.setAdresse("456 Test Avenue");

        listUniversites = Arrays.asList(universite, universite2);
    }

    @Test
    void testRetrieveAllUniversites() {
        // Arrange
        when(universiteRepository.findAll()).thenReturn(listUniversites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Université de Test", result.get(0).getNomUniversite());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUniversite() {
        // Arrange
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testModifyUniversite() {
        // Arrange
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.modifyUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRemoveUniversite() {
        // Arrange
        Long universiteId = 1L;
        doNothing().when(universiteRepository).deleteById(universiteId);

        // Act
        universiteService.removeUniversite(universiteId);

        // Assert
        verify(universiteRepository, times(1)).deleteById(universiteId);
    }
}

