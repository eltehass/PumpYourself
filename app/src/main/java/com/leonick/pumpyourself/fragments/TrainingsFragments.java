package com.leonick.pumpyourself.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.leonick.pumpyourself.R;

public class TrainingsFragments extends Fragment {

    public TrainingsFragments() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);
        return view;
    }

}
