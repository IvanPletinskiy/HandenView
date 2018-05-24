package com.handen.handenview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import static com.handen.handenview.database.DbSchema.BaseTable.Cols.DESCRIPTION;
import static com.handen.handenview.database.DbSchema.BaseTable.Cols.ID;
import static com.handen.handenview.database.DbSchema.BaseTable.Cols.MAX;
import static com.handen.handenview.database.DbSchema.BaseTable.Cols.MIN;
import static com.handen.handenview.database.DbSchema.BaseTable.Cols.NAME;
import static com.handen.handenview.database.DbSchema.BaseTable.Cols.UNITS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {

    private FloatingActionButton fab;

    private EditText id, name, currentValue, description, min, max, units;

    private int currentRowId;

    private OnFragmentInteractionListener mListener;

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        currentRowId = ParamsLab.get(getContext()).getFirstId();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsLab.get(getContext()).add();
                Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
            }
        });

        id = view.findViewById(R.id.id);
        id.setText(ParamsLab.get(getContext()).getValue(currentRowId, ID));
        id.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(ID, id.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });

        name = view.findViewById(R.id.name);
        name.setText(ParamsLab.get(getContext()).getValue(currentRowId, NAME));
        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(NAME, name.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });
        currentValue = view.findViewById(R.id.currentValue);

        description = view.findViewById(R.id.description);
        description.setText(ParamsLab.get(getContext()).getValue(currentRowId, DESCRIPTION));
        description.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(DESCRIPTION, description.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });
        min = view.findViewById(R.id.min);
        min.setText(ParamsLab.get(getContext()).getValue(currentRowId, MIN));
        min.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(MIN, min.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });
        max = view.findViewById(R.id.max);
        max.setText(ParamsLab.get(getContext()).getValue(currentRowId, MAX));
        max.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(MAX, max.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });
        units = view.findViewById(R.id.units);
        units.setText(ParamsLab.get(getContext()).getValue(currentRowId, UNITS));
        units.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ParamsLab.get(getContext()).update(UNITS, s.toString(), currentRowId);
            }
        });
        units.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ParamsLab.get(getContext()).update(UNITS, units.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
