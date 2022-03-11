import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class IntroPage {
    static void makeAFrame() {
        int width = 400;
        int height = 300;
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel = frame.getContentPane();
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println(dimension);
        int x = (int) (dimension.getWidth())/2 - (width/2);
        int y = (int) (dimension.getHeight())/2 - (height/2);
        frame.setBounds(x, y, width, height);

        text = new JLabel("", SwingConstants.CENTER);
        text.setBounds(0, 0, width, 150);
        panel.add(text);

        text2 = new JLabel("", SwingConstants.CENTER);
        text2.setBounds(0, 0, width, 180);
        panel.add(text2);

        text3 = new JLabel("", SwingConstants.CENTER);
        text3.setBounds(0, 0, width, 210);
        panel.add(text3);
    }

    static int windowNum = 0;
    static JLabel text;
    static JLabel text2;
    static JLabel text3;
    static JFrame frame;
    static Container panel;

    static void next() {
        windowNum++;

        frame.dispose();
        doEverything();
    }
    static void doEverything() {


        makeAFrame();
        if (windowNum == 0){
            text.setText("This is the outfit creator");

            JButton Instructions = new JButton();
            Instructions.setText("Instructions");
            Instructions.setBounds(65, 120, 120, 70);
            panel.add(Instructions);
            Instructions.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });

            JButton start = new JButton();
            start.setText("START");
            start.setBounds(215, 120, 120, 70);
            panel.add(start);
            start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ClothesGUI.run();
                    frame.dispose();
                }
            });
        }

        if (windowNum == 1){

            text.setText("this is how the program works 1");

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(75, 120, 100, 70);
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = -1;
                    next();
                }
            });

            JButton next = new JButton();
            next.setText("NEXT");
            next.setBounds(225, 120, 100, 70);
            panel.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });

        }

        if (windowNum == 2){
            text.setText("this is how the program works 2");

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(75, 120, 100, 70);
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = 0;
                    next();
                }
            });

            JButton next = new JButton();
            next.setText("NEXT");
            next.setBounds(225, 120, 100, 70);
            panel.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
        }

        if (windowNum == 3){
            text.setText("this is how the program works last");

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(75, 120, 100, 70);
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = 1;
                    next();
                }
            });

            JButton start = new JButton();
            start.setText("START");
            start.setBounds(225, 120, 100, 70);
            panel.add(start);
            start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ClothesGUI.run();
                    frame.dispose();
                }
            });

        }
        /*
        if (windowNum == 4){

            if (option == "sport"){
                add = "(ubranie sportowe)";
                if (rain == "yes") {
                    add = "(ubranie sportowe) and a rain jacket";
                }

            }
            if (option == "school") {;
                add = "(ubranie zwykle)";
                if (rain == "yes") {
                    add = "(ubranie zwykle) and an umbrella";
                }
            }
            if (option == "elegant") {
                add = "(ubranie eleganckie)";
                if (rain == "yes") {
                    add = "(ubranie eleganckie) and an umbrella";
                }
            }
            if (option == "party!") {
                add = "(ubranie imprezowe)";
                if (rain == "yes") {
                    add = "(ubranie imprezowe) and a rain jacket";
                }
            }

            text.setText("For this type of activity: " + option + ",");
            text2.setText("we recommend " + add + ".");
            text3.setText("Remember it's " + tempr + " degrees outside!");

            JButton ok = new JButton("NEXT");
            ok.setText("NEXT");
            ok.setBounds(100, 150, 200, 70);
            panel.add(ok);

            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });

        }
        if (windowNum == 5){
            text.setText("Thank you for choosing our services!");
            text2.setText("Btw you look good today.");

            JButton close= new JButton();
            close.setText("Close program");
            close.setBounds(100, 150, 200, 70);
            panel.add(close);
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
        }
*/
    }
    public static void main(String[] args) {
        doEverything();
    }


}
