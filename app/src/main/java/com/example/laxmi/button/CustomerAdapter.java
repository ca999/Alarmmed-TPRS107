package com.example.laxmi.button;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerAdapter extends ArrayAdapter<String> {
    int img [];
    public CustomerAdapter(@NonNull Context context, String [] print,int [] images) {
        super(context,R.layout.custom_row, print);
        img=images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myLayoutInflater=LayoutInflater.from(getContext());
        View customView=myLayoutInflater.inflate(R.layout.custom_row,parent,false);
        String singleItem=getItem(position);
        TextView mytext=(TextView)customView.findViewById(R.id.textview);//mytext-textview
        ImageView im=(ImageView)customView.findViewById(R.id.imageview);//n
        mytext.setText(singleItem);
        im.setImageResource(img[position]);
        return customView;



    }
}
