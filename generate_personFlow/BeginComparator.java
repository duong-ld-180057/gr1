import java.util.Comparator;

public class BeginComparator implements Comparator<PersonFlow> {
    @Override
    public int compare(PersonFlow o1, PersonFlow o2) {
        return o1.getBegin() - o2.getBegin();
    }
}
