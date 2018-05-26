package com.handen.handenview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private TextView currentParam;

    private int currentRowId;

    private OnFragmentInteractionListener mListener;

    public BaseFragment() {
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

        Button button = view.findViewById(R.id.previous);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPrevious();
            }
        });

        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNext();
            }
        });

        currentParam = view.findViewById(R.id.currentParam);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParamsLab.get(getContext()).add();
                Toast.makeText(getContext(), "Добавлено", Toast.LENGTH_SHORT).show();
            }
        });

        id = view.findViewById(R.id.id);
        id.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if(id.getText().toString().equals("")) {
                        int id = currentRowId;
                        if(currentRowId != ParamsLab.get(getContext()).getFirstId()) {
                            stepPrevious();
                        }
                        else {
                            stepNext();
                        }
                        ParamsLab.get(getContext()).delete (id);
                    }
                    else
                        ParamsLab.get(getContext()).update(ID, id.getText().toString(), currentRowId);
                    return true;
                }
                return false;
            }
        });

        name = view.findViewById(R.id.name);
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

        readAndSetValues();

        return view;
    }

    private void stepPrevious() {
        currentRowId = ParamsLab.get(getContext()).getPrevisiousId(currentRowId);
        readAndSetValues();
    }

    private void stepNext() {
        currentRowId = ParamsLab.get(getContext()).getNextId(currentRowId);
        readAndSetValues();
    }

    private void readAndSetValues() {
        currentParam.setText("[" + currentRowId + "/" + Integer.toString(ParamsLab.get(getContext()).getRowsCount()) + "]");
        id.setText(ParamsLab.get(getContext()).getValue(currentRowId, ID));
        name.setText(ParamsLab.get(getContext()).getValue(currentRowId, NAME));
        description.setText(ParamsLab.get(getContext()).getValue(currentRowId, DESCRIPTION));
        min.setText(ParamsLab.get(getContext()).getValue(currentRowId, MIN));
        max.setText(ParamsLab.get(getContext()).getValue(currentRowId, MAX));
        units.setText(ParamsLab.get(getContext()).getValue(currentRowId, UNITS));
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
