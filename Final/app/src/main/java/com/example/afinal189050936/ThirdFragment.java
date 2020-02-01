package com.example.afinal189050936;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {
    private ArrayList Contacts = new ArrayList<Map<String,Object>>();
    private TextView tv_totalExpend;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity activity = (MainActivity) getActivity();
            switch (v.getId()){
                case R.id.bt_addExpend:
                    activity.skipExpend();
                    break;
            }
        }
    };

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bt_addExpend).setOnClickListener(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        View header = inflater.inflate(R.layout.contact_list_header,null);
        Intent intent = getActivity().getIntent();

        tv_totalExpend = view.findViewById(R.id.tv_totalExpend);
        tv_totalExpend.setText(intent.getStringExtra("totalExpend"));

        Contacts = (ArrayList<Map<String,Object>>) intent.getSerializableExtra("Expend");
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), Contacts,
                R.layout.contact_list_item,
                new String[]{"projectName","money","date","type","mark"},
                new int[]{R.id.tv_projectName,R.id.tv_money,R.id.tv_date,R.id.tv_type,R.id.tv_mark});
        ListView lvContact = view.findViewById(R.id.lv_expend);
        lvContact.addHeaderView(header);

        lvContact.setAdapter(adapter);
        return view;
    }

}
