import javax.swing.*;
import java.awt.*;

public class Dialogs {

    //dialog for adding new clothing
    public static class AddClothingDialog extends JDialog {

        // declaring variables needed to create new clothing
        String name = "";
        int[] givenRealTemp = new int[2];
        int[] givenFeelsLikeTemp = new int[2];
        String givenBodyPart = "";
        boolean givenWaterResistance;

        // GUI interface parts
        // for input
        JTextField nameField = new JTextField(20);
        JComboBox<String> bodyPartComboBox = new JComboBox<>();

        JSpinner lowerRealTempBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 5));
        JSpinner upperRealTempBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 5));

        JSpinner lowerFeelsLikeBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 5));
        JSpinner upperFeelsLikeBound = new JSpinner(new SpinnerNumberModel(0, -30, 50, 5));

        JCheckBox waterResistanceCheckBox = new JCheckBox();

        // standard closing
        JButton okButton = new JButton("ok");
        JButton cancelButton = new JButton("cancel");

        // a canceling flag
        boolean canceled = true;

        public AddClothingDialog(Frame owner, String title){
            // making the dialog
            super(owner, title);
            setBounds(100, 100, 600, 350);
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

            firstPane.add(new JLabel("temperature boundaries for clothing: "));
            // secondary pane and box layout for spinners to be next to each other, below the same applies
            JPanel realTempPane = new JPanel();
            realTempPane.setLayout(new BoxLayout(realTempPane, BoxLayout.LINE_AXIS));
            realTempPane.add(lowerRealTempBound);
            realTempPane.add(upperRealTempBound);

            // for border layout purposes every time the layout changes axis new pane made
            JPanel secondPane = new JPanel();
            secondPane.setLayout(new BoxLayout(secondPane, BoxLayout.PAGE_AXIS));
            secondPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            secondPane.add(new JLabel("feels like temperature boundaries: "));
            secondPane.add(new JLabel("hint: this metric takes takes into account e.g. wind or strong sunshine"));
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

            okButton.addActionListener(e -> {
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
            });

            cancelButton.addActionListener(e -> {
                canceled = true;
                AddClothingDialog.this.setVisible(false);
            });
        }
    }

    public static class ShowClothingDialog extends JDialog {
        JPanel mainPane = new JPanel();
        // standard closing only ok since there is no need for cancel option
        JButton okButton = new JButton("ok");
        boolean reopen = false;
        public ManageClothing.Clothing[] clothes;

        public ShowClothingDialog(Frame owner, String title, ManageClothing.Clothing[] givenClothes){
            // making the dialog
            super(owner, title);
            clothes = givenClothes;
            int numOfCloth = clothes.length;
            //System.out.println(numOfCloth);
            setBounds(100,100, 300, (numOfCloth*57));
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            Container contentPane = getContentPane();
            JPanel[] panes = new JPanel[clothes.length];
            for(int i = 0; i<clothes.length; i ++){
                ManageClothing.Clothing clothing = clothes[i];
                JPanel temporaryPane = new JPanel();
                temporaryPane.setLayout(new BoxLayout(temporaryPane, BoxLayout.LINE_AXIS));
                temporaryPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                JLabel name = new JLabel();
                name.setText(clothing.name + " ");

                JButton info = new JButton();
                info.setText("info");

                info.addActionListener(e -> {
                    ShowSpecificationDialog showSpecificationDialog = new ShowSpecificationDialog(
                            ShowClothingDialog.this,
                            "clothing information",
                            clothing);
                    showSpecificationDialog.setVisible(true);
                });

                JButton removeClothingButton = new JButton();
                removeClothingButton.setText("remove");
                removeClothingButton.addActionListener(e -> {
                    System.out.println(clothing.name);
                    ManageClothing.deleteClothing(clothing.name);
                    reopen = true;
                    ShowClothingDialog.this.setVisible(false);
                });

                temporaryPane.add(name);
                temporaryPane.add(info);
                temporaryPane.add(removeClothingButton);
                panes[i] = temporaryPane;
            }
            // adding saved clothing panes to main pane
            for (JPanel pane: panes){
                mainPane.add(pane);
            }

            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            JScrollPane scrollPane = new JScrollPane(mainPane);
            contentPane.add(scrollPane);
            contentPane.add(okButton);


            okButton.addActionListener(e -> ShowClothingDialog.this.setVisible(false));
        }

    }

    public static class ShowSpecificationDialog extends JDialog {

        JLabel nameLabel = new JLabel();
        JLabel realTempRangeLabel = new JLabel();
        JLabel feelsLikeTempRangeLabel = new JLabel();
        JLabel bodyPartLabel = new JLabel();
        JLabel waterResistanceLabel = new JLabel();
        JButton closeButton = new JButton();

        public ShowSpecificationDialog(ShowClothingDialog owner, String title, ManageClothing.Clothing clothing) {
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
            closeButton.addActionListener(e -> ShowSpecificationDialog.this.setVisible(false));

        }
    }
}
