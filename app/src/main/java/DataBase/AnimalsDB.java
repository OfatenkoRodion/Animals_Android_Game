package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ro.animals_android_game.iDataSource;

public class AnimalsDB extends SQLiteOpenHelper implements iDataSource
{
    static final String BD_NAME ="Animals";
    static final int VERSION =1;

    public AnimalsDB(Context context)
    {
        super(context, BD_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //!!!!!!! Ключ (id) не auto increment, всz установка id идет руками
        sqLiteDatabase.execSQL("CREATE table AnimalsTable(id integer PRIMARY KEY NOT NULL,name text, question text, idPositive  integer, idNegative  integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    //Вносит узлы в БД
    public void insert(AnimalsNode animalsNode)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",animalsNode.getId());
        contentValues.put("name",animalsNode.getName());
        contentValues.put("question",animalsNode.getQuestion());
        contentValues.put("idPositive",animalsNode.getIdPositive());
        contentValues.put("idNegative",animalsNode.getIdNegative());
        this.getWritableDatabase().insert("AnimalsTable",null,contentValues);
    }
    public void setIdPositiveById(int id,int newIdPositive)
    {
        this.getWritableDatabase().execSQL("UPDATE AnimalsTable SET idPositive = "+newIdPositive+" WHERE id =" + id + ";");
    }
    public void setIdNegativeById(int id,int newIdNegative)
    {
        this.getWritableDatabase().execSQL("UPDATE AnimalsTable SET idNegative = "+newIdNegative+" WHERE id =" + id + ";");
    }
    public void setQuestionById(int id,String question)
    {
        this.getWritableDatabase().execSQL("UPDATE AnimalsTable SET question = '"+question+"' WHERE id =" + id + ";");
    }
    public int getMaxId()
    {
        Cursor cursor=this.getWritableDatabase().rawQuery("SELECT max(id) from AnimalsTable ",null);
        if (cursor != null)
            if(cursor.moveToFirst())
            {
                return cursor.getInt(0);

            }
        return -1;
    }
    public AnimalsNode getAnimalsNodeById(int id)
    {
        String selection = "id = ?";
        String selectionArgs[]=new String[] { String.valueOf(id) };
        Cursor cursor = this.getWritableDatabase().query("AnimalsTable", null,selection,selectionArgs, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                AnimalsNode tempAnimalsNode = new AnimalsNode();

                tempAnimalsNode.setId((cursor.getInt(cursor.getColumnIndex("id"))))
                        .setName(cursor.getString(cursor.getColumnIndex("name")))
                        .setQuestion(cursor.getString(cursor.getColumnIndex("question")))
                        .setIdPositive(cursor.getInt(cursor.getColumnIndex("idPositive")))
                        .setIdNegative(cursor.getInt(cursor.getColumnIndex("idNegative")));

                return tempAnimalsNode;
            }
            while (cursor.moveToNext());
        }
        return null;
    }
}

