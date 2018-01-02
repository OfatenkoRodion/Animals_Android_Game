package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqLiteDatabase.execSQL("CREATE table AnimalsTable(id integer PRIMARY KEY autoincrement,name text NOT NULL, question text NOT NULL, idPositive  integer, idNegative  integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    private void insert(String name,String question, int idPositive, boolean idNegative)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("question",question);
        contentValues.put("idPositive",idPositive);
        contentValues.put("idNegative",idNegative);
        this.getWritableDatabase().insert("AnimalsTable",null,contentValues);
    }
}

