import com.igorjoz.lab4.Mage;
import com.igorjoz.lab4.MageController;
import com.igorjoz.lab4.MageRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class MageControllerTest {
    private MageRepository repositoryMock;
    private MageController controller;

    @Before
    public void setUp() {
        repositoryMock = mock(MageRepository.class);
        controller = new MageController(repositoryMock);
    }

    @Test
    public void deleteNonExistingMageReturnsNotFound() {
        when(repositoryMock.find("NonExisting")).thenReturn(Optional.empty());
        String result = controller.delete("NonExisting");
        assertEquals("not found", result);
    }

    @Test
    public void deleteExistingMageReturnsDone() {
        Mage existingMage = new Mage("ExistingMage", 50);
        when(repositoryMock.find("ExistingMage")).thenReturn(Optional.of(existingMage));
        doNothing().when(repositoryMock).delete("ExistingMage");
        String result = controller.delete("ExistingMage");
        assertEquals("done", result);
    }

    @Test
    public void saveNewMageReturnsDone() {
        when(repositoryMock.find("NewMage")).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            return "jakas_wartosc_string";
        }).when(repositoryMock).save(any(Mage.class));
        String result = controller.save("NewMage", Integer.parseInt("30"));
        assertEquals("done", result);
    }

    @Test
    public void findNonExistingMageReturnsNotFound() {
        when(repositoryMock.find("NonExisting")).thenReturn(Optional.empty());
        String result = controller.find("NonExisting");
        assertEquals("not found", result);
    }

    @Test
    public void findExistingMageReturnsMageDetails() {
        Mage existingMage = new Mage("ExistingMage", 50);
        when(repositoryMock.find("ExistingMage")).thenReturn(Optional.of(existingMage));
        String result = controller.find("ExistingMage");
        assertTrue(result.contains("ExistingMage") && result.contains("Level: 50"));
    }

    @Test
    public void updateExistingMageLevelReturnsDone() {
        Mage existingMage = new Mage("MageToUpdate", 30);
        when(repositoryMock.find("MageToUpdate")).thenReturn(Optional.of(existingMage));
        String result = controller.save("MageToUpdate", 50);
        assertEquals("done", result);
    }

    @Test
    public void saveMageWithNegativeLevelReturnsBadRequest() {
        String result = controller.save("NewMage", -10);
        assertEquals("bad request", result);
    }

    @Test
    public void findMageDifferentLevelsReturnsCorrectDetails() {
        Mage lowLevelMage = new Mage("LowLevelMage", 5);
        Mage highLevelMage = new Mage("HighLevelMage", 99);

        when(repositoryMock.find("LowLevelMage")).thenReturn(Optional.of(lowLevelMage));
        when(repositoryMock.find("HighLevelMage")).thenReturn(Optional.of(highLevelMage));

        String lowLevelResult = controller.find("LowLevelMage");
        String highLevelResult = controller.find("HighLevelMage");

        assertTrue(lowLevelResult.contains("LowLevelMage") && lowLevelResult.contains("Level: 5"));
        assertTrue(highLevelResult.contains("HighLevelMage") && highLevelResult.contains("Level: 99"));
    }
}
