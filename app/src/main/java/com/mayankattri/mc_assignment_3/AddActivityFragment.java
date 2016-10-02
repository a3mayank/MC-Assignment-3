package com.mayankattri.mc_assignment_3;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddActivityFragment extends Fragment {

    private String MESSAGE = this.getClass().getSimpleName();

    EditText et_roll1;
    EditText et_name1;
    EditText et_email1;
    EditText et_batch1;
    EditText et_contact1;
    DBHandler db1;

    public AddActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        et_roll1 = (EditText) rootView.findViewById(R.id.ET_id);
        et_name1 = (EditText) rootView.findViewById(R.id.ET_name);
        et_email1 = (EditText) rootView.findViewById(R.id.ET_email);
        et_batch1 = (EditText) rootView.findViewById(R.id.ET_batch);
        et_contact1 = (EditText) rootView.findViewById(R.id.ET_contact);
        Button b_done = (Button) rootView.findViewById(R.id.B_done);
        db1 = new DBHandler(getActivity());

        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        return rootView;
    }

    public boolean validate(String roll, String name, String email, String batch, String contact) {
        boolean valid = true;

        if (roll.isEmpty()) {
            et_roll1.setError("enter correct roll no.");
            valid = false;
        } else {
            et_roll1.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email1.setError("enter a valid email address");
            valid = false;
        } else {
            et_email1.setError(null);
        }

        if (name.isEmpty()) {
            et_name1.setError("enter student's name");
            valid = false;
        } else {
            et_name1.setError(null);
        }

        if (batch.isEmpty()) {
            et_batch1.setError("enter student's batch");
            valid = false;
        } else {
            et_batch1.setError(null);
        }

        if (contact.isEmpty() || contact.length() != 10) {
            et_contact1.setError("enter correct contact no.");
            valid = false;
        } else {
            et_contact1.setError(null);
        }

        return  valid;
    }

    public void addData() {
        String roll = et_roll1.getText().toString();
        String name = "" + et_name1.getText().toString();
        String email = "" +  et_email1.getText().toString();
        String batch = "" + et_batch1.getText().toString();
        String contact = "" + et_contact1.getText().toString();

        if (validate(roll, name, email, batch, contact)) {
            Student student = new Student(Integer.parseInt(roll), name, email, batch, contact);
            db1.addStudent(student);

            Toast.makeText(getActivity().getBaseContext(), "Added", Toast.LENGTH_SHORT).show();
        }
    }
}
