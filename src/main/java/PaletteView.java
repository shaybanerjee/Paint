import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaletteView extends JPanel implements Observer {

    private JButton selectedColor;
    private JButton redColor;
    private JButton blueColor;
    private JButton greenColor;
    private JButton yellowColor;
    private JButton orangeColor;
    private JButton blackColor;
    private JButton custom;
    private JSlider thickness;
    private Model model;

    public PaletteView(Model model, JFrame frame)
    {
        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(9);
        gridLayout.setColumns(1);
        gridLayout.setVgap(10);
        this.setLayout(gridLayout);

        selectedColor = new JButton("SELECTED COLOR");


        redColor = new JButton("");
        blueColor = new JButton("");
        greenColor = new JButton("");
        yellowColor = new JButton("");
        orangeColor = new JButton("");
        blackColor = new JButton("");
        custom = new JButton("Custom");

        this.setBackground(Color.LIGHT_GRAY);

        //redColor.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));

        selectedColor.setMinimumSize(new Dimension(20, 20));
        redColor.setMinimumSize(new Dimension(20, 20));
        blueColor.setMinimumSize(new Dimension(20, 20));
        greenColor.setMinimumSize(new Dimension(20, 20));
        yellowColor.setMinimumSize(new Dimension(20, 20));
        orangeColor.setMinimumSize(new Dimension(20, 20));
        blackColor.setMinimumSize(new Dimension(20, 20));

        selectedColor.setPreferredSize(new Dimension(40, 40));
        redColor.setPreferredSize(new Dimension(40, 40));
        blueColor.setPreferredSize(new Dimension(40, 40));
        greenColor.setPreferredSize(new Dimension(40, 40));
        yellowColor.setPreferredSize(new Dimension(40, 40));
        orangeColor.setPreferredSize(new Dimension(40, 40));
        blackColor.setPreferredSize(new Dimension(40, 40));

        selectedColor.setMaximumSize(new Dimension(70, 70));
        redColor.setMaximumSize(new Dimension(70, 70));
        blueColor.setMaximumSize(new Dimension(70, 70));
        greenColor.setMaximumSize(new Dimension(70, 70));
        yellowColor.setMaximumSize(new Dimension(70, 70));
        orangeColor.setMaximumSize(new Dimension(70, 70));
        blackColor.setMaximumSize(new Dimension(70, 70));

        redColor.setBackground(Color.RED);
        blueColor.setBackground(Color.BLUE);
        greenColor.setBackground(Color.GREEN);
        yellowColor.setBackground(Color.YELLOW);
        orangeColor.setBackground(Color.ORANGE);
        blackColor.setBackground(Color.BLACK);
        selectedColor.setForeground(Color.WHITE);
        selectedColor.setBackground(model.getSelectedColor());
        selectedColor.setBorder(new LineBorder(Color.LIGHT_GRAY, 15));
        selectedColor.setFocusPainted(false);

        redColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.RED);
            }
        });
        blueColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.BLUE);
            }
        });
        greenColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.GREEN);
            }
        });
        yellowColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.YELLOW);
            }
        });
        orangeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.ORANGE);
            }
        });
        blackColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.updateSelectedColor(Color.BLACK);
            }
        });

        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Color newColor = JColorChooser.showDialog(frame,
                        "Choose custom color", Color.white);
                model.updateSelectedColor(newColor);
            }
        });



        JPanel vBox = new JPanel();
        GridLayout gl = new GridLayout();
        gl.setVgap(3);
        gl.setRows(4);
        gl.setColumns(1);
        vBox.setLayout(gl);
        JPanel jp = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();

        JButton thickButton1 = new JButton("");
        JButton thickButton2 = new JButton("");
        JButton thickButton3 = new JButton("");


        strokeThickness st1 = new strokeThickness();
        strokeThickness st2 = new strokeThickness();
        strokeThickness st3 = new strokeThickness();

        st1.setPos(thickButton1.getX(), thickButton1.getY(), this.getWidth());
        st2.setPos(thickButton2.getX(), thickButton2.getY(), this.getWidth());
        st3.setPos(thickButton3.getX(), thickButton3.getY(), this.getWidth());


        st1.setStrokeThickness(20);
        st2.setStrokeThickness(25);
        st3.setStrokeThickness(30);


        thickButton1.setIcon(st1);
        thickButton1.setBorder(BorderFactory.createEmptyBorder());
        thickButton1.setForeground(Color.WHITE);
        thickButton1.setBorderPainted(false);
        thickButton1.setFocusPainted(false);
        thickButton1.setContentAreaFilled(false);
        thickButton1.setOpaque(false);
        thickButton2.setIcon(st2);
        thickButton2.setBorder(BorderFactory.createEmptyBorder());
        thickButton2.setBorderPainted(false);
        thickButton2.setFocusPainted(false);
        thickButton2.setContentAreaFilled(false);
        thickButton2.setOpaque(false);
        thickButton3.setIcon(st3);
        thickButton3.setBorder(BorderFactory.createEmptyBorder());
        thickButton3.setBorderPainted(false);
        thickButton3.setFocusPainted(false);
        thickButton3.setContentAreaFilled(false);
        thickButton3.setOpaque(false);


        thickButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thickButton1.setForeground(Color.WHITE);
                thickButton2.setForeground(Color.BLACK);
                thickButton3.setForeground(Color.BLACK);
                model.setStroke(10);
            }
        });

        thickButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thickButton2.setForeground(Color.WHITE);
                thickButton1.setForeground(Color.BLACK);
                thickButton3.setForeground(Color.BLACK);
                model.setStroke(15);
            }
        });

        thickButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thickButton3.setForeground(Color.WHITE);
                thickButton1.setForeground(Color.BLACK);
                thickButton2.setForeground(Color.BLACK);
                model.setStroke(18);
            }
        });

        vBox.setBackground(Color.LIGHT_GRAY);
        JLabel title = new JLabel("Stroke Thickness: ");
        title.setHorizontalAlignment(JLabel.CENTER);
        vBox.add(title);
        vBox.add(thickButton1);
        vBox.add(thickButton2);
        vBox.add(thickButton3);


        this.model = model;


        //setLayout(new FlowLayout(FlowLayout.LEFT));
        //this.add( Box.createHorizontalGlue() );
        this.add(selectedColor);
        this.add(redColor);

        //this.add( Box.createHorizontalGlue() );
        this.add(blueColor);
        //this.add(Box.createVerticalStrut(2));
        //this.add( Box.createHorizontalGlue() );
        this.add(blackColor);
        //this.add(Box.createVerticalStrut(2));
        //this.add( Box.createHorizontalGlue() );
        this.add(yellowColor);
        //this.add(Box.createVerticalStrut(2));
        //this.add( Box.createHorizontalGlue() );
        this.add(orangeColor);
        //this.add(Box.createVerticalStrut(2));
        //this.add( Box.createHorizontalGlue() );
        this.add(greenColor);
        //this.add(Box.createVerticalStrut(2));
        //this.add(Box.createVerticalStrut(2));
        //this.add(Box.createVerticalStrut(2));
        //this.add(Box.createVerticalStrut(2));
        //this.add(Box.createVerticalStrut(2));
        //this.add(Box.createVerticalStrut(2));
        this.add(custom);
        this.add(vBox);

        //this.add(Box.createVerticalStrut(2));
        this.setBorder(new EmptyBorder(10,20,10,20));

        setVisible(true);
    }

    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
        selectedColor.setBackground(model.getSelectedColor());
    }
}