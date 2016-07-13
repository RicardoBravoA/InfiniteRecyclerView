package com.rba.inifiniterecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rba.inifiniterecyclerview.model.entity.ErrorEntity;
import com.rba.inifiniterecyclerview.model.entity.ItemEntity;
import com.rba.inifiniterecyclerview.model.entity.LoadingEntity;
import com.rba.inifiniterecyclerview.util.Constant;
import com.rba.inifiniterecyclerview.util.Util;
import com.rba.inifiniterecyclerview.view.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ItemAdapter itemAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<Object> objectList = new ArrayList<>();
    private int visibleThreshold = 1, page = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading=false;
    protected Handler handler;
    private int status = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvData = (RecyclerView) findViewById(R.id.rcvData);
        linearLayoutManager = new LinearLayoutManager(this);
        itemAdapter = new ItemAdapter(this, objectList);
        rcvData.setLayoutManager(linearLayoutManager);
        rcvData.setAdapter(itemAdapter);

        addData();
        handler = new Handler();

        rcvData.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loading = true;
                    loadMoreData();
                }
            }
        });

    }

    public void showLoadingItem(){
        objectList.add(new LoadingEntity("Cargando..."));
        itemAdapter.notifyItemInserted(objectList.size() - 1);
    }

    public void removeLoadingItem(){
        objectList.remove(objectList.size() - 1);
        itemAdapter.notifyItemRemoved(objectList.size());
    }

    public void showErrorItem(){
        objectList.add(new ErrorEntity("Error"));
        itemAdapter.notifyItemInserted(objectList.size() - 1);
    }

    public void removeErrorItem(){
        objectList.remove(objectList.size() - 1);
        itemAdapter.notifyItemRemoved(objectList.size());
    }


    public void loadMoreData(){
        showLoadingItem();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                removeLoadingItem();

                status = Util.generateNumber();

                if(status == Constant.STATUS_OK){
                    page++;
                    addData();
                    loading=false;
                }
                if(status == Constant.STATUS_ERROR){
                    loading = true;
                    showErrorItem();
                }

            }
        }, 2000);
    }


    private void addData(){

        int total;

        if(page == 1){
            total = page;
        }else{
            total = (page - 1) * 10 + 1;
        }

        for(int i = total; i < total + 10; i++){
            objectList.add(new ItemEntity(i, "Data "+i));
        }

        if(itemAdapter!=null){
            itemAdapter.notifyDataSetChanged();
        }

    }



}
