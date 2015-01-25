package com.hackathon.team6.utlities.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hackathon.team6.R;
import com.hackathon.team6.dataBase.dataType.Transaction;
import com.hackathon.team6.utlities.image.BitmapManager;
import com.hackathon.team6.utlities.image.PictureFileManager;

import java.util.ArrayList;

/**
 * Created by Colin on 1/24/2015.
 */
public class GridViewAdapter extends BaseAdapter {

    ArrayList<AsyncTask> tasks;
    Transaction transaction;
    Context context;
    LayoutInflater inflater;

    public GridViewAdapter(Context context, Transaction transaction) {
        super();
        this.context = context;
        this.transaction = transaction;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tasks = new ArrayList<AsyncTask>();
    }

    public void stopAllTasks(){
        for(AsyncTask asyncTask : tasks){
            asyncTask.cancel(true);
        }
        tasks.clear();
    }

    @Override
    public int getCount() {
        if(transaction == null || transaction.getImages() == null){
            return 0;
        }
        return transaction.getImages().size()+1;
    }

    @Override
    public Object getItem(int i) {
        return transaction.getImages().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView = null;

        if(i == 0){
            newView = inflater.inflate(R.layout.gridview_add_item,viewGroup,false);
        }
        else {
            //-1 accounts for add photo position
            final int position = i - 1;

            newView = inflater.inflate(R.layout.gridview_item,viewGroup,false);

            ImageView iv = (ImageView) newView.findViewById(R.id.gridView_item_imageView);

            Button b = (Button) newView.findViewById(R.id.gridView_item_button);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction.getImages().remove(position);
                    notifyDataSetChanged();
                }
            });

            if(transaction.getImages().get(position).getUri() != null){
                tasks.add(BitmapManager.setImageView(iv, context, transaction.getImages().get(position).getUri(), 120, 120));
            }

            iv.setFocusable(false);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return newView;
    }
}