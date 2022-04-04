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
        ArrayList<ArrayList<Clothing> > out = new ArrayList<ArrayList<Clothing> >(9);
        // adding inside arraylists for every body part
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
        out.add(new ArrayList<Clothing>());
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
        map.put("accessories", 0);
        map.put("head", 1);
        map.put("eyes", 2);
        map.put("neck", 3);
        map.put("body1", 4);
        map.put("body2", 5);
        map.put("hands", 6);
        map.put("legs", 7);
        map.put("feet", 8);

        for (Clothing clothing : clothes){
            int index = map.get(clothing.bodyPart);
            System.out.println("WWWWWWW OUT" + out);
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

    public static class clothingComparator  implements Comparator<Clothing> {

        @Override
        public int compare(Clothing clothing1, Clothing clothing2){
            return clothing2.rating - clothing1.rating;
        }
    }

    public static class Clothing implements Serializable{
        // ducktape fix for a wierd error
        @Serial
        private static final long serialVersionUID = -5116697425488699642L;
        // starting variables
        int[] realTempRange = {};
        int[] feelsLikeRange = {};
        String bodyPart;
        String name;

        boolean waterResistance;
        int rating;

        public Clothing(String givenName, int[] givenRealTempRange, int[] givenFeelsLikeRange, String givenBodyPart, boolean givenWaterResistance){
            this.realTempRange = givenRealTempRange;
            this.feelsLikeRange = givenFeelsLikeRange;
            this.bodyPart = givenBodyPart;
            this.name = givenName;
            this.waterResistance = givenWaterResistance;
        }

        public void rateClothing(double realTemp, double feelsLikeTemp, boolean rain){
            this.rating = 0;
            if (!rain || this.waterResistance){
                this.rating = 100;
                // decreasing the rating by the distance from mean real and feel like temp

                int realMean = this.realTempRange[0] + (Math.abs(this.realTempRange[1] - this.realTempRange[0])/2);
                //System.out.println("/n"+ "name: "+ name);
                //System.out.println("this.realTempRange[1] " + this.realTempRange[1]);
                //System.out.println("this.realTempRange[0] " + this.realTempRange[0]);
                //System.out.println("realMean " + realMean);
                //int realMean = (this.realTempRange[1] - this.realTempRange[0])/2;
                int distance = (int) (Math.max(realTemp, realMean) - Math.min(realTemp, realMean));
                //System.out.println("(int) Math.abs(realTemp - realMean) " + distance);
                this.rating -= distance;
                //System.out.println("this.rating -= distance " + this.rating);


                int feelsLikeMean = this.feelsLikeRange[0] + (Math.abs(this.feelsLikeRange[1] - this.feelsLikeRange[0])/2);
                //System.out.println("this.feelsLikeRange[1] " + this.feelsLikeRange[1]);
                //System.out.println("this.feelsLikeRange[0] " + this.feelsLikeRange[0]);
                //System.out.println("feelsLikeMean " + feelsLikeMean);
                //int feelsLikeMean = (this.feelsLikeRange[1] - this.feelsLikeRange[0])/2;
                distance = (int) (Math.max(feelsLikeTemp, feelsLikeMean) - Math.min(feelsLikeTemp, feelsLikeMean));
                //System.out.println("distance " + distance);
                //distance = (int) (feelsLikeTemp - feelsLikeMean);
                this.rating -= distance;
                System.out.println("FINAL this.rating -= distance " + this.rating);
            }
        }

    }
}
