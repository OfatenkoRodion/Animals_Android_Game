package ro.animals_android_game;


import DataBase.AnimalsDB;
import DataBase.AnimalsNode;

public class FakeDataForDB
{
    public static void load(AnimalsDB animalsDB)
    {
        AnimalsNode newAnimalNode = new AnimalsNode();
        //1
        newAnimalNode.setQuestion("Обитает на суше?").setId(1).setIdPositive(2).setIdNegative(9);
        animalsDB.insert(newAnimalNode);
        //2
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setQuestion("Живет в квартире или доме вместе с людьми?").setId(2).setIdPositive(3).setIdNegative(6);
        animalsDB.insert(newAnimalNode);
        //3
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setQuestion("Может мурчать?").setId(3).setIdPositive(4).setIdNegative(5);
        animalsDB.insert(newAnimalNode);
        //4
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Кот").setId(4);
        animalsDB.insert(newAnimalNode);
        //5
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Собака").setId(5);
        animalsDB.insert(newAnimalNode);
        //6
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setQuestion("Дает молоко?").setId(6).setIdPositive(7).setIdNegative(8);
        animalsDB.insert(newAnimalNode);
        //7
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Корова").setId(7);
        animalsDB.insert(newAnimalNode);
        //8
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Конь").setId(8);
        animalsDB.insert(newAnimalNode);
        //9
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setQuestion("Дышит жабрами?").setId(9).setIdPositive(10).setIdNegative(11);
        animalsDB.insert(newAnimalNode);
        //10
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Акула").setId(10);
        animalsDB.insert(newAnimalNode);
        //11
        newAnimalNode = new AnimalsNode();
        newAnimalNode.setName("Дельфин").setId(11);
        animalsDB.insert(newAnimalNode);
    }
}
