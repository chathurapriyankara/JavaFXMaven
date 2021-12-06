package parser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name="breakfast_menu")
public class BreakfastMenu {
    private ArrayList<Food> foods = new ArrayList<>();

    public BreakfastMenu(){}

    public BreakfastMenu(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @XmlElement(name="food")
    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}
