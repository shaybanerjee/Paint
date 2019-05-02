import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends JPanel implements Observer {

    private Model model;
    private JFrame mainFrame;

    public MenuView(Model model, JFrame frame)
    {
        this.setBorder(new LineBorder(Color.BLACK));
        JMenuBar menuBar = new JMenuBar();
        mainFrame = frame;
        this.setLayout(new GridLayout(1,1));
        JMenu fileOption = new JMenu("File");
        JMenu viewOption = new JMenu("View");
        fileOption.setSize(new Dimension(10,10));

        JMenuItem jmi_new = new JMenuItem("New");
        JMenuItem jmi_save = new JMenuItem("Save");
        JMenuItem jmi_load = new JMenuItem("Load");
        JMenuItem jmi_exit = new JMenuItem("Exit");



        jmi_new.setToolTipText("New");
        jmi_load.setToolTipText("Load");
        jmi_save.setToolTipText("Save");
        jmi_exit.setToolTipText("Exit");
        jmi_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getGroups().size() > 0)
                {
                    String[] options = { "Save", "Don't Save","Cancel" };
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Save changes?"), BorderLayout.CENTER);
                    int selected = JOptionPane.showOptionDialog(
                            frame, panel,"Confirmation", JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                    if (selected == 0) {
                        // Save
                        JFileChooser jfc = new JFileChooser();
                        jfc.setDialogTitle("Specify the file you want to save (.txt):");
                        jfc.setAcceptAllFileFilterUsed(false);
                        jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
                        //jfc.addChoosableFileFilter(new FileNameExtensionFilter("Binary File", "bin"));
                        int userInput = jfc.showSaveDialog(frame);
                        while(true) {
                            if (userInput == jfc.APPROVE_OPTION) {
                                File fileToSave = jfc.getSelectedFile();
                                String ext = getFileExtension(fileToSave);
                                System.out.println("Extension:" + ext);

                                if (ext.equals("txt")) {
                                    // save txt files
                                    if (fileToSave.exists()) {
                                        // prompt user that they are overwritten a drawing
                                        String[] options3 = {"Yes", "No"};
                                        JPanel panel3 = new JPanel();
                                        panel3.add(new JLabel("This file already exists! Do you want to overwrite it? "), BorderLayout.CENTER);
                                        int selected3 = JOptionPane.showOptionDialog(
                                                frame, panel3, "Confirmation", JOptionPane.YES_NO_OPTION,
                                                JOptionPane.PLAIN_MESSAGE, null, options3, options3[0]);

                                        if (selected3 == 1) {
                                            userInput = jfc.showSaveDialog(frame);
                                            continue;
                                        }
                                    }

                                    // write to file
                                    try {
                                        BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave));
                                        for (int i = 0; i < model.getGroups().size(); ++i)
                                        {
                                            out.write("" + i);
                                            out.newLine();
                                            out.write("" + model.getGroups().get(i).color.getRGB());
                                            out.newLine();
                                            out.write(""  + model.getGroups().get(i).strokeSize);
                                            out.newLine();
                                            out.write("" + model.getGroups().get(i).points.size());
                                            out.newLine();
                                            DrawingGroup currGroup = model.getGroups().get(i);
                                            for (int y = 0; y < currGroup.points.size(); ++y)
                                            {
                                                out.write((int)currGroup.points.get(y).getX() + "," + (int)currGroup.points.get(y).getY());
                                                out.newLine();
                                            }
                                        }
                                        out.close();
                                        model.clearDrawing();
                                        break;
                                    }
                                    catch (IOException err) {
                                        // handle file writing error
                                    }
                                }

                                    /*else if (ext.equals("bin")) {
                                        // save bin files
                                        try {
                                            FileOutputStream file_OS = new FileOutputStream(fileToSave.getName());
                                            ObjectOutputStream object_OS = new ObjectOutputStream(file_OS);
                                            for(int i = 0; i < model.getGroups().size(); ++i)
                                            {
                                                object_OS.writeInt(i);
                                                object_OS.writeInt(model.getGroups().get(i).points.size());
                                                for(int j = 0; j < model.getGroups().get(i).points.size(); ++j)
                                                {
                                                    object_OS.writeInt((int)model.getGroups().get(i).points.get(j).getX());
                                                    object_OS.writeInt((int)model.getGroups().get(i).points.get(j).getY());
                                                }
                                                object_OS.writeInt((int)model.getGroups().get(i).color);


                                            }

                                        }
                                        catch(IOException err)
                                        {

                                        }


                                        break;

                                    }*/ else {
                                    // invalid file extension
                                    String[] options2 = { "OK" };
                                    JPanel panel2 = new JPanel();
                                    panel2.add(new JLabel("Invalid file extension provided! Use .txt"), BorderLayout.CENTER);
                                    int selected2 = JOptionPane.showOptionDialog(
                                            frame, panel2,"Confirmation", JOptionPane.YES_OPTION,
                                            JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);

                                    userInput = jfc.showSaveDialog(frame);


                                }
                                //System.out.println(fileToSave);
                            }
                            else
                            {
                                break;
                            }

                        }
                    } else if (selected == 1) {
                        // don't save just clear
                        model.clearDrawing();
                    }
                }

            }

        });
        jmi_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getGroups().size() > 0 && model.getIsDrawn())
                {
                    String[] options = { "Save", "Don't Save" };
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Save current drawing?"), BorderLayout.CENTER);
                    int selected = JOptionPane.showOptionDialog(
                            frame, panel,"Confirmation", JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (selected == 0) {
                        // prompt user to save before loading
                        JFileChooser jfc = new JFileChooser();
                        jfc.setDialogTitle("Specify the file you want to save (.txt):");
                        jfc.setAcceptAllFileFilterUsed(false);
                        jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));
                        //jfc.addChoosableFileFilter(new FileNameExtensionFilter("Binary File", "bin"));
                        int userInput = jfc.showSaveDialog(frame);
                        while (true) {
                            if (userInput == jfc.APPROVE_OPTION) {
                                File fileToSave = jfc.getSelectedFile();
                                String ext = getFileExtension(fileToSave);

                                if (ext.equals("txt")) {
                                    // save txt files
                                    if (fileToSave.exists()) {
                                        // prompt user that they are overwritten a drawing
                                        String[] options3 = {"Yes", "No"};
                                        JPanel panel3 = new JPanel();
                                        panel3.add(new JLabel("This file already exists! Do you want to overwrite it? "), BorderLayout.CENTER);
                                        int selected3 = JOptionPane.showOptionDialog(
                                                frame, panel3, "Confirmation", JOptionPane.YES_NO_OPTION,
                                                JOptionPane.PLAIN_MESSAGE, null, options3, options3[0]);

                                        if (selected3 == 1) {
                                            userInput = jfc.showSaveDialog(frame);
                                            continue;
                                        }
                                    }

                                    // write to file
                                    try {
                                        BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave));
                                        for (int i = 0; i < model.getGroups().size(); ++i) {
                                            out.write("" + i);
                                            out.newLine();
                                            out.write("" + model.getGroups().get(i).color.getRGB());
                                            out.newLine();
                                            out.write("" + model.getGroups().get(i).strokeSize);
                                            out.newLine();
                                            out.write("" + model.getGroups().get(i).points.size());
                                            out.newLine();
                                            DrawingGroup currGroup = model.getGroups().get(i);
                                            for (int y = 0; y < currGroup.points.size(); ++y) {
                                                out.write((int) currGroup.points.get(y).getX() + "," + (int) currGroup.points.get(y).getY());
                                                out.newLine();
                                            }
                                        }
                                        out.close();
                                        model.save();
                                        break;
                                    } catch (IOException err) {
                                        // handle file writing error
                                    }
                                } else {
                                    // invalid file extension
                                    String[] options2 = {"OK"};
                                    JPanel panel2 = new JPanel();
                                    panel2.add(new JLabel("Invalid file extension provided! Use .txt"), BorderLayout.CENTER);
                                    int selected2 = JOptionPane.showOptionDialog(
                                            frame, panel2, "Confirmation", JOptionPane.YES_OPTION,
                                            JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);

                                    userInput = jfc.showSaveDialog(frame);


                                }
                                System.out.println(fileToSave);
                            } else {
                                break;
                            }
                        }
                    }
                }

                JFileChooser jfc = new JFileChooser();
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("text files", "txt"));
                int returnValue = jfc.showOpenDialog(null);
                File sFile = jfc.getSelectedFile();
                while(true)
                {
                    if (sFile == null)
                    {
                        break;
                    }
                    if (!sFile.exists())
                    {
                        String[] options5 = { "OK" };
                        JPanel panel5 = new JPanel();
                        panel5.add(new JLabel("Could not find file!"), BorderLayout.CENTER);
                        int selected2 = JOptionPane.showOptionDialog(
                                frame, panel5,"Confirmation", JOptionPane.YES_OPTION,
                                JOptionPane.PLAIN_MESSAGE, null, options5, options5[0]);
                        returnValue = jfc.showOpenDialog(null);
                        sFile = jfc.getSelectedFile();
                    }
                    else
                    {
                        break;
                    }

                }
                if (returnValue == JFileChooser.APPROVE_OPTION) {


                    try{
                        BufferedReader br = new BufferedReader(new FileReader(sFile));
                        System.out.println(br.lines());
                        String cLine;
                        List<DrawingGroup> wListDrawing = new ArrayList<>();
                        DrawingGroup currDraw = new DrawingGroup();
                        System.out.println("here");
                        while((cLine = br.readLine()) != null)
                        {
                            System.out.println("here");
                            currDraw = new DrawingGroup();
                            currDraw.points = new ArrayList<>();
                            cLine = br.readLine();
                            currDraw.color = new Color(Integer.parseInt(cLine));
                            cLine = br.readLine();
                            currDraw.strokeSize = Integer.parseInt(cLine);
                            cLine = br.readLine();
                            int pointSize = Integer.parseInt(cLine);

                            for (int i = 0; i < pointSize; ++i)
                            {
                                cLine = br.readLine();
                                String[] wArr = cLine.split(",");
                                int x = Integer.parseInt(wArr[0]);
                                int y = Integer.parseInt(wArr[1]);

                                Point p = new Point(x, y);
                                currDraw.points.add(p);
                            }

                            System.out.println(currDraw.strokeSize);
                            System.out.println(currDraw.points.size());

                            wListDrawing.add(currDraw);

                        }
                        model.loadGroup(wListDrawing);
                        model.save();
                    }
                    catch(IOException err)
                    {

                    }


                }

            }
        });
        jmi_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Specify the file you want to save (.txt):");
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));

                //jfc.addChoosableFileFilter(new FileNameExtensionFilter("Binary File", "bin"));
                int userInput = jfc.showSaveDialog(frame);
                while(true) {
                    if (userInput == jfc.APPROVE_OPTION) {
                        File fileToSave = jfc.getSelectedFile();
                        String ext = getFileExtension(fileToSave);

                        if (ext.equals("txt")) {
                            // save txt files
                            if (fileToSave.exists()) {
                                // prompt user that they are overwritten a drawing
                                String[] options3 = {"Yes", "No"};
                                JPanel panel3 = new JPanel();
                                panel3.add(new JLabel("This file already exists! Do you want to overwrite it? "), BorderLayout.CENTER);
                                int selected3 = JOptionPane.showOptionDialog(
                                        frame, panel3, "Confirmation", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.PLAIN_MESSAGE, null, options3, options3[0]);

                                if (selected3 == 1) {
                                    userInput = jfc.showSaveDialog(frame);
                                    continue;
                                }
                            }

                            // write to file
                            try {
                                BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave));
                                for (int i = 0; i < model.getGroups().size(); ++i)
                                {
                                    out.write("" + i);
                                    out.newLine();
                                    out.write("" + model.getGroups().get(i).color.getRGB());
                                    out.newLine();
                                    out.write(""  + model.getGroups().get(i).strokeSize);
                                    out.newLine();
                                    out.write("" + model.getGroups().get(i).points.size());
                                    out.newLine();
                                    DrawingGroup currGroup = model.getGroups().get(i);
                                    for (int y = 0; y < currGroup.points.size(); ++y)
                                    {
                                        out.write((int)currGroup.points.get(y).getX() + "," + (int)currGroup.points.get(y).getY());
                                        out.newLine();
                                    }
                                }
                                out.close();
                                model.save();
                                break;
                            }
                            catch (IOException err) {
                                // handle file writing error
                            }
                        }
                        else {
                            // invalid file extension
                            String[] options2 = { "OK" };
                            JPanel panel2 = new JPanel();
                            panel2.add(new JLabel("Invalid file extension provided! Use .txt"), BorderLayout.CENTER);
                            int selected2 = JOptionPane.showOptionDialog(
                                    frame, panel2,"Confirmation", JOptionPane.YES_OPTION,
                                    JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);

                            userInput = jfc.showSaveDialog(frame);


                        }
                        System.out.println(fileToSave);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        });
        jmi_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileOption.add(jmi_new);
        fileOption.add(jmi_load);
        fileOption.add(jmi_save);
        fileOption.add(jmi_exit);

        menuBar.add(fileOption);
        menuBar.add(viewOption);
        this.add(menuBar);
        this.setVisible(true);



    }


    private static String getFileExtension(File file) {
        if (file.getName().lastIndexOf(".") != -1 && file.getName().lastIndexOf(".") != 0) {
            return file.getName().substring(file.getName().lastIndexOf(".") + 1);
        }
        return "";
    }



    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
    }
}