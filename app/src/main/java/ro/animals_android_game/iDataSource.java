package ro.animals_android_game;

import DataBase.AnimalsNode;

public interface iDataSource
{
    void insert(AnimalsNode animalsNode);
    void insert(int pointer,String question,String newName,String oldName);
    void setIdPositiveById(int id,int newIdPositive);
    void setIdNegativeById(int id,int newIdNegative);
    void setQuestionById(int id,String question);
    int getMaxId();
    AnimalsNode getAnimalsNodeById(int id);
}
