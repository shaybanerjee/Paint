import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.geom.AffineTransform;


public class CanvasView extends JPanel implements Observer {
    private Model model;
    private int canvasWidth;
    private int canvasHeight;
    private int count;
    private JFrame fr;
    private int highestX = 577;
    private int highestY = 520;


    public CanvasView(Model model, JFrame frame)
    {
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);
        this.model = model;
        this.count = 0;
        this.fr = frame;

        //this.addMouseListener();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //super.componentResized(e);
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                //System.out.println("HERE3!");
                int mouseX = e.getX();
                int mouseY = e.getY();

                if (mouseX > getWidth())
                {
                    mouseX = getWidth();
                }
                if (mouseX < 0)
                {
                    mouseX = 0;
                }
                if  (mouseY > getHeight())
                {
                    mouseY = getHeight();
                }
                if (mouseY < 0)
                {
                    mouseY = 0;
                }
                //model.addStroke(new Point((int)(mouseX * highestX/(double)getWidth()), (int)(mouseY*highestY/(double)getHeight())));
                model.addStroke(new Point(mouseX, mouseY));
                //model.addStroke(new Point((int)(mouseX * highestX/(double)getWidth()), (int)(mouseY*highestY/(double)getHeight())));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //super.mouseReleased(e);
                //System.out.println("HERE2!");
                super.mouseReleased(e);
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (mouseX > getWidth())
                {
                    mouseX = getWidth();
                }
                if (mouseX < 0)
                {
                    mouseX = 0;
                }
                if  (mouseY > getHeight())
                {
                    mouseY = getHeight();
                }
                if (mouseY < 0)
                {
                    mouseY = 0;
                }
                model.addStroke(new Point(mouseX, mouseY));
                //model.addStroke(new Point((int)(mouseX * highestX/(double)getWidth()), (int)(mouseY*highestY/(double)getHeight())));
                model.addGroup();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                //System.out.println("HERE1!");
                //super.mouseDragged(e);
                int mouseX = e.getX();
                int mouseY = e.getY();

                //System.out.println(getHeight());
                //System.out.println(getWidth());
                //System.out.println(highestX);
                //System.out.println(highestY);
                if (mouseX > getWidth())
                {
                    mouseX = getWidth();
                    highestX = mouseX;
                }
                if (mouseX < 0)
                {
                    mouseX = 0;
                }
                if  (mouseY > getHeight())
                {
                    mouseY = getHeight();
                    highestY = mouseY;
                }
                if (mouseY < 0)
                {
                    mouseY = 0;
                }
                model.addStroke(new Point(mouseX, mouseY));
                //model.addStroke(new Point((int)(mouseX * highestX/(double)getWidth()), (int)(mouseY*highestY/(double)getHeight())));

                //
            }
        });
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //System.out.println(model.resizeVal());
        getLargestPoint();



        g2.setStroke(new BasicStroke(model.getStroke()));
        g2.setColor(model.getSelectedColor());
        List<Point> wListOfPoints = model.getPoints();

        for (int i = 1; i < wListOfPoints.size(); ++i) {
            g2.drawLine((int) wListOfPoints.get(i - 1).getX(), (int) wListOfPoints.get(i - 1).getY(), (int) wListOfPoints.get(i).getX(), (int) wListOfPoints.get(i).getY());
        }

        List<DrawingGroup> wListOfGroups = model.getGroups();

        int sliderVal = model.getSliderPos();

        double sliderPosVal = (double)sliderVal / (double)100;

        for (int i = 0; i < wListOfGroups.size(); ++i) {
            System.out.println("here");
            if ((double)i+1 <= sliderPosVal)
            {
                g2.setColor(wListOfGroups.get(i).color);
                g2.setStroke(new BasicStroke(wListOfGroups.get(i).strokeSize));
                for (int j = 1; j < wListOfGroups.get(i).points.size(); ++j) {

                    //System.out.println("Model changed!");
                    g2.drawLine((int) wListOfGroups.get(i).points.get(j - 1).getX(), (int) wListOfGroups.get(i).points.get(j - 1).getY(), (int) wListOfGroups.get(i).points.get(j).getX(), (int) wListOfGroups.get(i).points.get(j).getY());
                }
            }
            else
            {
                //System.out.println("VF");
                g2.setColor(wListOfGroups.get(i).color);
                g2.setStroke(new BasicStroke(wListOfGroups.get(i).strokeSize));
                int length = wListOfGroups.get(i).points.size();
                if (length == 1)
                {
                    for (int j = 1; j < wListOfGroups.get(i).points.size(); ++j) {

                        //System.out.println("Model changed!");
                        g2.drawLine((int) wListOfGroups.get(i).points.get(j - 1).getX(), (int) wListOfGroups.get(i).points.get(j - 1).getY(), (int) wListOfGroups.get(i).points.get(j).getX(), (int) wListOfGroups.get(i).points.get(j).getY());
                    }
                }
                else
                {

                    int val = (int)sliderPosVal;
                    //System.out.println("H: " + val);
                    //System.out.println("V: " + i);
                    double decimal = sliderPosVal - val;
                    double len = (double)length * decimal;
                    if (val == i) {
                        for (int j = 1; j < len; ++j) {
                            g2.drawLine((int) wListOfGroups.get(i).points.get(j - 1).getX(), (int) wListOfGroups.get(i).points.get(j - 1).getY(), (int) wListOfGroups.get(i).points.get(j).getX(), (int) wListOfGroups.get(i).points.get(j).getY());
                        }
                    }
                }

            }

        }
    }

    public Point getLargestPoint()
    {
        int largestX = 577;
        int largestY = 520;
        int minX = 0;
        int minY = 0;
        for (int i = 0; i < model.getGroups().size(); ++i)
        {
            List<Point> points = model.getGroups().get(i).points;
            for (int j = 0; j < points.size(); ++j)
            {
                if((int)points.get(j).getX() > highestX)
                {
                    highestX = (int)points.get(j).getX();
                }
                if ((int)points.get(j).getY() > highestY)
                {
                    highestY = (int)points.get(j).getY();
                }
            }
        }

        highestX = largestX;
        highestY = largestY;

        return new Point(highestX, highestY);
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.highestX, this.highestY);
    }

    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
        repaint();

    }
}
