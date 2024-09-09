package vehicleClasses;

import java.io.Serializable;

/**Класс координат*/
public class Coordinates implements Serializable {
    private int x; //Значение поля должно быть больше -971
    private double y;

    public Coordinates (int x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Устанавливает значение координаты X
     * @param x координата x
     */
    public void setX(int x){
        this.x = x > -971 ? x : this.x;
    }

    /**
     * Устанавливает значение координаты Y
     * @param y координата y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Возвращает значение координаты X
     * @return координату x
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает значение координаты Y
     * @return координату y
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает объект типа Coordinates в виде строки
     * @return описание объекта
     */
    @Override
    public String toString() {
        return "coordinates (x, y) = (" + getX() + ", " + getY() + ")";
    }
}