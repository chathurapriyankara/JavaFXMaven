package parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class DataProcessor {
    public ArrayList<Food> findFoodByName(String name) {
        ArrayList<Food> foods = getFoodData();
        ArrayList<Food> results = new ArrayList<>();
        for (Food food : foods) {
            if (food.getName().contains(name)) {
                results.add(food);
            }
        }
        return results;
    }

    private ArrayList<Food> getFoodData() {
        ArrayList<Food> foods = new ArrayList<>();
        try {
            JAXBContext context = JAXBContext.newInstance(BreakfastMenu.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileInputStream fileInputStream = new FileInputStream("src/main/java/parser/simple.xml");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BreakfastMenu breakfastMenu = (BreakfastMenu) unmarshaller.unmarshal(inputStreamReader);
            foods = breakfastMenu.getFoods();
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return foods;
    }

    public String marshallFood(ArrayList<Food> foods) {
        BreakfastMenu breakfastMenu = new BreakfastMenu(foods);
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(BreakfastMenu.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(breakfastMenu, stringWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}
