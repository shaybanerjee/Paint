
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame implements Observer {

    private Model model;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.

    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Model changed!");
    }
}
