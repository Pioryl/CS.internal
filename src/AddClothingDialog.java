import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//dialog for adding new clothing
public class AddClothingDialog extends JDialog {

    // declaring variables needed to create new clothing
    String name = "";
    int[] givenRealTemp = new int[2];
    int[] givenFeelsLikeTemp = new int[2];
    String givenBodyPart = "";
    boolean givenWaterResistance;

    // GUI interface parts
    // for input
    JTextField nameField = new JTextField(20);
    JComboBox<String> bodyPartComboBox = new JComboBox<String>();

    JSpinner lowerRealTempBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 1));
    JSpinner upperRealTempBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 1));

    JSpinner lowerFeelsLikeBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 1));
    JSpinner upperFeelsLikeBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 1));

    JCheckBox waterResistanceCheckBox = new JCheckBox();

    // standard closing
    JButton okButton = new JButton("ok");
    JButton cancelButton = new JButton("cancel");

    // a canceling flag
    boolean canceled = true;

    public AddClothingDialog(Frame owner, String title){
        // making the dialog
        super(owner, title);
        setBounds(100, 100, 400, 400);
        Container dialogContent = getContentPane();
        JPanel firstPane = new JPanel();
        firstPane.setLayout(new BoxLayout(firstPane, BoxLayout.PAGE_AXIS));
        firstPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // adding options to combo box
        bodyPartComboBox.addItem("accessories");
        bodyPartComboBox.addItem("head");
        bodyPartComboBox.addItem("eyes");
        bodyPartComboBox.addItem("neck");
        bodyPartComboBox.addItem("body1");
        bodyPartComboBox.addItem("body2");
        bodyPartComboBox.addItem("hands");
        bodyPartComboBox.addItem("legs");
        bodyPartComboBox.addItem("feet");


        // Flow layout makes everything just flow into place, fucking magic!
        // on the other hand extremely messy probably should be changed...
        firstPane.add(new JLabel("name: "));
        firstPane.add(nameField);

        firstPane.add(new JLabel("body part: "));
        firstPane.add(bodyPartComboBox);

        firstPane.add(new JLabel("lower and upper temperature for clothing: "));
        // secondary pane and box layout for spinners to be next to each other, below the same applies
        JPanel realTempPane = new JPanel();
        realTempPane.setLayout(new BoxLayout(realTempPane, BoxLayout.LINE_AXIS));
        realTempPane.add(lowerRealTempBound);
        realTempPane.add(upperRealTempBound);

        // for border layout purposes every time the layout changes axis new pane made
        JPanel secondPane = new JPanel();
        secondPane.setLayout(new BoxLayout(secondPane, BoxLayout.PAGE_AXIS));
        secondPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        secondPane.add(new JLabel("lower and upper feels like temperature for clothing: "));
        secondPane.add(new JLabel("hint this metric takes thing like wind and strong sunshine into consideration"));
        JPanel feelsLikePane = new JPanel();
        feelsLikePane.setLayout(new BoxLayout(feelsLikePane, BoxLayout.LINE_AXIS));
        feelsLikePane.add(lowerFeelsLikeBound);
        feelsLikePane.add(upperFeelsLikeBound);


        JPanel thirdPane = new JPanel();
        thirdPane.setLayout(new BoxLayout(thirdPane, BoxLayout.PAGE_AXIS));
        thirdPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        thirdPane.add(new JLabel("check if water resistant: "));
        thirdPane.add(waterResistanceCheckBox);

        JPanel closingPane = new JPanel();
        closingPane.setLayout(new BoxLayout(closingPane, BoxLayout.LINE_AXIS));
        closingPane.add(okButton);
        closingPane.add(cancelButton);

        // getting everything together
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(firstPane);
        contentPane.add(realTempPane);
        contentPane.add(secondPane);
        contentPane.add(feelsLikePane);
        contentPane.add(thirdPane);
        contentPane.add(closingPane);

        okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // saving values of input to variables
                name = nameField.getText();

                givenRealTemp[0] = (Integer) lowerRealTempBound.getValue();
                givenRealTemp[1] = (Integer) upperRealTempBound.getValue();

                givenFeelsLikeTemp[0] = (Integer) lowerFeelsLikeBound.getValue();
                givenFeelsLikeTemp[1] = (Integer) upperFeelsLikeBound.getValue();

                givenBodyPart = (String) bodyPartComboBox.getSelectedItem();

                givenWaterResistance = waterResistanceCheckBox.isSelected();

                canceled = false;

                AddClothingDialog.this.setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                canceled = true;
                AddClothingDialog.this.setVisible(false);
            }
        });
    }
}
