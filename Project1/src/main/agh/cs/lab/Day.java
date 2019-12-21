package agh.cs.lab;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day {

    private Configuration configuration;
    private WildMap map;
    private int dayNumber;
    private int lifeLengthTillDeath;

/*    private int howManyAnimals;
    private int howManyAnimalsOnMap;
    private int howManyPlantsCounter;
    private int howManyPlantsOnMap;
    Byte[] dominantGenes = new Byte[8];
    private int howManyChildren;
    private int howManyAnimalsSoFar;
    private int moveEnergy;
    private int plantEnergy;
    private int startEnergy;*/

/*    Day(int width, int height, int startEnergy, int startAnimals, int moveEnergy, int plantEnergy, double jungleRatio){
        this.dayNumber = 0;
        this.startEnergy = startEnergy;
        this.map = new WildMap(width, height, jungleRatio);
        this.howManyPlantsCounter = 0;
        this.howManyAnimals = startAnimals;
        //this.howManyChildren = 0;
        this.howManyAnimalsSoFar = 20;
        this.moveEnergy = moveEnergy;
        for (int i = 0; i<this.howManyAnimals; i++){
            Animal animal = new Animal(map, startEnergy);
        }
        this.plantEnergy = plantEnergy;
        this.howManyPlantsCounter += map.growGrass();

        this.howManyPlantsOnMap = map.plants.size();
        this.howManyAnimalsOnMap = map.animals.size();
    }*/

    Day(Configuration configuration){
        this.configuration = configuration;
        this.map = new WildMap(configuration.width, configuration.height, configuration.jungleRatio);
        for (int i = 0; i<this.configuration.startAnimals; i++){
            Animal animal = new Animal(this.map, this.configuration.startEnergy);
        }
        for (int i = 0; i<this.configuration.starterPlants; i++){
            this.map.growGrass();
        }

    }


    void cycle () {

        dayNumber++;

        //phase of dying
        List <Animal> animalsToDie = new ArrayList<>();
        for (Animal a : map.animals) {
            if (a.energy < 1) {
                animalsToDie.add(a);
            }
        }

        for (Animal a : animalsToDie){
            a.dies(map);
        }

        //phase of movement,
        for (Animal a : map.animals){
            a.move();
            a.energy-=this.configuration.moveEnergy;
            map.objects.get(a.getPosition()).tryForChildren = false;
        }

        //phase of eating
        for (Animal a: map.animals){
            if (!this.map.noPlants.contains(a.getPosition()) && this.map.plants.contains(a.getPosition())){
                map.objects.get(a.getPosition()).animals.first().energy+=this.configuration.plantEnergy;
                //map.objects.get(a.getPosition()).grass = false;
                map.noPlants.add(a.getPosition());
                map.plants.remove(a.getPosition());
                //map.plants.remove(a.getPosition());
            }
        }

        //phase of breeding
        List <Animal> childrenParents = new ArrayList<Animal>();
        for (Animal a : map.animals){
            if (!map.objects.get(a.getPosition()).tryForChildren && map.objects.get(a.getPosition()).animals.size()>1){
                Iterator<Animal> iterator = map.objects.get(a.getPosition()).animals.iterator();
                Animal stronger = iterator.next();
                Animal weaker = iterator.next();
                if(weaker.energy >= this.configuration.startEnergy/2) {
                    childrenParents.add(stronger);
                    childrenParents.add(weaker);
                    map.objects.get(a.getPosition()).tryForChildren = true;
                }
            }
        }
        for (int i=0; i<childrenParents.size()/2; i++){
            Animal animal = new Animal(childrenParents.get(2*i), childrenParents.get(2*i + 1));
        }
        map.growGrass();
    }

    int getDayNumber(){
        return this.dayNumber;
    }

    int getHowManyAnimals(){
        return this.map.animals.size();
    }


    int getHowManyPlants(){
        return this.map.plants.size();
    }

    int getMediumEnergyLevel(){
        int sumEnergy=0;
        for (int i = 0; i < this.map.animals.size(); i++ ){
            sumEnergy+=this.map.animals.get(i).energy;
        }
        if (sumEnergy==0) return sumEnergy;
        return (sumEnergy/this.map.animals.size());
    }
/*
    int getHowManyPlantsOnMap(){
        return this.map.plants.size();
    }

    int getHowManyAnimalsOnMap(){
        return this.howManyAnimalsOnMap;
    }*/

}
