import java.util.Comparator;

public class clothingComparator  implements Comparator<Clothing> {

    @Override
    public int compare(Clothing clothing1, Clothing clothing2){
        return clothing1.rating - clothing2.rating;
    }
}
