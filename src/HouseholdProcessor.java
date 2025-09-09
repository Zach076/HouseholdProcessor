import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HouseholdProcessor {
    public static void main(String[] args) {
        for(String fileName: args) {
            String raw = openFile(fileName);
            if (raw != "") {
                HashMap<String, List<Person>> households = processData(raw);
                households = sortResidents(households);
                printHouseholds(households);
            }
        }
    }

    /*
     * Just for printing residents at each house
     */
    public static void printHouseholds(HashMap<String, List<Person>> households) {
        for (String key: households.keySet()) {
            List<Person> residents = households.get(key);
            System.out.println("Household of " + Integer.toString(residents.size()) + " at " + key + ": ");
            for (Person p: residents) {
                if (p.age >= 13) {
                    p.printDetails();
                }
            }
            System.out.println();
        }
    }

    /*
     * Helper function to contain sorting residents at each household
     */
    public static HashMap<String, List<Person>> sortResidents(HashMap<String, List<Person>> households) {
        for (String key: households.keySet()) {
            List<Person> residents = households.get(key);
            Collections.sort(residents, new PersonComparator());
            households.put(key, residents);
        }
        return households;
    }

    /*
     * Helper function to standardize the format of addresses
     */
    public static String getAddress(String street, String city, String state) {
        return street + " " + city + ", " + state;
    }

    /*
     * Function to validate that given details are valid and formatted before building a Person class from them
     */ 
    public static Person validatePerson(String[] details) {
        if (details.length < 6) {
            System.out.println("Expecting input format of \"firstName\",\"lastName\",\"address\",\"cityName\",\"state\",\"age\"");
            System.out.println("Where age is a whole numerical value.");
            System.out.println("Less than 6 elements given for a person: ");
            for (int x = 0; x < details.length; x++) {
                System.out.print(details[x]);
            }
            System.out.println("");
            return null;
        }
        String first = details[0];
        String last = details[1];
        String street = details[2].toLowerCase();

        // TODO: capitalize first letter of city name
        String city = details[3].toLowerCase();

        // TODO: make helper function to change full state names to abreviations (Washington to WA)
        //       and potentially support for foreign address in the future
        String state = details[4].toUpperCase();
        String address = getAddress(street, city, state);
        Integer age;
        try {
            age = Integer.valueOf(details[5]);
        } catch(NumberFormatException e) {
            System.out.println("Could not process age given for a person: ");
            for (int x = 0; x < details.length; x++) {
                System.out.print(details[x]);
            }
            System.out.println("");
            return null;
        }
        Person p = new Person(first, last, street, city, state, address, age);
        return p;
    }

    /*
     * Function to take a raw String formatted as such:
     * "firstName","lastName","address","cityName","state","age"
     * "firstName","lastName","address","cityName","state","age"
     * ...etc
     * and return a HashMap with keys being addresses, and a list of residents as values
     */
    public static HashMap<String, List<Person>> processData(String raw) {
        HashMap<String, List<Person>> households = new HashMap<String, List<Person>>();
        String[] people = raw.split("\"\"");

        //remove quotes at start of first and end of last element in 'people'
        String firstElement = people[0];
        if (firstElement.startsWith("\"")) {
            CharSequence removedQuote = firstElement.subSequence(1, firstElement.length());
            people[0] = removedQuote.toString();
        }

        String lastElement = people[people.length-1];
        if (lastElement.endsWith("\"")) {
            CharSequence removedQuote = lastElement.subSequence(0, lastElement.length()-1);
            people[people.length-1] = removedQuote.toString();
        }

        //for each set of details, build a person data class and populate them appropriately in a hashMap of households, based on address 
        for (String person: people) {
            String[] details = person.split("\",\\s*\"");
            Person p = validatePerson(details);
            if (p == null) {
                // This means validation failed. For this excercise we will continue and simply omit this 'person' from results
                // TODO: re-evaluate this behavior based on real-world application
            } else {
                List<Person> residents = households.get(p.address);
                if (residents == null) {
                    residents = new LinkedList<Person>();
                }
                residents.add(p);
                households.put(p.address, residents);
            }
        }

        return households;
    }

    /*
     * Function to return raw string data in a file at given path - fileName
     * try/catch because files in java are very particular and scary to me 
     * even though I'm pretty sure the exists() and canRead() functions protect from the would-be-most-common exception: FileNotFoundException
     */
    public static String openFile(String fileName) {
        String rawString = "";

        try {
            File myFile = new File(fileName);

            if (myFile.exists() && myFile.canRead()) {
                Scanner myReader = new Scanner(myFile);
                while (myReader.hasNextLine()) {
                    rawString += myReader.nextLine();
                }
                myReader.close();
            } else {
                System.out.println("Could not find readable file at " + fileName);
            }

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return rawString;
    }
}