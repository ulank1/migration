package com.example.admin.pagination.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.pagination.Activities.RulesOfIncomingActivity;
import com.example.admin.pagination.Activities.SecondActivities.DiasporySecondActivity;
import com.example.admin.pagination.Activities.SecondActivities.EmploymentSecondActivity;
import com.example.admin.pagination.Activities.SecondActivities.QuestionSecondActivity;
import com.example.admin.pagination.Activities.RulesOfMigrationActivity;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.EAEU;
import com.example.admin.pagination.Serializables.RulesOfIncoming;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RVEAEUAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<EAEU> studentList;
    public Context context;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public int isAbroad;


    public RVEAEUAdapter(ArrayList<EAEU> students, RecyclerView recyclerView, Context context,int isAbroad) {
        studentList = students;
        this.isAbroad=isAbroad;
        this.context=context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return studentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_rv_eaeu, parent, false);

            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            EAEU singleStudent= (EAEU) studentList.get(position);

            ((StudentViewHolder) holder).tvName.setText(singleStudent.getName());
            Picasso.with(context).load("http://176.126.167.231:8000"+singleStudent.getPicture()).into(((StudentViewHolder) holder).imageView);

            ((StudentViewHolder) holder).student= singleStudent;
            ((StudentViewHolder) holder).isAbroad2=isAbroad;
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        int isAbroad2;
        public TextView tvText;
        public ImageView imageView;
        public EAEU student;

        public StudentViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_text_forum);
            imageView = (ImageView) v.findViewById(R.id.image_eaeu);


            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent;
                    if (isAbroad2==2)
                     intent=new Intent(v.getContext(), RulesOfMigrationActivity.class);

                    else if (isAbroad2==1)intent=new Intent(v.getContext(), RulesOfIncomingActivity.class);
                    else if (isAbroad2==3)intent=new Intent(v.getContext(), EmploymentSecondActivity.class);
                    else intent=new Intent(v.getContext(), DiasporySecondActivity.class);
                    intent.putExtra("position",student.getId());
                    intent.putExtra("text",student.getName());
                    v.getContext().startActivity(intent);


                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);

        }
    }
}