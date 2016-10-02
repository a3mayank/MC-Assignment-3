package com.mayankattri.mc_assignment_3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private String MESSAGE = this.getClass().getSimpleName();

    ArrayAdapter<String> adapter;
    ArrayList<String> studentsList;
    String batch;
    String storage;
    String appdata;
    SharedPreferences prefs;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        batch = prefs.getString(getString(R.string.pref_batch_key), getString(R.string.pref_batch_btech));
        Log.d("Batch : ", batch);

        DBHandler db = new DBHandler(getActivity());

        // Inserting Students/Rows
        Log.d("Insert: ", "Inserting ..");
        db.addStudent(new Student(2014063, "Mayank Attri", "mayank14063@iiitd.ac.in", "BTech", "9910129202"));
        db.addStudent(new Student(2014054, "Kunal Sharma", "kunal14054@iiitd.ac.in", "BTech", "9965465465"));
        db.addStudent(new Student(2014119, "Vipin Chaudhary", "vipin14119@iiitd.ac.in", "MTech", "9915129202"));
        db.addStudent(new Student(2014098, "Sahil Ruhela", "sahil14098@iiitd.ac.in", "MTech", "9910129602"));
        db.addStudent(new Student(2014080, "Pulkit Rohilla", "pulkit14080@iiitd.ac.in", "PhD", "9910125552"));

        // Reading all students
        Log.d("Reading: ", "Reading all students");
        final List<Student> students = db.getAllStudents();

        studentsList = new ArrayList<>();

        for (Student student : students) {
            String log = "Roll No.: " + student.getRoll() + "\nName: " + student.getName() +
                    "\nEmail: " + student.getEmail();

//            if (batch.equals(student.getBatch())) {
//                studentsList.add(student.getRoll() + "\n" + student.getName() + "\n" +
//                        student.getEmail() + "\n" + student.getBatch() + "\n" + student.getContact());
//            }

            studentsList.add(student.getRoll() + "\n" + student.getName() + "\n" +
                    student.getEmail() + "\n" + student.getBatch() + "\n" + student.getContact());

            // Writing students  to log
            Log.d("Students : ", log);
        }

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item_textview, studentsList);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String forecast = adapter.getItem(position);
                    Intent intent = new Intent(view.getContext(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, forecast);
                    startActivity(intent);
                }
            });
        }

        Button b_add = (Button) rootView.findViewById(R.id.B_add);
        Button b_write = (Button) rootView.findViewById(R.id.B_write);
        Button b_read = (Button) rootView.findViewById(R.id.B_read);

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        b_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storage = prefs.getString(getString(R.string.pref_storage_key), getString(R.string.pref_storage_internal));
                appdata = prefs.getString(getString(R.string.pref_appdata_key), getString(R.string.pref_appdata_public));
                Log.d("Store in : ", storage);
                Log.d("App Data : ", appdata);

                if (storage.equals("Internal Storage")) {
                    String fileName = "mydata";
                    String data = "";

                    for (int i = 0; i < studentsList.size(); i++) {
                        data += studentsList.get(i);
                        data += "\n\n";
                    }

                    try {
                        FileOutputStream outputStream;
                        outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                        outputStream.write(data.getBytes());
                        outputStream.close();
                        Toast.makeText(getActivity().getBaseContext(), "file saved to internal storage", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (storage.equals("External Storage")) {
                    File sdcard;
                    // get the path to sdcard
                    if (appdata.equals("Public")) {
                        sdcard = Environment.getExternalStorageDirectory();
                    } else {
                        sdcard = getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                    }

                    // to this path add a new directory path
                    File dir = new File(sdcard.getAbsolutePath() + "/MC-Assignment-3/");
                    Log.d("Directory : ", String.valueOf(dir));

                    // create this directory if not already created
                    dir.mkdir();
                    // create the file in which we will write the contents
                    File file = new File(dir, "mydata");
                    String data1 = "";

                    for (int i = 0; i < studentsList.size(); i++) {
                        data1 += studentsList.get(i);
                        data1 += "\n\n";
                    }

                    try {
                        FileOutputStream os = new FileOutputStream(file);
                        os.write(data1.getBytes());
                        os.close();
                        Toast.makeText(getActivity().getBaseContext(), "file saved to external storage", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        b_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ReadActivity.class).putExtra(Intent.EXTRA_TEXT, studentsList);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(albumName, " : Directory not created");
        }
        return file;
    }
}
