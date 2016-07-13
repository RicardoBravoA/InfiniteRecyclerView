package com.rba.inifiniterecyclerview.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rba.inifiniterecyclerview.MainActivity;
import com.rba.inifiniterecyclerview.R;
import com.rba.inifiniterecyclerview.model.entity.ErrorEntity;
import com.rba.inifiniterecyclerview.model.entity.ItemEntity;
import com.rba.inifiniterecyclerview.model.entity.LoadingEntity;
import com.rba.inifiniterecyclerview.util.Constant;

import java.util.List;

/**
 * Created by Ricardo Bravo on 13/07/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private Context context;
    private List<Object> listObject;

    public ItemAdapter(Context context, List<Object> listObject) {
        this.context = context;
        this.listObject = listObject;
    }

    @Override
    public int getItemViewType(int position) {

        if(listObject.get(position) instanceof ErrorEntity){
            return Constant.TAG_ERROR;
        }

        if(listObject.get(position) instanceof ItemEntity){
            return Constant.TAG_ITEM;
        }

        if(listObject.get(position) instanceof LoadingEntity){
            return Constant.TAG_LOADING;
        }

        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        View view;

        switch (viewType){
            case Constant.TAG_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
                viewHolder = new ItemViewHolder(view);
                break;
            case Constant.TAG_LOADING:
                view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
            case Constant.TAG_ERROR:
                view = LayoutInflater.from(context).inflate(R.layout.item_error, parent, false);
                viewHolder = new ErrorViewHolder(view);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder && listObject.get(position) instanceof ItemEntity){
            ((ItemViewHolder) holder).lblDescription.setText(((ItemEntity)
                    listObject.get(position)).getDescription());
        }

        if(holder instanceof LoadingViewHolder){
            ((LoadingViewHolder) holder).pbLoading.setIndeterminate(true);
        }

        if(holder instanceof ErrorViewHolder && listObject.get(position) instanceof ErrorEntity){
            ((ErrorViewHolder) holder).lblError.setText(((ErrorEntity) listObject.get(position)).getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return listObject.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemViewHolder extends ViewHolder implements View.OnClickListener{

        TextView lblDescription;

        public ItemViewHolder(View itemView) {
            super(itemView);
            lblDescription = (TextView) itemView.findViewById(R.id.lblDescription);
            lblDescription.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lblDescription:
                    if(listObject.get(getAdapterPosition()) instanceof ItemEntity){
                        Log.i("x- msge ", ((ItemEntity) listObject.get(getAdapterPosition())).getDescription());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static class LoadingViewHolder extends ViewHolder {
        public ProgressBar pbLoading;

        public LoadingViewHolder(View v) {
            super(v);
            pbLoading = (ProgressBar) v.findViewById(R.id.pbLoading);
        }
    }

    class ErrorViewHolder extends ViewHolder implements View.OnClickListener{
        public TextView lblError;
        public Button btnError;

        public ErrorViewHolder(View itemView) {
            super(itemView);
            lblError = (TextView) itemView.findViewById(R.id.lblError);
            btnError = (Button) itemView.findViewById(R.id.btnError);
            btnError.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnError:
                    ((MainActivity)context).removeErrorItem();
                    ((MainActivity)context).loadMoreData();
                    break;
            }
        }
    }
}
