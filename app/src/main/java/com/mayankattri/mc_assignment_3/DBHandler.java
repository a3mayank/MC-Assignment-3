package com.mayankattri.mc_assignment_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayank on 1/10/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "studentsInfo";

    // Contacts table name
    private static final String TABLE_STUDENTS = "students";

    // Shops Table Columns names
    private static final String KEY_ROLL = "roll";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BATCH = "batch";
    private static final String KEY_CONTACT = "contact";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ROLL + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_BATCH + " TEXT," + KEY_CONTACT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new student
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROLL, student.getRoll());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_EMAIL, student.getEmail());
        values.put(KEY_BATCH, student.getBatch());
        values.put(KEY_CONTACT, student.getContact());

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one student
    public Student getStudent(int roll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ROLL,
                        KEY_NAME, KEY_EMAIL, KEY_BATCH, KEY_CONTACT}, KEY_ROLL + "=?",
                new String[]{String.valueOf(roll)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student contact = new Student(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return student
        return contact;
    }

    // Getting All students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setRoll(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setEmail(cursor.getString(2));
                student.setBatch(cursor.getString(3));
                student.setContact(cursor.getString(4));
                // Adding contact to list
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        // return contact list
        return studentList;
    }

    // Getting students Count
    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a student
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ROLL, student.getRoll());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_EMAIL, student.getEmail());
        values.put(KEY_BATCH, student.getBatch());
        values.put(KEY_CONTACT, student.getContact());

        // updating row
        return db.update(TABLE_STUDENTS, values, KEY_ROLL + " = ?",
                new String[]{String.valueOf(student.getRoll())});
    }

    // Deleting a student
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ROLL + " = ?",
                new String[] { String.valueOf(student.getRoll()) });
        db.close();
    }
}
