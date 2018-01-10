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
    public void insert(int pointer,String question,String newName,String oldName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.beginTransaction();
        try
        {
            // Получаем максимальный id в базе данных.
            // Так как мы устанавливаем id для новых узлов вручную, то это гарантирует, что maxId+1 - Unique
            int maxIdValue = getMaxId();

            // Обновляем конечный узел по указателю.
            // После добавления двух узлов для положительного и отрицательного узлов с правильными id, узел по указателю перестанет быть конечным
            setIdPositiveById(pointer, maxIdValue + 1);
            setIdNegativeById(pointer, maxIdValue + 2);
            setQuestionById(pointer, question);//Записываем вопрос, в зависимости от которого будет продвижение указателя

            // Новый узел, на который будут переходить после положительного ответа
            AnimalsNode newAnimalNode = new AnimalsNode();
            newAnimalNode.setName(newName)//В название животного записывает то, что указал пользователь. Это новое животное в Бд
                    .setId(maxIdValue + 1);
            insert(newAnimalNode);

            // Новый узел, на который будут переходить после отрицательного ответа
            newAnimalNode = new AnimalsNode();
            newAnimalNode.setName(oldName)//Не теряем старое конечное животное, дабавляем его тут. Тем самым, опускаем вниз по дереву
                    .setId(maxIdValue + 2);
            insert(newAnimalNode);

            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
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

