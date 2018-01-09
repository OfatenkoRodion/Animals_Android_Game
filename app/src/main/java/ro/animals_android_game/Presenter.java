package ro.animals_android_game;

import DataBase.AnimalsNode;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class Presenter
{
    private iDataSource dataSource;
    private AnimalsNode currentAnimalsNode;
    private int pointer=1;

    private BehaviorSubject<String> behaviorSubject;

    public Presenter(iDataSource dataSource)
    {
        this.dataSource=dataSource;
        behaviorSubject= BehaviorSubject.create("init");
    }

    public void makePositiveStep()
    {
        pointer=currentAnimalsNode.getIdPositive();
        getCurrentAnimalsNodeByPointer();
        behaviorSubject.onNext(currentAnimalsNode.getQuestion());
    }

    public void makeNegativeStep()
    {
        pointer=currentAnimalsNode.getIdNegative();
        getCurrentAnimalsNodeByPointer();
        behaviorSubject.onNext(currentAnimalsNode.getQuestion());
    }

    public void subscribeOnStepsResult( Action1<String> onNextAction)
    {
        behaviorSubject.subscribe(onNextAction);
    }

    private void getCurrentAnimalsNodeByPointer()
    {
        currentAnimalsNode = dataSource.getAnimalsNodeById(pointer);
    }

}
