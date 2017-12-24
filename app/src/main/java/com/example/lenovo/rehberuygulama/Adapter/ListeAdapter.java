package com.example.lenovo.rehberuygulama.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.rehberuygulama.Activities.SilDuzenleRehber;
import com.example.lenovo.rehberuygulama.Model.Kisi;
import com.example.lenovo.rehberuygulama.R;

import java.util.List;

/**
 * Created by lenovo on 23.12.2017.
 */

public class ListeAdapter extends RecyclerView.Adapter<ListeHolder> {

    private List<Kisi> mKisiler;
    private Context mContext;

    public ListeAdapter(List<Kisi> kisiler , Context context) {

    mKisiler = kisiler;
    mContext = context;

    }

    @Override
    public ListeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.list_row,parent,false);

        return new ListeHolder(v,mContext);
    }

    @Override
    public void onBindViewHolder(ListeHolder holder, int position) {

        Kisi kisi = mKisiler.get(position);
        holder.bindHolder(kisi);

    }

    @Override
    public int getItemCount() {

        return mKisiler.size();
    }
}

class ListeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView advesoyad;
    private TextView email;
    private TextView telno;
    private ImageView cinsiyet;
    private Context mcontext;
    private Kisi mKisi;

    public ListeHolder(View itemView,Context context) {
        super(itemView);

        itemView.setOnClickListener(this);

        advesoyad = (TextView)itemView.findViewById(R.id.advesoyad);
        email = (TextView)itemView.findViewById(R.id.email);
        telno = (TextView)itemView.findViewById(R.id.telno);
        cinsiyet = (ImageView)itemView.findViewById(R.id.cinsiyet);
        mcontext = context;
    }


    public void bindHolder(Kisi kisi){

        advesoyad.setText(kisi.getAdveSoyad());
        email.setText(kisi.getEmail());
        telno.setText(kisi.getTelNo());
        mKisi = kisi;
        if(kisi.getCinsiyet().equals("KADIN")){
            cinsiyet.setImageResource(R.drawable.woman);
        }
        else {
            cinsiyet.setImageResource(R.drawable.adam);
        }

    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent(mcontext, SilDuzenleRehber.class);
        i.putExtra("id",mKisi.getId());
        mcontext.startActivity(i);
    }
}
