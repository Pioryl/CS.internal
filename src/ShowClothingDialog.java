import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ShowClothingDialog extends JDialog {
    JPanel mainPane = new JPanel();
    // standard closing only ok since there is no need for cancel option
    JButton okButton = new JButton("ok");
    boolean reopen = false;
    public Clothing[] clothes;

    public ShowClothingDialog(Frame owner, String title, Clothing[] givenClothes){
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
            Clothing clothing = clothes[i];
            JPanel temporaryPane = new JPanel();
            temporaryPane.setLayout(new BoxLayout(temporaryPane, BoxLayout.LINE_AXIS));
            temporaryPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            JLabel name = new JLabel();
            name.setText(clothing.name + " ");

            JButton info = new JButton();
            info.setText("info");

            info.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ShowSpecificationDialog showSpecificationDialog = new ShowSpecificationDialog(
                            ShowClothingDialog.this,
                            "clothing information",
                            clothing);
                    showSpecificationDialog.setVisible(true);
                }
            });

            JButton removeClothingButton = new JButton();
            removeClothingButton.setText("remove");
            removeClothingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(clothing.name);
                    ManageClothing.deleteClothing(clothing.name);
                    reopen = true;
                    ShowClothingDialog.this.setVisible(false);
                }
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


        okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ShowClothingDialog.this.setVisible(false);
            }
        });
    }

}
