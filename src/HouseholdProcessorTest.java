import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class HouseholdProcessorTest {

    @Test
    public void testValidatePerson() {
        Person expectedPerson = new Person("First", "Last", "street", "city", "STATE", "street city, STATE", 15);
        String[] details = new String[6];
        details[0] = "First";
        details[1] = "Last";
        details[2] = "stREet";
        details[3] = "ciTy";
        details[4] = "sTAte";
        details[5] = "15";
        Person actualPerson = HouseholdProcessor.validatePerson(details);
        assertEquals(expectedPerson.first, actualPerson.first);
        assertEquals(expectedPerson.last, actualPerson.last);
        assertEquals(expectedPerson.street, actualPerson.street);
        assertEquals(expectedPerson.city, actualPerson.city);
        assertEquals(expectedPerson.state, actualPerson.state);
        assertEquals(expectedPerson.address, actualPerson.address);
        assertEquals(expectedPerson.age, actualPerson.age);
    }

    @Test
    public void testMissingFieldsValidation() {
        String[] details = new String[5];
        details[0] = "First";
        details[1] = "Last";
        details[2] = "stREet";
        details[3] = "ciTy";
        details[4] = "sTAte";
        Person actualPerson = HouseholdProcessor.validatePerson(details);
        assertEquals(null, actualPerson);
    }

    @Test
    public void testAgeNanValidation() {
        String[] details = new String[6];
        details[0] = "First";
        details[1] = "Last";
        details[2] = "stREet";
        details[3] = "ciTy";
        details[4] = "sTAte";
        details[5] = "fifteen";
        Person actualPerson = HouseholdProcessor.validatePerson(details);
        assertEquals(null, actualPerson);
    }

    @Test
    public void testProcessData() {
        Person mockPerson = new Person("First", "Last", "street", "city", "STATE", "street city, STATE", 15);
        Person mockPerson2 = new Person("First2", "Last2", "street", "city", "STATE", "street city, STATE", 15);
        Person mockPerson3 = new Person("First", "Last", "street2", "city", "STATE", "street2 city, STATE", 15);
        List<Person> streetKids = new LinkedList<>();
        streetKids.add(mockPerson);
        streetKids.add(mockPerson2);
        List<Person> street2Kids = new LinkedList<>();
        street2Kids.add(mockPerson3);
        HashMap<String, List<Person>> expectedHashMap = new HashMap<>();
        expectedHashMap.put("street city, STATE", streetKids);
        expectedHashMap.put("street2 city, STATE", street2Kids);

        String rawInput = "\"First\",\"Last\",\"street\",\"city\",\"STATE\", \"15\"\"First2\",\"Last2\",\"street\",\"city\",\"STATE\", \"15\"\"First\",\"Last\",\"street2\",\"city\",\"STATE\", \"15\"";
        HashMap<String, List<Person>> actualHashMap = HouseholdProcessor.processData(rawInput);
        assertEquals(expectedHashMap.size(), actualHashMap.size());
        assertEquals(expectedHashMap.keySet(), actualHashMap.keySet());
        for (String key : expectedHashMap.keySet()) {
            List<Person> expectedPeople = expectedHashMap.get(key);
            List<Person> actualPeople = actualHashMap.get(key);
            assertEquals(expectedPeople.size(), actualPeople.size());
            System.out.println(expectedPeople);
            System.out.println(actualPeople);
            for (int x = 0; x < expectedPeople.size(); x++) {
                assertEquals(true, actualPeople.get(x).equals(expectedPeople.get(x)));
            }
        }
    }
}
