public class Person{
    String first;
    String last;
    String street;
    String city;
    String state;
    String address;
    Integer age;

    public Person(String first, String last, String street, String city, String state, String address, Integer age) {
        this.first = first;
        this.last = last;
        this.street = street;
        this.city = city;
        this.state = state;
        this.address = address;
        this.age = age;
    }

    public void printDebugDetails() {
        System.out.println("Street: " + street);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        printDetails();
    }

    public void printDetails() {
        System.out.println("First name: " + first);
        System.out.println("Last name: " + last);
        System.out.println("Address: " + address);
        System.out.println("Age: " + age.toString());
    }

    public boolean equals(Person p) {
        return (this.first.equals(p.first) &&
        this.last.equals(p.last) &&
        this.street.equals(p.street) &&
        this.city.equals(p.city) &&
        this.state.equals(p.state) &&
        this.address.equals(p.address) &&
        this.age == p.age);
    }
}
