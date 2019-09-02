package com.mario.gfx.frames;

import com.mario.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OptionFrame extends JFrame {

    public OptionFrame(){
        super("Choose world");
        setSize(1350,735);//Set Frame dimensions to 400 width and 400 height
        setResizable(false);
        setLocationRelativeTo(null);//Center the frame on the screen.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Make the program Abort When user closes the Frame.
        setVisible(true);
        pack();
        try {
            JPanel container = new JPanelWithBackground("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\wallpaper2.jpg");
            ActionListener start0Listener = e -> {
                setVisible(false);
                Game game=new Game(0);
                JFrame frame= new JFrame("Mario");
                frame.add(game, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                game.start();
            };
            ActionListener start1Listener = e -> {
                setVisible(false);
                Game game=new Game(1);
                JFrame frame= new JFrame("Mario");
                frame.add(game, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                game.start();
            };
            ActionListener start2Listener = e -> {
                setVisible(false);
                Game game=new Game(2);
                JFrame frame= new JFrame("Mario");
                frame.add(game, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                game.start();
            };
            ActionListener start3Listener = e -> {
                setVisible(false);
                Game game=new Game(3);
                JFrame frame= new JFrame("Mario");
                frame.add(game, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                game.start();
            };

            JPanel imageOptionsPanel = new JPanel();
            imageOptionsPanel.setBorder(new LineBorder(Color.blue));
            JLabel startLbl = new JLabel("choose world to play with :");//Create Button with Text.
            startLbl.setFont(new Font("Forte",Font.PLAIN,35));
            startLbl.setForeground(Color.black);
            startLbl.setBackground(Color.blue);
            imageOptionsPanel.add(startLbl);//Add buttons to Panel.
            //container.add(imageOptionsPanel);//Add imageOptions Panel to Container Panel.

            JButton start0Btn = new JButton();
            BufferedImage image0 = ImageIO.read(new File("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\b1.jpg"));
            start0Btn.setIcon(new ImageIcon(image0));
            start0Btn.addActionListener(start0Listener);

            JButton start1Btn = new JButton();
            BufferedImage image1 = ImageIO.read(new File("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\b2.jpg"));
            start1Btn.setIcon(new ImageIcon(image1));
            start1Btn.addActionListener(start1Listener);

            JButton start2Btn = new JButton();
            BufferedImage image2 = ImageIO.read(new File("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\b3.png"));
            start2Btn.setIcon(new ImageIcon(image2));
            start2Btn.addActionListener(start2Listener);

            JButton start3Btn = new JButton();
            BufferedImage image3 = ImageIO.read(new File("C:\\Users\\peleg\\IdeaProjects\\Mario\\res\\b4.jpg"));
            start3Btn.setIcon(new ImageIcon(image3));
            start3Btn.addActionListener(start3Listener);

            //container.setBorder(new EmptyBorder(700,0,0,0));
            //container.setLayout(new BorderLayout());

            JPanel btnPanel = new JPanel();
            btnPanel.setBackground(Color.CYAN);
            btnPanel.setBorder(new LineBorder(Color.blue));

            btnPanel.add(start0Btn);//Add Button.
            btnPanel.add(start1Btn);//Add Button.
            btnPanel.add(start2Btn);//Add Button.
            btnPanel.add(start3Btn);//Add Button.
            btnPanel.setPreferredSize(new Dimension(790,720));

            imageOptionsPanel.setBounds(320,10,200,50);
            btnPanel.setBounds(50,65,800,740);

            container.add(imageOptionsPanel);
            container.add(btnPanel);

            add(container);
            pack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Create Main Frame with Buttons and assign actions to buttons.
}
