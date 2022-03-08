import javax.print.DocFlavor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ManageClothing {

    public static void saveClothing(String directory, Clothing clothing) throws IOException {
        // function for permanent storage of clothing objects
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(directory +
                clothing.name + ".bin"));
        objectOutputStream.writeObject(clothing);
        objectOutputStream.close();
    }

    public static Clothing[] loadAllClothing(String directory){
        Clothing[] clothes = new Clothing[0];
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            Object[] clothePaths = paths.toArray();
            paths.close();
            clothes = new Clothing[clothePaths.length - 1];
            System.out.println(Arrays.toString(clothePaths));
            // starting from 1 since stream starts with the directory not a file path
            for(int i = 1; i < clothePaths.length; i++){
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.valueOf(clothePaths[i])));
                Clothing cloth = (Clothing) objectInputStream.readObject();
                objectInputStream.close();
                clothes[i-1] = cloth;
            }
            paths.close();
            return clothes;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clothes;
    }

    public static ArrayList<ArrayList<Clothing> > suggestClothing(Clothing[] clothes, boolean rain, double realTemp, double feelsLikeTemp){
        // we know the length of the first array but not the second, and array of arraylist is illegal
        // therefore arraylist of arraylist
        ArrayList<ArrayList<Clothing> > out = new ArrayList<ArrayList<Clothing> >(5);
        // adding inside arraylists for every body part
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        // sorting the clothes by scores
        // updating scores
        for(Clothing clothing : clothes){
            clothing.rateClothing(realTemp, feelsLikeTemp, rain);
        }
        // sorting by scores
        Arrays.sort(clothes, new clothingComparator());
        // adding clothes to adequate parts of body
        // mapping names of body parts to indexes of output array
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("head", 0);
        map.put("body", 1);
        map.put("legs", 2);
        map.put("feet", 3);
        map.put("accessories", 4);

        for (Clothing clothing : clothes){
            int index = map.get(clothing.bodyPart);
            System.out.println(out);
            out.get(index).add(clothing);
        }
        return out;
    }

    public static void deleteClothing(String name) {
        String cwd = System.getProperty("user.dir");
        File file = new File(cwd +"/clothes/" + name + ".bin");
        System.out.println(file.exists());
        System.out.println(file.canWrite());
        if(file.delete()){
            System.out.println("deleted " + name);
        } else {
            System.out.println(file.getPath() + " doesnt exist");
        }
    }

    public static void main(String[] args) throws IOException {
        ManageClothing.deleteClothing("Jacket");
    }
}
