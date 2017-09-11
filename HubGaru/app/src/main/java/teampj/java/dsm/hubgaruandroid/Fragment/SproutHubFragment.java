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
import teampj.java.dsm.hubgaruandroid.Activity.TeamCreateActivity;
import teampj.java.dsm.hubgaruandroid.R;

/**
 * Created by user on 2017-08-22.
 */

public class SproutHubFragment extends Fragment {

    private Button createTeamBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sprout_hub, container, false);

        createTeamBtn = (Button) view.findViewById(R.id.team_create_button);
        createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_to_tc = new Intent(getActivity(), TeamCreateActivity.class);
                startActivity(main_to_tc);
            }
        });

        return view;
    }
}
