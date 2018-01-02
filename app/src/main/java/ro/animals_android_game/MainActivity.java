package ro.animals_android_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.textViewQuestion)
    TextView textViewQuestion;

    @BindView(R.id.buttonStart)
    Button buttonStart;

    @BindView(R.id.buttonYes)
    Button buttonYes;

    @BindView(R.id.buttonNo)
    Button buttonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonStart)
    void onButtonStartClick()
    {
        Toast.makeText(this, "R.id.buttonStart", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonYes)
    void onButtonYesClick()
    {
        Toast.makeText(this, "R.id.buttonYes", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonNo)
    void onButtonNoClick()
    {
        Toast.makeText(this, "R.id.buttonNo", Toast.LENGTH_SHORT).show();
    }
}
