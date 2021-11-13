//package modules;
//
//import org.sql2o.*;
//import org.junit.*;
//import static org.junit.Assert.*;
//import java.util.Arrays;
//
//import static org.junit.Assert.*;
//
//
//public class SightingTest {
//
//    @Rule
//    public DatabaseRule database = new DatabaseRule();
//
//    public Sighting setUpNewSighting(){
//        return new Sighting("ZoneA","Kevin",1 );
//    }
//    @Test
//    public void sighting_instantiatesCorrectly_true() {
//        Sighting testSighting = new Sighting("ZoneA","Kevin", 1);
//    }
//
//    @Test
//    public void getLocation_sightingInstantiatesWithLocation_ZoneA() {
//        Sighting testSighting = new Sighting("ZoneA","Kevin", 1);
//        assertEquals("ZoneA", testSighting.getLocation());
//
//    }
//
//    @Test
//    public void getRanger_sightingInstantiatesWithRanger_Kevin() {
//        Sighting testSighting = new Sighting("ZoneA","Kevin",1);
//        assertEquals("Kevin", testSighting.getRanger());
//    }
//
//    @Test
//    public void equals_returnsTrueIfLocationAndRangerAreSame_true() {
//        Sighting testSighting = setUpNewSighting();
//        Sighting otherSighting = setUpNewSighting();
//        assertTrue(testSighting.equals(otherSighting));
//    }
//
//    @Test
//    public void save_insertsObjectIntoDatabase_Sighting() {
//        Sighting testSighting = setUpNewSighting();
//        testSighting.save();
//        assertEquals(Sighting.all().get(0), testSighting);
//    }
//
//    @Test
//    public void all_returnsAllInstancesOfSighting_true() {
//        Sighting testSighting = setUpNewSighting();
//        testSighting.save();
//        Sighting otherSighting = new Sighting("","",1);
//        otherSighting.save();
//        assertEquals(true,Sighting.all().get(0).equals(testSighting));
//        assertEquals(true,Sighting.all().get(1).equals(otherSighting));
//    }
//
//    @Test
//    public void save_assignsIdToObject() {
//        Sighting testSighting = setUpNewSighting();
//        testSighting.save();
//        Sighting savedSighting = Sighting.all().get(0);
//        assertEquals(testSighting.getId(),savedSighting.getId());
//    }
//
//    @Test
//    public void find_returnsSightingWithSameId_otherSighting() {
//        Sighting testSighting = setUpNewSighting();
//        testSighting.save();
//        Sighting otherSighting = new Sighting("","",1);
//        otherSighting.save();
//        assertEquals(Sighting.find(otherSighting.getId()), otherSighting);
//    }
//
//
//
//    @Test
//    public void delete_deletesSighting_true() {
//        Sighting newSighting = setUpNewSighting();
//        newSighting.save();
//        newSighting.delete();
//        assertEquals(0, Sighting.all().size());
//    }
//}