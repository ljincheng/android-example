package cn.booktable.note.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cn.booktable.note.R;
import cn.booktable.note.ui.adapter.NoticesListAdapter;
import cn.booktable.note.ui.activity.NewUserActivity;
import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.note.viewmodel.notices.NoticesViewModel;
import cn.booktable.note.viewmodel.user.User;

public class FragmentNotices extends BaseFragment {

    public static String TAG_NAME="NAV_NOTICES";

    private NoticesViewModel mNoticesViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fab ;
    private ViewGroup root;


    @Override
    public String getTagName() {
        return TAG_NAME;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // View root= super.onCreateView(inflater, container, savedInstanceState);
        root= (ViewGroup) inflater.inflate(R.layout.fragment_notices, container, false);
        if(savedInstanceState == null)
        {

        }
        return root;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {


            recyclerView = root.findViewById(R.id.recyclerview);

            fab = root.findViewById(R.id.fab);
            final NoticesListAdapter adapter = new NoticesListAdapter(this.getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

            // Get a new or existing ViewModel from the ViewModelProvider.
            mNoticesViewModel = new ViewModelProvider(getActivity()).get(NoticesViewModel.class);

            // Add an observer on the LiveData returned by getAlphabetizedWords.
            // The onChanged() method fires when the observed data changes and the activity is
            // in the foreground.
            mNoticesViewModel.getAllNoticesDetail().observe(getActivity(), new Observer<List<NoticesDetail>>() {
                @Override
                public void onChanged(@Nullable final List<NoticesDetail> noticesDetails) {
                    // Update the cached copy of the words in the adapter.
                    if(noticesDetails!=null) {
                        for (int i=0,k=noticesDetails.size();i<k;i++)
                        {
                            NoticesDetail noticesDetail= noticesDetails.get(i);
                            System.out.println("n="+i+",noticesDetail["+noticesDetail.toString()+"]");
                        }
                    }
                    adapter.setNoticesDetail(noticesDetails);
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), NewUserActivity.class);
                    startActivityForResult(intent,100);
//                      getActivity().startActivityForResult(intent,MainActivity.NEW_WORD_ACTIVITY_REQUEST_CODE);


//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivityForResult(intent,101);
                }
            });

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        setActivityActionBarVisible(false);
    }

    @Override
    public void setCurrentUser(User user) {
        super.setCurrentUser(user);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && getActivity().RESULT_OK== resultCode) {
            if (mNoticesViewModel != null) {
                NoticesDetail noticesDetail = new NoticesDetail();
                noticesDetail.setTitle(data.getStringExtra(NewUserActivity.EXTRA_RES_TITLE));
                String detail=data.getStringExtra(NewUserActivity.EXTRA_RES_DETAIL);
                if(detail!=null && detail.length()>0)
                {
                    noticesDetail.setDetail(detail);
                }
                User currentUser= getCurrentUser();
                if(currentUser!=null)
                {

                    noticesDetail.setUserId(currentUser.getId());
                    mNoticesViewModel.insert(noticesDetail);
                }
            }
        }
    }
}
