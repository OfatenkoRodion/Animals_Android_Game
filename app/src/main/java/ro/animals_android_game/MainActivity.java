package ro.animals_android_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import DataBase.AnimalsDB;
import DataBase.AnimalsNode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.textViewQuestion)
    TextView textViewQuestion;

    @BindView(R.id.editTextNewQuestion)
    TextView editTextNewQuestion;

    @BindView(R.id.editTextName)
    TextView editTextName;

    private int pointer=1;
    private AnimalsDB animalsDB;
    private AnimalsNode currentAnimalsNode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animalsDB = new AnimalsDB(this);
    }

    @OnClick(R.id.buttonStart)
    void onButtonStartClick()
    {
        askQuestion();
    }

    private void askQuestion()
    {
        currentAnimalsNode=animalsDB.getAnimalsNodeById(pointer);
        textViewQuestion.setText(currentAnimalsNode.getQuestion());
    }
    @OnClick(R.id.buttonYes)
    void onButtonYesClick()
    {
        if (currentAnimalsNode.getIdPositive()==-1)
        {
            textViewQuestion.setText("Я думаю, твое животное - это "+currentAnimalsNode.getName()+". Это так?");
        }
        else
        {
            pointer=currentAnimalsNode.getIdPositive();
            askQuestion();
        }
    }

    @OnClick(R.id.buttonNo)
    void onButtonNoClick()
    {
        if (currentAnimalsNode.getIdNegative()==-1)
        {
            textViewQuestion.setText("Я думаю, твое животное - это "+currentAnimalsNode.getName()+". Это так?");
        }
        else
            pointer=currentAnimalsNode.getIdNegative();
    }

    @OnClick(R.id.buttonSave)
    void onButtonSaveClick()
    {
        AnimalsNode newAnimalNode = new AnimalsNode();
        newAnimalNode.setName(editTextName.getText().toString()).setQuestion(editTextNewQuestion.getText().toString());

        animalsDB.insert(newAnimalNode,pointer,true);
        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
    }
}
