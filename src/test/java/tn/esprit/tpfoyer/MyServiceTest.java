package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;
    private List<Etudiant> listEtudiants;

    @BeforeEach
    void setUp() {
        // Initialize single Etudiant object with sample data
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());

        // Initialize list of Etudiant objects
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(2L);
        etudiant1.setNomEtudiant("Jane");
        etudiant1.setPrenomEtudiant("Smith");
        etudiant1.setCinEtudiant(87654321L);
        etudiant1.setDateNaissance(new Date());

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(3L);
        etudiant2.setNomEtudiant("Alice");
        etudiant2.setPrenomEtudiant("Brown");
        etudiant2.setCinEtudiant(11223344L);
        etudiant2.setDateNaissance(new Date());

        listEtudiants = Arrays.asList(etudiant1, etudiant2);
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(listEtudiants);

        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Jane", result.get(0).getNomEtudiant());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testModifyEtudiant() {
        // Arrange
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals(12345678L, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Arrange
        Long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);

        // Act
        etudiantService.removeEtudiant(etudiantId);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    void testRecupererEtudiantParCin() {
        // Arrange
        long cin = 12345678L;
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.recupererEtudiantParCin(cin);

        // Assert
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }
}