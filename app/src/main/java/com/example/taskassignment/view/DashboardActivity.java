package com.example.taskassignment.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.taskassignment.BasicUtality.BasicFunction;
import com.example.taskassignment.BasicUtality.Constant;
import com.example.taskassignment.R;
import com.example.taskassignment.adapter.TestTopicAdapter;
import com.example.taskassignment.databinding.ActivityDashboardBinding;
import com.example.taskassignment.model.UserModel;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private UserModel userModel;
    private String[] topicName = {"Maths Quiz", "Java Quiz", "Android Quiz"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        userModel = BasicFunction.getPassValueObjectIntnet(this, Constant.PASS_INTENT_OBJECT_VALUE);
        setToolBar(userModel.getName(), userModel.getAddress());

        TestTopicAdapter testTopicAdapter = new TestTopicAdapter(topicName);
        binding.recyelerView.setAdapter(testTopicAdapter);
        binding.recyelerView.setLayoutManager(BasicFunction.getLinearLayoutManagerVertical(this));

        testTopicAdapter.onClickFunction(new TestTopicAdapter.onClickInterface() {
            @Override
            public void onInfoCattleClicked(int position) {
                switch (position) {
                    case 0:
                        startActivity(BasicFunction.passValueIntent(DashboardActivity.this,
                                QuizActivty.class, 0, Constant.QUIZE_ACTIVTY));
                        break;
                    case 1:
                        startActivity(BasicFunction.passValueIntent(DashboardActivity.this,
                                QuizActivty.class, 1, Constant.QUIZE_ACTIVTY));
                        break;
                    case 2:
                        startActivity(BasicFunction.passValueIntent(DashboardActivity.this,
                                QuizActivty.class, 2, Constant.QUIZE_ACTIVTY));
                        break;
                }
            }
        });

    }


    public void setToolBar(String title, String subTitle) {
        binding.toolbar.setTitle(title);
        binding.toolbar.setSubtitle(subTitle);
        binding.toolbar.inflateMenu(R.menu.menu);

        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_score:
                        BasicFunction.getToastMessage("hellop", DashboardActivity.this);
                        break;
                    case R.id.logout:
                        startActivity(BasicFunction.useIntent(DashboardActivity.this, MainActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
