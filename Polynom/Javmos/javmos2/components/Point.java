package javmos2.components;

import javmos2.enums.RootType;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import javmos2.JavmosGUI;

public class Point extends JavmosComponent {

    public final int RADIUS = 5;
    private final RootType type;
    private double x;
    private double y;
    public java.awt.geom.Ellipse2D.Double point;

    public Point(JavmosGUI gui, RootType type, double x, double y) {
        super(gui);
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public RootType getRootType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final Point other = (Point) object;

        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x);
    }

    public int compareTo(Point point) {
        if (x > point.getX()) {
            return 1;
        } else if (x < point.getX()) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return getRootType() + " (" + x + ", " + y + ")";
    }

    //Listens to points clicked (Connected to PointClickedListener)
    public Ellipse2D.Double getPoint() {
        double xA = (gui.getPlaneWidth() / 2) + x * gui.getZoom() / gui.getDomainStep();
        double yA = (gui.getPlaneWidth() / 2) + y * -gui.getZoom() / gui.getRangeStep();
        point = new Ellipse2D.Double(xA - 4, yA - 4, 8, 8);
        return point;
    }

    @Override
    public void draw(java.awt.Graphics2D graphics2D) {
        if (x <= gui.getMaxDomain() && x >= gui.getMinDomain() && y <= gui.getMaxRange() && y >= gui.getMinRange()) {
            graphics2D.setStroke(new BasicStroke(8));
            graphics2D.setColor(type.getPointColor());
            graphics2D.draw(getPoint());
        }
    }
}
