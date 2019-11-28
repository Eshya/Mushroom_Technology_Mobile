package com.example.mushroomtechnologymobile.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mushroomtechnologymobile.NetworkUtil;
import com.example.mushroomtechnologymobile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    DatabaseReference[] databaseReference = new DatabaseReference[2];
    static String Database_Path = "nodemcu";
    Switch pompaSwitch,FreonSwitch;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        databaseReference[0] = FirebaseDatabase.getInstance().getReference(Database_Path+"/pompa");
        databaseReference[1] = FirebaseDatabase.getInstance().getReference(Database_Path+"/freon");

        final CardView onPompa = root.findViewById(R.id.pompaon);
        final CardView offPompa = root.findViewById(R.id.pompaoff);
        pompaSwitch = root.findViewById(R.id.switch_pompa);
        FreonSwitch = root.findViewById(R.id.switch_freon);
        databaseReference[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getValue()!= null){
                    if(dataSnapshot.getValue(String.class).equals("on")){
                        pompaSwitch.setChecked(true);
                        Toast.makeText(getContext(),"pompa on",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pompaSwitch.setChecked(false);
                        Toast.makeText(getContext(),"pompa off",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference[1].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getValue()!= null){
                    if(dataSnapshot.getValue(String.class).equals("on")){
                        FreonSwitch.setChecked(true);
                        Toast.makeText(getContext(),"Freon on",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FreonSwitch.setChecked(false);
                        Toast.makeText(getContext(),"Freon off",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pompaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!NetworkUtil.getConnectivityStatusString(getContext()).equals("No internet is available"))
                    {
                        databaseReference[0].setValue("on");
                    }
                    else{
                        Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    if(!NetworkUtil.getConnectivityStatusString(getContext()).equals("No internet is available"))
                    {
                        databaseReference[0].setValue("off");
                    }
                    else{
                        Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        FreonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!NetworkUtil.getConnectivityStatusString(getContext()).equals("No internet is available"))
                    {
                        databaseReference[1].setValue("on");
                    }
                    else{
                        Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    if(!NetworkUtil.getConnectivityStatusString(getContext()).equals("No internet is available"))
                    {
                        databaseReference[1].setValue("off");
                    }
                    else{
                        Toast.makeText(getContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        return root;
    }
}