package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepositoryMock; // Mock du ChambreRepository

    @InjectMocks
    private ChambreServiceImpl chambreService; // Injection automatique du mock dans le service

    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    public void setUp() {
        // Initialisation des chambres à tester
        chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101);  // Correctly passing a long for numeroChambre
        chambre1.setTypeC(TypeChambre.SIMPLE);
        chambre1.setReservations(new HashSet<>());  // Initialize the reservations set if necessary
        chambre1.setBloc(new Bloc());  // Provide a mock or an actual instance of Bloc

        chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102);  // Correctly passing a long for numeroChambre
        chambre2.setTypeC(TypeChambre.DOUBLE);
        chambre2.setReservations(new HashSet<>());
        chambre2.setBloc(new Bloc());
    }

    @Test
    public void testRetrieveAllChambres() {
        // Arrange : Définir le comportement du mock pour findAll()
        when(chambreRepositoryMock.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act : Appeler la méthode du service
        var result = chambreService.retrieveAllChambres();

        // Assert : Vérifier que la méthode retourne bien la liste attendue
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(chambre1));
        assertTrue(result.contains(chambre2));

        // Vérifier que findAll a été appelé une fois
        verify(chambreRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testRetrieveChambre() {
        // Arrange : Définir le comportement du mock pour findById()
        when(chambreRepositoryMock.findById(1L)).thenReturn(Optional.of(chambre1));

        // Act : Appeler la méthode du service
        Chambre result = chambreService.retrieveChambre(1L);

        // Assert : Vérifier que la chambre retournée est celle attendue
        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre()); // Vérifier avec le bon nom
        assertEquals(TypeChambre.SIMPLE, result.getTypeC());

        // Vérifier que findById a été appelé avec l'ID 1
        verify(chambreRepositoryMock, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveChambre_NotFound() {
        // Arrange : Définir le comportement du mock pour findById()
        when(chambreRepositoryMock.findById(999L)).thenReturn(Optional.empty());

        // Act : Appeler la méthode du service
        Chambre result = chambreService.retrieveChambre(999L);

        // Assert : Vérifier que la chambre retournée est null
        assertNull(result);

        // Vérifier que findById a été appelé avec l'ID 999
        verify(chambreRepositoryMock, times(1)).findById(999L);
    }

    @Test
    public void testAddChambre() {
        // Arrange : Définir le comportement du mock pour save()
        when(chambreRepositoryMock.save(chambre1)).thenReturn(chambre1);

        // Act : Appeler la méthode du service
        Chambre result = chambreService.addChambre(chambre1);

        // Assert : Vérifier que la chambre a bien été ajoutée
        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre()); // Vérifier avec le bon nom
        assertEquals(TypeChambre.SIMPLE, result.getTypeC());

        // Vérifier que save a été appelé avec la chambre
        verify(chambreRepositoryMock, times(1)).save(chambre1);
    }

    @Test
    public void testModifyChambre() {
        // Arrange : Définir le comportement du mock pour save()
        when(chambreRepositoryMock.save(chambre1)).thenReturn(chambre1);

        // Act : Appeler la méthode du service
        Chambre result = chambreService.modifyChambre(chambre1);

        // Assert : Vérifier que la chambre a bien été modifiée
        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre()); // Vérifier avec le bon nom
        assertEquals(TypeChambre.SIMPLE, result.getTypeC());

        // Vérifier que save a été appelé une fois
        verify(chambreRepositoryMock, times(1)).save(chambre1);
    }

    @Test
    public void testRemoveChambre() {
        // Act : Appeler la méthode du service
        chambreService.removeChambre(1L);

        // Assert : Vérifier que deleteById a été appelé une fois
        verify(chambreRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        // Arrange : Définir le comportement du mock pour findAllByTypeC()
        when(chambreRepositoryMock.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1));

        // Act : Appeler la méthode du service
        var result = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        // Assert : Vérifier que la méthode retourne bien la chambre avec le type SIMPLE
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(chambre1));

        // Vérifier que findAllByTypeC a été appelé une fois
        verify(chambreRepositoryMock, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }

    @Test
    public void testTrouverChambreSelonEtudiant() {
        // Arrange : Définir le comportement du mock pour trouverChselonEt()
        when(chambreRepositoryMock.trouverChselonEt(123456789L)).thenReturn(chambre1);

        // Act : Appeler la méthode du service
        Chambre result = chambreService.trouverchambreSelonEtudiant(123456789L);

        // Assert : Vérifier que la chambre retournée est celle attendue
        assertNotNull(result);
        assertEquals(101L, result.getNumeroChambre());

        // Vérifier que trouverChselonEt a été appelé avec le bon CIN
        verify(chambreRepositoryMock, times(1)).trouverChselonEt(123456789L);
    }
}
