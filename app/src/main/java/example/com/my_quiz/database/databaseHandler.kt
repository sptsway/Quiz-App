package example.com.my_quiz.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val TABLE_NAME= "SCORECOARD"
val COL_SCORE= "SCORE"
val COL_DUARTION= "DURATION"
//val COL_INDX= "SERIAL NUMBER"

class databaseHandler(var context: Context) : SQLiteOpenHelper(context, TABLE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable= "CREATE TABLE " + TABLE_NAME + "( " +
                //COL_INDX +  " NUMBER AUTOINCREMENT, " +
                COL_SCORE + " NUMBER, "+COL_DUARTION+" DECIMAL(3,3) ) "
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
        val deleteRows= "DELETE From TABLE " + TABLE_NAME
        db?.execSQL(deleteRows)
    }

    fun insertScore(curScore : myScore) {
        val db= this.writableDatabase
        val cv= ContentValues()
        //cv.put(COL_INDX,curScore.indx)
        cv.put(COL_SCORE,curScore.score)
        cv.put(COL_DUARTION,curScore.duration)
        val result = db.insert(TABLE_NAME,null,cv)

        if(result == -1.toLong()) {
            Toast.makeText(context,"INSERTION FAILED",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"INSERTION SUCCESSFUL",Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() : MutableList<myScore> {
        val list : MutableList<myScore> = ArrayList()

        val db= this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_SCORE DESC, $COL_DUARTION"
        val result= db.rawQuery(query,null)

        if(result.moveToFirst()) {
            do{
                val curScore= myScore()
                curScore.score= result.getString(result.getColumnIndex(COL_SCORE)).toInt()
                curScore.duration= result.getString(result.getColumnIndex(COL_DUARTION)).toDouble()
                list.add(curScore)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData() {
        val db= this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }


}