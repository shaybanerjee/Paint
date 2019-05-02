import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Model model = new Model();
        frame.setTitle("A2: MVC in Java - Shayon Banerjee");
        //View view = new View(model);
        PaletteView paletteView = new PaletteView(model, frame);
        MenuView menuView = new MenuView(model, frame);
        CanvasView canvasView = new CanvasView(model, frame);
        PlaybackView playbackView = new PlaybackView(model);
        model.addObserver(paletteView);
        model.addObserver(menuView);
        model.addObserver(canvasView);
        model.addObserver(playbackView);

        JPanel pane = new JPanel(new GridBagLayout());
        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();




        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.ipady = 0;
        pane.add(menuView, c);


        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx= 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        c.weighty = 0.5;
        pane.add(paletteView, c);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx= 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx  = 0.9;
        c.weighty = 0.5;
        pane.add(canvasView, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 0;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.weighty = 0.0;

        pane.add(playbackView, c);



        frame.add(pane);
        //frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
        frame.setPreferredSize( new Dimension(800, 900) );
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}



