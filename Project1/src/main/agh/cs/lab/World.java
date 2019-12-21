package agh.cs.lab;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private static final String startUpData = "parameters.json";

    public static void main(String args[]) throws FileNotFoundException {
        try {
            /*Day day = new Day(40, 30, 40, 2, 30, 0.5);
            System.out.println("Day: " +day.getDayNumber());
            System.out.println("Animals: " +day.getHowManyAnimals());
            //System.out.println("Animals on map: " +day.getHowManyAnimalsOnMap());
            System.out.println("Plants: " +day.getHowManyPlants());
            //System.out.println("Plants on map: " +day.getHowManyPlantsOnMap());

            while (day.getDayNumber()<1000){
                day.cycle();
                System.out.println("Day: " +day.getDayNumber());
                System.out.println("Animals: " +day.getHowManyAnimals());
                //System.out.println("Animals on map: " +day.getHowManyAnimalsOnMap());
                System.out.println("Plants: " +day.getHowManyPlants());
               // System.out.println("Plants on map: " +day.getHowManyPlantsOnMap());
            }*/

            final Configuration configuration  = Configuration.fromJson(World.startUpData);
            Day day = new Day(configuration);
            for (int i = 0; i < configuration.timeRunning; i++){
                day.cycle();
                System.out.println("Day: " +day.getDayNumber());
                System.out.println("Animals: " +day.getHowManyAnimals());
                System.out.println("Plants: " +day.getHowManyPlants());
                System.out.println("Medium energy: " +day.getMediumEnergyLevel());
            }


        } catch (FileNotFoundException ex){
            System.out.println("I can't start without a file!");
        }

        catch (IllegalArgumentException ex){
            System.out.println("Illegal arguments! Check json file " + ex.toString());

        }

    }
}
