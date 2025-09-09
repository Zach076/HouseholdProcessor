import java.util.Comparator;

class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        int lastCmp = p1.last.compareToIgnoreCase(p2.last);
        if (lastCmp != 0) return lastCmp;

        int firstCmp = p1.first.compareToIgnoreCase(p2.first);
        if (firstCmp != 0) return firstCmp;

        return Integer.compare(p1.age, p2.age);
    }
}
