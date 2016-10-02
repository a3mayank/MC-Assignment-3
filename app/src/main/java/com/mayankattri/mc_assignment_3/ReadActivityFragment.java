package com.mayankattri.mc_assignment_3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReadActivityFragment extends Fragment {

    private String MESSAGE = this.getClass().getSimpleName();
    String readFrom;
    String appData;

    public ReadActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_read, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        readFrom = prefs.getString(getString(R.string.pref_read_key), getString(R.string.pref_read_internal));
        appData = prefs.getString(getString(R.string.pref_appdata_key), getString(R.string.pref_appdata_public));

        Intent intent = getActivity().getIntent();
        TextView tv_read = (TextView) rootView.findViewById(R.id.TV_readFile);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            if (readFrom.equals("Internal Storage")) {
                String fileName = "mydata";
                try {
                    FileInputStream fin = getActivity().openFileInput(fileName);
                    int c;
                    String temp = "";

                    while ((c = fin.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    tv_read.setText(temp);
                    Toast.makeText(getActivity().getBaseContext(), "file read from internal storage", Toast.LENGTH_SHORT).show();
                } catch (Exception ignored) {
                }
            }

            if (readFrom.equals("External Storage")) {
                // works for public files
                String filename = "mydata";
                String aDataRow = "";
                String aBuffer = "";
                File myFile;

                if (appData.equals("Public")) {
                    myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/MC-Assignment-3/" + filename);
                } else {
                    myFile = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() +
                            "/MC-Assignment-3/" + filename);
                }

                try {
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));

                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow + "\n";
                    }
                    myReader.close();
                    tv_read.setText(aBuffer);
                    Toast.makeText(getActivity().getBaseContext(), "file read from external storage", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return rootView;
    }
}
