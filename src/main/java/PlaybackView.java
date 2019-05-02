import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

public class PlaybackView extends JPanel implements Observer {

    private Model model;
    private JSlider slider;
    private JButton play;
    private JButton rewind;


    public PlaybackView(Model model)
    {
        this.setBorder(new LineBorder(Color.BLACK));
        this.setLayout(new GridLayout(1,5));

        slider = new JSlider(JSlider.HORIZONTAL, 0, 0);
        slider.setBackground(Color.WHITE);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(100);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setSlider();
                model.setSliderPos(slider.getValue());
                //System.out.println(slider.getValue());
            }
        });
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                model.setSliderPos(slider.getValue());

                //System.out.println(slider.getValue());
                //System.out.println(slider.getValue());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                model.setSliderPos(slider.getValue());

            }
        });
        play = new JButton("");
        play.setBackground(Color.WHITE);
        play.setEnabled(false);
        rewind = new JButton("");
        rewind.setBackground(Color.WHITE);
        rewind.setEnabled(false);


        try {
            Image imgVal = ImageIO.read(new FileInputStream("images/play.png"));
            play.setIcon(new ImageIcon(imgVal));
            Image imgVal2 = ImageIO.read(new FileInputStream("images/rewind.png"));
            rewind.setIcon(new ImageIcon(imgVal2));
        }
        catch (Exception e)
        {
            System.out.println("ERROR: PlaybackView");
        }


        play.addMouseListener(new MouseAdapter() {
            Timer t;
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                //play.setEnabled(false);

                t = new Timer(0, new ActionListener() {
                    int currVal = model.getSliderPos();
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        model.setSliderPos(currVal);
                        slider.setValue(currVal);
                        if (currVal >= slider.getMaximum())
                        {
                            t.stop();
                        }
                        ++currVal;
                    }
                });

                t.start();
                rewind.setEnabled(true);
                play.setEnabled(false);
            }
        });


        rewind.addMouseListener(new MouseAdapter() {
            Timer t;
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);

                t = new Timer(0, new ActionListener() {
                    int currVal = model.getSliderPos();
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        model.setSliderPos(currVal);
                        slider.setValue(currVal);
                        if (currVal <= slider.getMinimum())
                        {
                            t.stop();
                        }
                        --currVal;
                    }
                });

                t.start();
                rewind.setEnabled(false);
                play.setEnabled(true);
            }
        });


        this.add(slider);
        this.add(play);
        this.add(rewind);

        this.model = model;
    }


    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
        if (model.isSlidVal() == false)
        {
            //System.out.println("here");
            // init or reinit slider
            if (slider.getMaximum() != 0) {
                this.play.setEnabled(false);
                this.rewind.setEnabled(true);
            }
            slider.setMaximum(model.getSliderSize());
            slider.setValue(model.getSliderSize());
        }
        if (model.getSliderPos() == slider.getMaximum() && model.getSliderPos() != 0 )
        {
            this.play.setEnabled(false);
            this.rewind.setEnabled(true);
        }
        else if (model.getSliderPos() == slider.getMinimum() && slider.getMaximum() != 0)
        {
            this.rewind.setEnabled(false);
            this.play.setEnabled(true);
        }
        else if (model.getSliderPos() != 0)
        {
            this.rewind.setEnabled(true);
            this.play.setEnabled(true);
        }



    }
}


