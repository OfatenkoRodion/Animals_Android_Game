package ro.animals_android_game;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import DataBase.AnimalsDB;
import DataBase.AnimalsNode;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.textViewQuestion)
    TextView textViewQuestion;

    @BindView(R.id.editTextNewQuestion)
    EditText editTextNewQuestion;

    @BindView(R.id.editTextNewName)
    EditText editTextName;

    @BindViews({R.id.editTextNewQuestion, R.id.editTextNewName,R.id.buttonSave})
    View[] viewListForAdding;

    @BindViews({R.id.buttonGuessed, R.id.buttonNoGuessed})
    View[] viewListGussed;

    @BindViews({R.id.buttonYes ,R.id.buttonNo})
    View[] viewListYesNo;

    private final String TRIGGER = "trigger";

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AnimalsDB animalsDB = new AnimalsDB(this);
        prepareDB(animalsDB);

        presenter=new Presenter(animalsDB);

        Action1<String> onNextActionQuestion = s -> textViewQuestion.setText(s);

        presenter.subscribeOnQuestions(onNextActionQuestion);

        Action1<String> onNextActionName = s ->{

            for (View view: viewListGussed)
                view.setVisibility(View.VISIBLE);

            for (View view: viewListYesNo)
                view.setVisibility(View.INVISIBLE);

                textViewQuestion.setText("Ваше животное - это "+s+"?");
        };

        presenter.subscribeOnNames(onNextActionName);

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListYesNo)
            view.setVisibility(View.INVISIBLE);

    }
    private void prepareDB(AnimalsDB animalsDB)
    {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(TRIGGER, "");

        if (!savedText.equals("ok"))
        {
            FakeDataForDB.load(animalsDB);
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(TRIGGER, "ok");
            ed.commit();
        }
    }

    @OnClick(R.id.buttonStart)
    void onButtonStartClick()
    {
        presenter.returnToBegin();

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
        presenter.makePositiveStep();
    }

    @OnClick(R.id.buttonNo)
    void onButtonNoClick()
    {
        presenter.makeNegativeStep();
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
        textViewQuestion.setText("Помоги мне. Добавь свое животное и вопрос, который поможет мне отгадать животное. Или начинай с начала.");

        for (View view:viewListForAdding)
            view.setVisibility(View.VISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.buttonSave)
    void onButtonSaveClick()
    {
        String qe=editTextNewQuestion.getText().toString();
        String name=editTextName.getText().toString();

        if(qe.equals("") || name.equals(""))
        {
            for (View view:viewListForAdding)
                view.setVisibility(View.INVISIBLE);
            textViewQuestion.setText("");
            return;
        }

        for (View view:viewListForAdding)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListGussed)
            view.setVisibility(View.INVISIBLE);

        for (View view: viewListYesNo)
            view.setVisibility(View.INVISIBLE);

        textViewQuestion.setText("");

        presenter.insertInCurrentPosition(qe,name);
    }
}