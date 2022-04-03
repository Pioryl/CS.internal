import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClothesGUI extends JFrame{

    private JPanel mainPanel;
    private JTextField cityTextField;
    private JTextField countryTextField;
    private JButton updateButton;
    private JLabel weatherForecast;
    private JLabel locationLabel;
    private JLabel feelsLikeLabel;
    private JLabel conditionsLabel;
    private JLabel temperatureLabel;

    private JLabel accessoriesSuggestionLabel;
    private JLabel headSuggestionLabel;
    private JLabel eyesSuggestionLabel;
    private JLabel neckSuggestionLabel;
    private JLabel body1SuggestionLabel;
    private JLabel body2SuggestionLabel;
    private JLabel handsSuggestionLabel;
    private JLabel legsSuggestionLabel;
    private JLabel feetSuggestionLabel;

    private JButton addClothing;
    private JButton showClothingButton;
    private JButton helpButton;
    private JLabel twoLetterCountryAbbreviationLabel;
    private JLabel realTemperatureLabel;
    private JLabel feelsLikeTemperatureLabel;
    private JLabel currentConditionsLabel;
    private final JLabel[] clothingSuggestionLabels = {accessoriesSuggestionLabel, headSuggestionLabel, eyesSuggestionLabel,
                    neckSuggestionLabel, body1SuggestionLabel, body2SuggestionLabel, handsSuggestionLabel, legsSuggestionLabel, feetSuggestionLabel};


    public ClothesGUI(String title)  {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();


        locationLabel.setFont(new Font("Rockwell", Font. PLAIN, 25));
        twoLetterCountryAbbreviationLabel.setFont(new Font("Rockwell", Font. PLAIN, 25));
        weatherForecast.setFont(new Font("Rockwell", Font. BOLD, 30));
        realTemperatureLabel.setFont(new Font("Rockwell", Font. PLAIN, 20));
        feelsLikeTemperatureLabel.setFont(new Font("Rockwell", Font. PLAIN, 20));
        currentConditionsLabel.setFont(new Font("Rockwell", Font. PLAIN, 20));
        updateButton.setFont(new Font("Rockwell", Font. BOLD, 20));


        // centering

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)dimension.getWidth();
        int height = (int)dimension.getHeight();
        int sizeX = 240;
        int sizeY = 100;

        updateButton.setBounds(0, 0, sizeX, sizeY);
//System.out.println(dimension);
        this.setBounds(0, 0, width, height);



        updateButton.addActionListener(e -> {
            //grab the location
            String city = cityTextField.getText();
            String countryCode = countryTextField.getText();
            boolean rain;
            // updating the weather
            try {
                String[] data = WeatherForecastRetrieval.timelineRequestHttpClient(city, countryCode);
                assert data != null;
                // setting the weather
                conditionsLabel.setText(data[0]);
                temperatureLabel.setText(data[1]);
                feelsLikeLabel.setText(data[2]);

                //feelsLikeLabel.setFont(new Font("Serif", Font. BOLD, 16));
                //conditionsLabel.setFont(new Font("Serif", Font. BOLD, 16));
                //temperatureLabel.setFont(new Font("Serif", Font. BOLD, 16));


                System.out.println(Arrays.toString(data));
                // marking rain
                rain = isSubString("rain", data[0]);
                // updating clothes
                Clothing[] clothes = ManageClothing.loadAllClothing("clothes/");
                ArrayList<ArrayList<Clothing>> sortedClothes = ManageClothing.suggestClothing(clothes, rain,
                                                                                Double.parseDouble(data[1]),
                                                                                Double.parseDouble(data[2]));

                for(int i = 0; i<9; i++) {
                    List<Clothing> suggestedClothingForBodyPart = sortedClothes.get(i);
                    System.out.println(suggestedClothingForBodyPart);
                    JLabel currentBodyPartLabel = clothingSuggestionLabels[i];

                    if (!suggestedClothingForBodyPart.isEmpty()) {
                        String name = suggestedClothingForBodyPart.get(0).name;
                        System.out.println(name);
                        currentBodyPartLabel.setText(name);
                    } else {
                        currentBodyPartLabel.setText("not added");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        addClothing.addActionListener(e -> {
            // opening
            AddClothingDialog addClothingDialog = new AddClothingDialog(ClothesGUI.this, "add clothing dialog");
            addClothingDialog.setModal(true);
            addClothingDialog.setVisible(true);

            // after closing
            if (!addClothingDialog.canceled){
                try {
                    ManageClothing.saveClothing("clothes/", new Clothing(
                            addClothingDialog.name,
                            addClothingDialog.givenRealTemp,
                            addClothingDialog.givenFeelsLikeTemp,
                            addClothingDialog.givenBodyPart,
                            addClothingDialog.givenWaterResistance));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        showClothingButton.addActionListener(e -> {
            // getting clothes
            Clothing[] clothes = ManageClothing.loadAllClothing("clothes/");
            // opening
            ShowClothingDialog showClothingDialog = new ShowClothingDialog(ClothesGUI.this, "show clothing", clothes);
            showClothingDialog.setModal(true);
            showClothingDialog.setVisible(true);
            if(showClothingDialog.reopen){
                clothes = ManageClothing.loadAllClothing("clothes/");
                showClothingDialog = new ShowClothingDialog(ClothesGUI.this, "show clothing", clothes);
                showClothingDialog.setModal(true);
                showClothingDialog.setVisible(true);
            }
        });

        helpButton.addActionListener(e -> {
         IntroPage.doEverything();
         //mainPanel.dispose();
        });


    }

    //searching for "rain" in a larger string
    public static boolean isSubString(String string1, String string2) {
        int length1 = string1.length();
        int length2 = string2.length();

        for (int i = 0; i< length2-length1; i++){
            int j;
            for (j = 0; j < length1;j++){
                if (string2.charAt(i+j) != string1.charAt(j)){
                    break;
                }
            }

            if (j == length1)
                return true;
        }
        return false;
    }

    public static void run(){
        JFrame frame = new ClothesGUI( "Clothes app");
        frame.setVisible(true);
    }

    public static void main(String[] args){
        JFrame frame = new ClothesGUI( "Official clothe choosing algorithm");
        frame.setVisible(true);
    }

    public static class WeatherForecastRetrieval {

        public static String[] timelineRequestHttpClient(String city, String countryCode) throws Exception {
            //set up the end point

            // creating the local date in a correct form, without nanoseconds
            String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
            String location=city+","+countryCode;

            // getting current time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatted = dtf.format(now);
            formatted = formatted.substring(0, 10) + "T" + formatted.substring(11);

            String time=formatted;

            String unitGroup="metric";
            String apiKey="HADZ5CTFXJDGEGMB9TGHCHH7P";

            StringBuilder requestBuilder=new StringBuilder(apiEndPoint);
            requestBuilder.append(URLEncoder.encode(location, StandardCharsets.UTF_8.toString()));
            requestBuilder.append("/").append(time);
            URIBuilder builder = new URIBuilder(requestBuilder.toString());

            builder.setParameter("unitGroup", unitGroup)
                    .setParameter("key", apiKey);



            HttpGet get = new HttpGet(builder.build());

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = httpclient.execute(get);

            String rawResult=null;
            try {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    System.out.printf("Bad response status code:%d%n", response.getStatusLine().getStatusCode());
                    return null;
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rawResult= EntityUtils.toString(entity, Charset.forName("utf-8"));
                }


            } finally {
                response.close();
            }

            return parseTimelineJson(rawResult);

        }
        private static String[] parseTimelineJson(String rawResult) {

            if (rawResult==null || rawResult.isEmpty()) {
                System.out.printf("No raw data%n");
                return null;
            }

            JSONObject timelineResponse = new JSONObject(rawResult);

            ZoneId zoneId=ZoneId.of(timelineResponse.getString("timezone"));

            JSONObject current = timelineResponse.getJSONObject("currentConditions");
            String conditions = current.getString("conditions");
            String realTemp = Double.toString(current.getDouble("temp"));
            String feelsLike = Double.toString(current.getDouble("feelslike"));
            String[] data = {conditions, realTemp, feelsLike};

            return data;
    /*
            JSONArray values=timelineResponse.getJSONArray("days");
            System.out.printf("Date\tMaxTemp\tMinTemp\tPrecip\tSource%n");
            for (int i = 0; i < values.length(); i++) {
                JSONObject dayValue = values.getJSONObject(i);

                ZonedDateTime datetime=ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId);

                double maxtemp=dayValue.getDouble("tempmax");
                double mintemp=dayValue.getDouble("tempmin");
                double pop=dayValue.getDouble("precip");
                String source=dayValue.getString("source");
                System.out.printf("%s\t%.1f\t%.1f\t%.1f\t%s%n", datetime.format(DateTimeFormatter.ISO_LOCAL_DATE), maxtemp, mintemp, pop,source );
            }

     */
        }



        public static void main(String[] args)  throws Exception {
            System.out.println(Arrays.toString(WeatherForecastRetrieval.timelineRequestHttpClient("Warsaw", "PL")));
        }
    }
}
