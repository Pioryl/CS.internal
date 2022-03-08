import java.io.*;
import java.lang.Math.*;

public class Clothing implements Serializable{
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
            int realMean = Math.abs(this.realTempRange[1] - this.realTempRange[0])/2;
            int distance = (int) Math.abs(realTemp - realMean);
            this.rating -= distance;

            int feelsLikeMean = Math.abs(this.feelsLikeRange[1] - this.feelsLikeRange[0])/2;
            distance = (int) Math.abs(feelsLikeTemp - feelsLikeMean);
            this.rating -= distance;
        }
    }

}
