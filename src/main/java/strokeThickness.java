import javax.swing.*;
import java.awt.*;


public class strokeThickness implements Icon {
    int strokeThickness = 0;
    int x_butt = 0;
    int y_butt = 0;
    int width = 0;
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(strokeThickness));
        g2.drawLine(x_butt, y_butt, x_butt+c.getWidth(), y_butt);
    }

    public void setStrokeThickness(int thick)
    {
        strokeThickness = thick;
    }

    public void setPos(int x, int y, int w)
    {
        x_butt = x;
        y_butt = y;
        width = w;
    }





    /**
     * Returns the icon's width.
     *
     * @return an int specifying the fixed width of the icon.
     */
    public int getIconWidth() {
        return 0;
    }

    /**
     * Returns the icon's height.
     *
     * @return an int specifying the fixed height of the icon.
     */
    public int getIconHeight(){
        return 0;
    }
}
