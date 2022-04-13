package edu.bluejack21_2.guk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserDonateFragment extends Fragment {

    private TextView message;

    public UserDonateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_donate, container, false);

        message = view.findViewById(R.id.donate_prompt_message);
        String txt = "What a privilege to be here on the planet to contribute your unique donation to these homeless dogs.<br/>You can donate to: <b>604 123 1234</b> (BCA)";
        message.setText(Html.fromHtml(txt));
        return view;

    }
}