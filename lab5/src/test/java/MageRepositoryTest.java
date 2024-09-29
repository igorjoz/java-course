import static org.junit.Assert.*;

import com.igorjoz.lab4.Mage;
import com.igorjoz.lab4.MageRepository;
import org.junit.Before;
import org.junit.Test;

public class MageRepositoryTest {
    private MageRepository repository;

    @Before
    public void setUp() {
        repository = new MageRepository();
        repository.save(new Mage("Marek", 100));
    }

    @Test
    public void testFindExistingMage() {
        assertTrue(repository.find("Marek").isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingMage() {
        repository.delete("Andrzej");
    }

    @Test
    public void testSaveAndFindMage() {
        Mage newMage = new Mage("Andrzej", 150);
        repository.save(newMage);
        assertEquals(newMage, repository.find("Andrzej").get());
    }
}
