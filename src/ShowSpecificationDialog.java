import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowSpecificationDialog extends JDialog {

    JLabel nameLabel = new JLabel();
    JLabel realTempRangeLabel = new JLabel();
    JLabel feelsLikeTempRangeLabel = new JLabel();
    JLabel bodyPartLabel = new JLabel();
    JLabel waterResistanceLabel = new JLabel();

    JButton closeButton = new JButton();

    public ShowSpecificationDialog(ShowClothingDialog owner, String title, Clothing clothing) {
        // creating dialog
        super(owner, title);
        setBounds(200,100, 300, 200);

        // creating pane
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // setting labels to clothing values
        nameLabel.setText("NAME: " + clothing.name);
        bodyPartLabel.setText("BODY PART: " +clothing.bodyPart);
        realTempRangeLabel.setText("<html>TEMPERATURE RANGE <br>   from: " + clothing.realTempRange[0]+ " to: " + clothing.realTempRange[1] + " degrees" + "</html>");
        feelsLikeTempRangeLabel.setText("<html> FEELS LIKE TEMPERATURE RANGE <br>   from: " + clothing.feelsLikeRange[0] + " to: " + clothing.feelsLikeRange[1] + " degrees"+ "</html>");

        if(clothing.waterResistance){
            waterResistanceLabel.setText("Water Resistant");
        } else {
            waterResistanceLabel.setText("Not water resistant");
        }

        closeButton.setText("close");

        // adding to pane
        mainPane.add(nameLabel);
        mainPane.add(bodyPartLabel);
        mainPane.add(realTempRangeLabel);
        mainPane.add(feelsLikeTempRangeLabel);
        mainPane.add(waterResistanceLabel);
        mainPane.add(closeButton);

        //
        Container container = getContentPane();
        container.add(mainPane);


        // closing dialog
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowSpecificationDialog.this.setVisible(false);
            }
        });

    }
}
