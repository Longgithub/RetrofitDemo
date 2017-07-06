package com.braval.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.braval.retrofitdemo.R;
import com.braval.retrofitdemo.components.DaggerMainComponent;
import com.braval.retrofitdemo.contract.MainContract;
import com.braval.retrofitdemo.moudles.MainMoudle;
import com.braval.retrofitdemo.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @Inject
    MainPresenter mainPresenter;
    ViewHolder mViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        setContentView(view);
        DaggerMainComponent.builder().mainMoudle(new MainMoudle(this)).build().inject(this);
        mViewHolder=new ViewHolder(view);
        mainPresenter.showIp();
    }

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    @Override
    public void showProgressDialog(String msg) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onHandleSessionTimeout(boolean isNeedKillProcess) {

    }

    @Override
    public void showHttpErrorMessage(String msg, int style) {

    }

    @Override
    public void showHttpErrorMessage(int msg, int style) {

    }

    @Override
    public void onUpdateUI(String ip) {
        mViewHolder.mTextView.setText(ip);
    }
    class ViewHolder{
        @BindView(R.id.textView3)
        TextView mTextView;
        @OnClick(R.id.button)
        void onClick(){
            mainPresenter.showIp();
        }
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
