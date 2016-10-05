package studio.crazybt.adventure.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.crazybt.adventure.R;
import studio.crazybt.adventure.adapters.StatusShortcutListAdapter;

/**
 * Created by Brucelee Thanh on 12/09/2016.
 */
public class TabDiscussTripFragment extends Fragment {

    private View rootView;
    private StatusShortcutListAdapter sslaDiscussTrip;
    @BindView(R.id.rvDiscussTrip)
    RecyclerView rvDiscussTrip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_tab_discuss_trip, container, false);
            ButterKnife.bind(this, rootView);
            this.initDiscussTripList();
        }
        return rootView;
    }

    private void initDiscussTripList(){
        rvDiscussTrip.setLayoutManager(new LinearLayoutManager(getContext()));
        sslaDiscussTrip = new StatusShortcutListAdapter(getContext());
        rvDiscussTrip.setAdapter(sslaDiscussTrip);
    }
}