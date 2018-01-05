package ro.animals_android_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import DataBase.AnimalsDB;
import DataBase.AnimalsNode;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.textViewQuestion)
    TextView textViewQuestion;

    @BindView(R.id.editTextNewQuestion)
    TextView editTextNewQuestion;

    @BindView(R.id.editTextNewName)
    TextView editTextName;

    @BindViews({R.id.editTextNewQuestion, R.id.editTextNewName,R.id.buttonSave})
    View[] viewListForAdding;

    @BindViews({R.id.buttonGuessed, R.id.buttonNoGuessed})
    View[] viewListGussed;

    @BindViews({R.id.buttonYes ,R.id.buttonNo})
    View[] viewListYesNo;

    private int pointer=1;//Указатель на текущую точку узла в базе данных
    private AnimalsDB animalsDB; //База данных
    private AnimalsNode currentAnimalsNode; // текущий узел бд

    private boolean lastPressedAnswer; //true - yesButton, false - noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animalsDB = new AnimalsDB(this);

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListYesNo)
            view.setVisibility(View.INVISIBLE);

        loadTestData();
    }
    private void loadTestData()
    {
        AnimalsNode newAnimalNode = new AnimalsNode();
        //1
        newAnimalNode.setQuestion("Обитает на суше?").setId(1).setIdPositive(2);
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


    }

    @OnClick(R.id.buttonStart)
    void onButtonStartClick()
    {
        pointer=1;
        askQuestion();

        for (View view: viewListYesNo)
            view.setVisibility(View.VISIBLE);

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.buttonYes)
    void onButtonYesClick()
    {
        if (currentAnimalsNode.getIdPositive()!=-1)
        {
            pointer=currentAnimalsNode.getIdPositive();
            askQuestion();
            Toast.makeText(this, String.valueOf(currentAnimalsNode.getIdPositive()), Toast.LENGTH_SHORT).show();

            if (currentAnimalsNode.getIdPositive()==-1)
            {
                for (View view: viewListGussed)
                    view.setVisibility(View.VISIBLE);

                for (View view: viewListYesNo)
                    view.setVisibility(View.INVISIBLE);

                textViewQuestion.setText("Ваше животное - это "+currentAnimalsNode.getName()+"?");
            }
        }

        lastPressedAnswer=true;
    }

    @OnClick(R.id.buttonNo)
    void onButtonNoClick()
    {
        if (currentAnimalsNode.getIdNegative()!=-1)
        {
            pointer=currentAnimalsNode.getIdNegative();
            askQuestion();

            if (currentAnimalsNode.getIdNegative()==-1)
            {
                for (View view: viewListGussed)
                    view.setVisibility(View.VISIBLE);

                for (View view: viewListYesNo)
                    view.setVisibility(View.INVISIBLE);

                textViewQuestion.setText("Ваше животное - это "+currentAnimalsNode.getName()+"?");
            }
        }


        lastPressedAnswer=false;
    }

    @OnClick(R.id.buttonSave)
    void onButtonSaveClick()
    {
        AnimalsNode newAnimalNode = new AnimalsNode();
        newAnimalNode.setName(editTextName.getText().toString()).setQuestion(editTextNewQuestion.getText().toString());
        animalsDB.insert(newAnimalNode,pointer,lastPressedAnswer);

        for (View view:viewListGussed)
            view.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.buttonGuessed)
    void onButtonGuessedClick()
    {
        textViewQuestion.setText("Я так и знал :)");

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.buttonNoGuessed)
    void onButtonNoGuessedClick()
    {
        textViewQuestion.setText("Помоги мне. Добавь свое животное и вопрос, который поможет мне отгадать животное");

        for (View view:viewListForAdding)
            view.setVisibility(View.VISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);
    }

    private void askQuestion()
    {
        try
        {
            currentAnimalsNode=animalsDB.getAnimalsNodeById(pointer);
            textViewQuestion.setText(currentAnimalsNode.getQuestion());
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}