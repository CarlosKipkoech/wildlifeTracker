package modules;



import static org.junit.Assert.*;
import org.junit.*;


public class AnimalTest {

    public Animal setUpNewAnimal() {
        return new Animal("lion");
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void animal_instantiatesCorrectly_true() {
        Animal testAnimal = setUpNewAnimal();
        assertNotNull(testAnimal);
    }

    @Test
    public void getName_instantiatesWithName_String() {
        Animal testAnimal = setUpNewAnimal();
        assertEquals("lion", testAnimal.getName());
    }


    @Test
    public void equals_returnsTrueObjectsAreSame_true() {
        Animal testAnimal = setUpNewAnimal();
        Animal otherAnimal = setUpNewAnimal();
        assertEquals(testAnimal, otherAnimal);
    }

    @Test
    public void save_returnsTrueIfObjectIsSame() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        assertEquals(Animal.all().get(0), testAnimal);
    }

    @Test
    public void save_assignsIdToAnimal() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(savedAnimal.getId(), testAnimal.getId());
    }

    @Test
    public void all_returnsAllInstancesOfAnimal_true() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        Animal otherAnimal = new Animal("");
        otherAnimal.save();
        assertEquals(Animal.all().get(0), testAnimal);
        assertEquals(Animal.all().get(1), otherAnimal);
    }

    @Test
    public void find_returnsAnimalWithSameId_otherAnimal() {
        Animal firstAnimal =setUpNewAnimal();
        firstAnimal.save();
        Animal secondAnimal = new Animal("");
        secondAnimal.save();
        assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
    }


    @Test
    public void delete_deletesAnimalFromDatabase_true() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertNull(Animal.find(testAnimal.getId()));
    }
}