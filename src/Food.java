import javafx.scene.shape.Circle;
/**
 * Food generated by the GameField for the Snake to eat and grow
 * @author Owner
 */
public class Food extends Circle
{
    private int x;
    private int y;
    
    public Food()
    {
        super(8);
        setStyle("-fx-stroke: black; -fx-fill: orange; -fx-stroke-width: 2;");
    }
    
    public Food(int x, int y)
    {
        super(8);
        setStyle("-fx-stroke: black; -fx-fill: orange; -fx-stroke-width: 2;");
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
}
