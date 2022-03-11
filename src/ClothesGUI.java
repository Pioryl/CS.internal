import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClothesGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField cityTextField;
    private JLabel weatherForecast;
    private JLabel locationLabel;
    private JButton updateButton;
    private JLabel feelsLikeLabel;
    private JLabel conditionsLabel;
    private JTextField countryTextField;
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
    private JScrollBar scrollBar1;
    private final JLabel[] clothingSuggestionLabels = {accessoriesSuggestionLabel, headSuggestionLabel, eyesSuggestionLabel, neckSuggestionLabel, body1SuggestionLabel, body2SuggestionLabel, handsSuggestionLabel, legsSuggestionLabel, feetSuggestionLabel};

    public ClothesGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        // centering
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println(dimension);
        int x = (int) (dimension.getWidth())/2 - (this.getWidth()/2);
        int y = (int) (dimension.getHeight())/2 - (this.getHeight()/2);
        this.setBounds(x, y, this.getWidth(), this.getHeight());


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //grab the location
                String city = cityTextField.getText();
                String countryCode = countryTextField.getText();
                boolean rain = false;
                // updating the weather
                try {
                    String[] data = WeatherForecastRetrieval.timelineRequestHttpClient(city, countryCode);
                    assert data != null;
                    // setting the weather
                    conditionsLabel.setText(data[0]);
                    temperatureLabel.setText(data[1]);
                    feelsLikeLabel.setText(data[2]);
                    System.out.println(Arrays.toString(data));
                    // marking rain
                    rain = isSubString("rain", data[0]);
                    // updating clothes
                    Clothing[] clothes = ManageClothing.loadAllClothing("clothes/");
                    ArrayList<ArrayList<Clothing>> sortedClothes = ManageClothing.suggestClothing(clothes, rain,
                                                                                    Double.parseDouble(data[1]),
                                                                                    Double.parseDouble(data[2]));
                    // lol thats wierd it has to be done
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

            }
        });

        addClothing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

            }
        });

        showClothingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
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
        JFrame frame = new ClothesGUI( "Clothes app");
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
