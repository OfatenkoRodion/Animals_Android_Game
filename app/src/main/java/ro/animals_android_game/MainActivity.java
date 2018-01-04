package ro.animals_android_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private int pointer=1;//Указатель на текущую точку узла в базе данных
    private AnimalsDB animalsDB; //База данных
    private AnimalsNode currentAnimalsNode; // текущий узел бд

    Status status=Status.INPROGRESS ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animalsDB = new AnimalsDB(this);

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

    }

    @OnClick(R.id.buttonStart)
    void onButtonStartClick()
    {
        askQuestion();
        ((Button)findViewById(R.id.buttonStart)).setText("Дальше");
    }

    @OnClick(R.id.buttonYes)
    void onButtonYesClick()
    {
        pointer=currentAnimalsNode.getIdPositive();
        askQuestion();
    }

    @OnClick(R.id.buttonNo)
    void onButtonNoClick()
    {
        pointer=currentAnimalsNode.getIdNegative();
        askQuestion();
    }

    @OnClick(R.id.buttonSave)
    void onButtonSaveClick()
    {
        AnimalsNode newAnimalNode = new AnimalsNode();
        newAnimalNode.setName(editTextName.getText().toString()).setQuestion(editTextNewQuestion.getText().toString());

        animalsDB.insert(newAnimalNode,pointer,true);

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

        status=Status.INPROGRESS;
        pointer=1;

        ((Button)findViewById(R.id.buttonStart)).setText("Начать с начала");

        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonGuessed)
    void onButtonGuessedClick()
    {
        textViewQuestion.setText("Я так и знал :)");
    }

    @OnClick(R.id.buttonNoGuessed)
    void onButtonNoGuessedClick()
    {
        textViewQuestion.setText("Помоги мне. Добавь свое животное и вопрос, который поможет мне отгадать животное");
        for (View view:viewListForAdding)
            view.setVisibility(View.VISIBLE);
    }

    private void askQuestion()
    {
        currentAnimalsNode=animalsDB.getAnimalsNodeById(pointer);
        textViewQuestion.setText(currentAnimalsNode.getQuestion());
    }
}