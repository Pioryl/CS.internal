import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
                ManageClothing.Clothing[] clothes = ManageClothing.loadAllClothing("clothes/");
                ArrayList<ArrayList<ManageClothing.Clothing>> sortedClothes = ManageClothing.suggestClothing(clothes, rain,
                                                                                Double.parseDouble(data[1]),
                                                                                Double.parseDouble(data[2]));

                for(int i = 0; i<9; i++) {
                    List<ManageClothing.Clothing> suggestedClothingForBodyPart = sortedClothes.get(i);
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
            Dialogs.AddClothingDialog addClothingDialog = new Dialogs.AddClothingDialog(ClothesGUI.this, "add clothing dialog");
            addClothingDialog.setModal(true);
            addClothingDialog.setVisible(true);

            // after closing
            if (!addClothingDialog.canceled){
                try {
                    ManageClothing.saveClothing("clothes/", new ManageClothing.Clothing(
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
            ManageClothing.Clothing[] clothes = ManageClothing.loadAllClothing("clothes/");
            // opening
            Dialogs.ShowClothingDialog showClothingDialog = new Dialogs.ShowClothingDialog(ClothesGUI.this, "show clothing", clothes);
            showClothingDialog.setModal(true);
            showClothingDialog.setVisible(true);
            if(showClothingDialog.reopen){
                clothes = ManageClothing.loadAllClothing("clothes/");
                showClothingDialog = new Dialogs.ShowClothingDialog(ClothesGUI.this, "show clothing", clothes);
                showClothingDialog.setModal(true);
                showClothingDialog.setVisible(true);
            }
        });

        helpButton.addActionListener(e -> {
         IntroPage.doEverything();
         //panel.dispose();
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

}
