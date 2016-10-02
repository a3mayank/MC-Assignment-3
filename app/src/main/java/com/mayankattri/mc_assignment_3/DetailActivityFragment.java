package com.mayankattri.mc_assignment_3;

import android.content.Intent;
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
public class DetailActivityFragment extends Fragment {

    private String MESSAGE = this.getClass().getSimpleName();

    EditText et_roll;
    EditText et_name;
    EditText et_email;
    EditText et_batch;
    EditText et_contact;
    DBHandler db;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        et_roll = (EditText) rootView.findViewById(R.id.editText1);
        et_name = (EditText) rootView.findViewById(R.id.editText2);
        et_email = (EditText) rootView.findViewById(R.id.editText3);
        et_batch = (EditText) rootView.findViewById(R.id.editText4);
        et_contact = (EditText) rootView.findViewById(R.id.editText5);
        Button b_update = (Button) rootView.findViewById(R.id.B_update);
        Button b_delete = (Button) rootView.findViewById(R.id.B_delete);

        Intent intent = getActivity().getIntent();
        db = new DBHandler(getActivity());

        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String str = intent.getStringExtra(Intent.EXTRA_TEXT);
            String[] details = str.split("\n");

            et_roll.setText(details[0]);
            et_name.setText(details[1]);
            et_email.setText(details[2]);
            et_batch.setText(details[3]);
            et_contact.setText(details[4]);
        }

        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int roll = Integer.parseInt(et_roll.getText().toString());
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String batch= et_email.getText().toString();
                String contact = et_email.getText().toString();

                Student student = new Student(roll, name, email, batch, contact);
                db.deleteStudent(student);

                Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public boolean validate(String roll, String name, String email, String batch, String contact) {
        boolean valid = true;

        if (roll.isEmpty()) {
            et_roll.setError("enter correct roll no.");
            valid = false;
        } else {
            et_roll.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (name.isEmpty()) {
            et_name.setError("enter student's name");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (batch.isEmpty()) {
            et_batch.setError("enter student's batch");
            valid = false;
        } else {
            et_batch.setError(null);
        }

        if (contact.isEmpty() || contact.length() != 10) {
            et_contact.setError("enter correct contact no.");
            valid = false;
        } else {
            et_contact.setError(null);
        }

        return  valid;
    }

    public void addData() {
        String roll = et_roll.getText().toString();
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String batch = et_batch.getText().toString();
        String contact = et_contact.getText().toString();

        if (validate(roll, name, email, batch, contact)) {
            Student student = new Student(Integer.parseInt(roll), name, email, batch, contact);
            db.updateStudent(student);

            Toast.makeText(getActivity().getBaseContext(), "Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
