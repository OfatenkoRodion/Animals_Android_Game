package ro.animals_android_game;

import DataBase.AnimalsNode;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class Presenter
{
    private iDataSource dataSource;
    private AnimalsNode currentAnimalsNode;
    private int pointer=1;

    private BehaviorSubject<String> behaviorSubjectQuestions;
    private BehaviorSubject<String> behaviorSubjectNames;

    public Presenter(iDataSource dataSource)
    {
        this.dataSource=dataSource;
        behaviorSubjectQuestions = BehaviorSubject.create();
        behaviorSubjectNames = BehaviorSubject.create();
    }

    public void returnToBegin()
    {
        pointer=1;
        getCurrentAnimalsNodeByPointer();
        behaviorSubjectQuestions.onNext(currentAnimalsNode.getQuestion());
    }

    public void makePositiveStep()
    {
        pointer=currentAnimalsNode.getIdPositive();
        getCurrentAnimalsNodeByPointer();

        if(currentAnimalsNode.getIdPositive()!=-1)
            behaviorSubjectQuestions.onNext(currentAnimalsNode.getQuestion());
        else
            behaviorSubjectNames.onNext(currentAnimalsNode.getName());

    }

    public void makeNegativeStep()
    {
        pointer=currentAnimalsNode.getIdNegative();
        getCurrentAnimalsNodeByPointer();

        if(currentAnimalsNode.getIdNegative()!=-1)
            behaviorSubjectQuestions.onNext(currentAnimalsNode.getQuestion());
        else
            behaviorSubjectNames.onNext(currentAnimalsNode.getName());
    }

    public void subscribeOnQuestions( Action1<String> onNextAction)
    {
        behaviorSubjectQuestions.subscribe(onNextAction);
    }

    public void subscribeOnNames( Action1<String> onNextAction)
    {
        behaviorSubjectNames.subscribe(onNextAction);
    }

    public void insertInCurrentPosition(String question,String name)
    {
        dataSource.insert(pointer,question,name,currentAnimalsNode.getName());
    }

    private void getCurrentAnimalsNodeByPointer()
    {
        currentAnimalsNode = dataSource.getAnimalsNodeById(pointer);
    }
}
