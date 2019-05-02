


import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private Color selectedColor = Color.BLACK;
    private List<Point> drawing;
    private List<DrawingGroup> wDrawGroup;
    private int strokeSize = 10;
    private boolean isDrawn = false;
    private boolean isResize = false;
    private boolean isSlid = false;
    int slider_position = 0;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
        this.drawing = new ArrayList<>();
        this.wDrawGroup = new ArrayList<>();

    }

    public void addGroup()
    {
        DrawingGroup dg = new DrawingGroup();
        dg.points = drawing;
        dg.color = selectedColor;
        dg.strokeSize = strokeSize;

        isSlid = false;
        if (slider_position == 0)
        {
            wDrawGroup = new ArrayList<>();
            this.drawing = new ArrayList<>();
            wDrawGroup.add(dg);
            notifyObservers();
        }
        else if (slider_position % 100 != 0)
        {
            double val = (double)slider_position / (double)100;
            int index = (int)val;
            double decimal = val - index;
            List<Point> wNewPoints = new ArrayList<>();
            int len = wDrawGroup.get(index).points.size();
            double new_len = len * decimal;



            for (int i = 0; i < new_len; ++i)
            {
                wNewPoints.add(wDrawGroup.get(index).points.get(i));
            }
            wDrawGroup.get(index).points = wNewPoints;
            List<DrawingGroup> newGroup = new ArrayList<>();
            for(int i = 0; i < index+1; ++i)
            {
                newGroup.add(wDrawGroup.get(i));
            }
            wDrawGroup = newGroup;
        }

        this.drawing = new ArrayList<>();
        wDrawGroup.add(dg);
        notifyObservers();
    }

    int getStroke()
    {
        return strokeSize;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
        observer.update(this);
    }

    public int getSliderSize()
    {
        return getGroups().size() * 100;
    }

    public void clearDrawing()
    {
        wDrawGroup = new ArrayList<>();
        notifyObservers();
    }

    public void setSlider()
    {
        isSlid = true;
        notifyObservers();
    }

    public int getSliderPos() {
        return slider_position;
    }

    public void setSliderPos(int pos)
    {
        slider_position = pos;
        notifyObservers();
    }

    public boolean isSlidVal()
    {
        return isSlid;
    }
    public void addStroke(Point point)
    {
        isDrawn = true;
        this.drawing.add(point);
        notifyObservers();
    }



    public void setStroke(int stroke)
    {
        strokeSize = stroke;
    }
    public void updateSelectedColor(Color color)
    {
        selectedColor = color;
        notifyObservers();
    }

    public List<Point> getPoints()
    {
        return drawing;
    }


    public Color getSelectedColor()
    {
        return selectedColor;
    }

    public List<DrawingGroup> getGroups()
    {
        return wDrawGroup;
    }

    public void loadGroup(List<DrawingGroup> wGroup)
    {
        wDrawGroup = wGroup;
        notifyObservers();
    }

    public void save()
    {
        isDrawn = false;
    }

    public void setResizeOff()
    {
        isResize = false;
    }

    public boolean resizeVal()
    {
        return isResize;
    }






    public boolean getIsDrawn()
    {
        return isDrawn;
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}
