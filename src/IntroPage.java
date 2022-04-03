import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class IntroPage {

    static int windowNum = 0;
    static JLabel text;
    static JLabel text2;
    static JLabel text3;
    static JLabel text4;
    static JFrame frame;
    static Container panel;

    static void makeAFrame() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)dimension.getWidth();
        int height = (int)dimension.getHeight();
        frame = new JFrame("Intro Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel = frame.getContentPane();
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);


        //System.out.println(dimension);
        int x = (int) (dimension.getWidth())/2 - (width/2);
        int y = (int) (dimension.getHeight())/2 - (height/2);
        frame.setBounds(x, y, width, height);

        text = new JLabel("", SwingConstants.CENTER);
        text.setBounds(0, 0, width, 150);
        text.setFont(new Font("Rockwell", Font. BOLD, 40));
        panel.add(text);

        text2 = new JLabel("", SwingConstants.CENTER);
        text2.setBounds(0, 0, width, 250);
        text2.setFont(new Font("Rockwell", Font. PLAIN, 30));
        panel.add(text2);

        text3 = new JLabel("", SwingConstants.CENTER);
        text3.setBounds(0, 0, width, 350);
        text3.setFont(new Font("Rockwell", Font. PLAIN, 30));
        panel.add(text3);

        text4 = new JLabel("", SwingConstants.CENTER);
        text4.setBounds(0, 0, width, 450);
        text4.setFont(new Font("Rockwell", Font. PLAIN, 30));
        panel.add(text4);
    }

    static void next() {
        windowNum++;
        frame.dispose();
        doEverything();
    }

    static void doEverything() {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)dimension.getWidth();
        int height = (int)dimension.getHeight();
        int sizeX = 250;
        int sizeY = 100;
        int W1 = width/3 - sizeX/2;
        int H = 4*height/5 - sizeY/2;
        int W2 = 2*width/3 - sizeX/2;
        makeAFrame();
        if (windowNum == 0){

            text2.setText("This is the outfit creator");
            text2.setFont(new Font("Serif", Font. BOLD, 60));


            JButton Instructions = new JButton();
            Instructions.setText("Instructions");
            Instructions.setBounds(W2, H, sizeX, sizeY);
            Instructions.setFont(new Font("Serif", Font. BOLD, 40));

            panel.add(Instructions);
            Instructions.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });


            JButton start = new JButton();
            start.setText("START");
            start.setBounds(W1, H, sizeX, sizeY);
            start.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(start);
            start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ClothesGUI.run();
                    windowNum = 1;
                    frame.dispose();
                }
            });
        }

        if (windowNum == 1){

            text.setText("How to create an outfit?");
            text2.setText("This is what the main page of the outfit generator will look like");
            text3.setText("Begin with imputing your current location at the top of the page (Warsaw, PL by default)");
            text4.setText("Then proceed by pressing the red button");

//adding ClothingGUI image
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("ClothesGUI.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel img1 = new JLabel(new ImageIcon(myPicture));
            int imgW = 642;
            int imgH = 339;
            img1.setBounds(width/2 - imgW/2,height/2 - imgH/2,imgW,imgH);
            panel.add(img1);


            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(W1, H, sizeX, sizeY);
            back.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = -1;
                    next();
                }
            });

            JButton next = new JButton();
            next.setText("NEXT");
            next.setBounds(W2, H, sizeX, sizeY);
            next.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });

        }

        if (windowNum == 2){
            text.setText("After pressing the update button ");
            text2.setText("The current weather conditions, including real and feels-like temperature");
            text3.setText("and current atmospheric conditions, will be visible on the left");
            text4.setText("The newly generated outfit, with every item, will be visible on the right");

    //adding updated ClothingGUI image
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("updatedClothes GUI.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel img1 = new JLabel(new ImageIcon(myPicture));
            int imgW = 642;
            int imgH = 339;
            img1.setBounds(width/2 - imgW/2,height/2 - imgH/2,imgW,imgH);
            panel.add(img1);

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(W1, H, sizeX, sizeY);
            back.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = 0;
                    next();
                }
            });

            JButton next = new JButton();
            next.setText("NEXT");
            next.setBounds(W2, H, sizeX, sizeY);
            next.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
        }

        if (windowNum == 3){
            text.setText("Adding more clothes");
            text2.setText("To add a new clothing item press the button on the bottom left.");
            text3.setText("Then input the required information in the new window.");
            text4.setText("Press \"ok\" to save and return to main page.");

     //adding clothing image
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("addingClothes.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel img1 = new JLabel(new ImageIcon(myPicture));
            int imgW = 459;
            int imgH = 285;
            img1.setBounds(width/2 - imgW/2,height/2 - imgH/2,imgW,imgH);
            panel.add(img1);

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(W1, H, sizeX, sizeY);
            back.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = 1;
                    next();
                }
            });

            JButton next = new JButton();
            next.setText("NEXT");
            next.setBounds(W2, H, sizeX, sizeY);
            next.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(next);
            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });

        }

        if (windowNum == 4){
            text.setText("Wardrobe view and removing clothes");
            text2.setText("To view all clothes currently added to the program press the button on the bottom right");
            text3.setText("A list will appear with two options beside each clothing item");
            text4.setText("To see an items info press \"info\" and to delete it press \"remove\"");

     //adding Wardrobe image
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("Wardrobe.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel img1 = new JLabel(new ImageIcon(myPicture));
            int imgW = 342;
            int imgH = 540;
            img1.setBounds(width/2 - imgW/2,height/2 - imgH/2,imgW,imgH);
            panel.add(img1);

            JButton back = new JButton();
            back.setText("BACK");
            back.setBounds(W1, H, sizeX, sizeY);
            back.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(back);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    windowNum = 2;
                    next();
                }
            });

            JButton start = new JButton();
            start.setText("START");
            start.setBounds(W2, H, sizeX, sizeY);
            start.setFont(new Font("Serif", Font. BOLD, 40));
            panel.add(start);
            start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ClothesGUI.run();
                    windowNum = 1;
                    frame.dispose();
                }
            });

        }

    }

    public static void main(String[] args) {
        doEverything();
    }

}
