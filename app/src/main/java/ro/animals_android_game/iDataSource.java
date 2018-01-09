package ro.animals_android_game;

import DataBase.AnimalsNode;

public interface iDataSource
{
    void insert(AnimalsNode animalsNode);
    void setIdPositiveById(int id,int newIdPositive);
    void setIdNegativeById(int id,int newIdNegative);
    void setQuestionById(int id,String question);
    int getMaxId();
    AnimalsNode getAnimalsNodeById(int id);
}
