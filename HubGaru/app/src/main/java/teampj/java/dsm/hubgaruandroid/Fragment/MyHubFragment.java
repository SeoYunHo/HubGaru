package teampj.java.dsm.hubgaruandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import teampj.java.dsm.hubgaruandroid.Activity.MainActivity;
import teampj.java.dsm.hubgaruandroid.Activity.TeamMainActivity;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class MyHubFragment extends Fragment{

    private Button teamMainBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_hub, container, false);

        teamMainBtn = (Button) view.findViewById(R.id.team_main_button);
        teamMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_team = new Intent(getActivity(), TeamMainActivity.class);
                startActivity(main_to_team);
            }
        });

        return view;
    }
}
