package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AnimalsDB extends SQLiteOpenHelper
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
        sqLiteDatabase.execSQL("CREATE table AnimalsTable(id integer PRIMARY KEY NOT NULL,name text, question text, idPositive  integer, idNegative  integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

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
    public void insert2(AnimalsNode animalsNode, int parent,boolean yesOrNo)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",animalsNode.getId());
        contentValues.put("name",animalsNode.getName());
        contentValues.put("question",animalsNode.getQuestion());
        this.getWritableDatabase().insert("AnimalsTable",null,contentValues);

        Cursor cursor=this.getWritableDatabase().rawQuery("SELECT max(id) from AnimalsTable ",null);
        if (cursor != null)
            if(cursor.moveToFirst())
            {
                if (yesOrNo)
                this.getWritableDatabase().execSQL("UPDATE AnimalsTable SET idPositive = "+cursor.getInt(0)+" WHERE id =" + parent + ";");
                else
                this.getWritableDatabase().execSQL("UPDATE AnimalsTable SET idNegative = "+cursor.getInt(0)+" WHERE id =" + parent + ";");
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
    public ArrayList<AnimalsNode> getAllAnimalsNode()
    {
        ArrayList<AnimalsNode> temp= new ArrayList<AnimalsNode>();

        Cursor cursor = this.getWritableDatabase().query("AnimalsTable", null,null,null, null, null, null);
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

                temp.add(tempAnimalsNode);
            }
            while (cursor.moveToNext());
        }
        return temp;
    }
}

