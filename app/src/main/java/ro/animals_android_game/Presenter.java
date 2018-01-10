package ro.animals_android_game;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import DataBase.AnimalsDB;
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
        AnimalsDB animalsDB=((AnimalsDB)dataSource);
        SQLiteDatabase db=animalsDB.getWritableDatabase();
        db.beginTransaction();
        try
        {
            // Получаем максимальный id в базе данных.
            // Так как мы устанавливаем id для новых узлов вручную, то это гарантирует, что maxId+1 - Unique
            int maxIdValue = animalsDB.getMaxId();

            // Обновляем конечный узел по указателю.
            // После добавления двух узлов для положительного и отрицательного узлов с правильными id, узел по указателю перестанет быть конечным
            animalsDB.setIdPositiveById(pointer, maxIdValue + 1);
            animalsDB.setIdNegativeById(pointer, maxIdValue + 2);
            animalsDB.setQuestionById(pointer, question);//Записываем вопрос, в зависимости от которого будет продвижение указателя

            // Новый узел, на который будут переходить после положительного ответа
            AnimalsNode newAnimalNode = new AnimalsNode();
            newAnimalNode.setName(name)//В название животного записывает то, что указал пользователь. Это новое животное в Бд
                    .setId(maxIdValue + 1);
            animalsDB.insert(newAnimalNode);

            // Новый узел, на который будут переходить после отрицательного ответа
            newAnimalNode = new AnimalsNode();
            newAnimalNode.setName(currentAnimalsNode.getName())//Не теряем старое конечное животное, дабавляем его тут. Тем самым, опускаем вниз по дереву
                    .setId(maxIdValue + 2);
            animalsDB.insert(newAnimalNode);

            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
    }

    private void getCurrentAnimalsNodeByPointer()
    {
        currentAnimalsNode = dataSource.getAnimalsNodeById(pointer);
    }
}
