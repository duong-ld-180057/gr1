import java.util.Comparator;

public class FromComparator implements Comparator<PersonFlow> {
    public int compare(PersonFlow p1, PersonFlow p2) {
        return p1.getFrom().compareTo(p2.getFrom());
    }
}
