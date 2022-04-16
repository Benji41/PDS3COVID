package com.benjaminreynoso.covidamigopds3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benjaminreynoso.covidamigopds3.Model.Event;
import com.benjaminreynoso.covidamigopds3.R;
import com.bumptech.glide.Glide;

import java.util.List;


public class AdapterAvEvents extends RecyclerView.Adapter<AdapterAvEvents.MyViewHolder> {
    private Context mContext;
    private List<Event> mEventList;
    private OnEventListener mOnEventListener;

    public AdapterAvEvents(Context context, List<Event> eventList, OnEventListener eventListener) {
        mContext = context;
        mEventList= eventList;
        this.mOnEventListener = eventListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.event_item,parent,false);
        return new MyViewHolder(v,mOnEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.title.setText(mEventList.get(position).getNombre());

        holder.date.setText(holder.date.getText()+" "+mEventList.get(position).getFecha().substring(0,mEventList.get(position).getFecha().indexOf('T')));
        holder.minAge.setText(holder.minAge.getText()+" "+String.valueOf(mEventList.get(position).getEdad_min()));
        holder.cap.setText(holder.cap.getText()+" "+String.valueOf(mEventList.get(position).getAforo()));

        //codigo para cargar imagenes
        //Glide.with(mContext).load(mEventList.get(position).getfoto().getimage()).into(holder.img);
    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,date,minAge,cap;
        OnEventListener OnEventListener;
        public MyViewHolder(@NonNull View itemView, OnEventListener onEventListener) {
            super(itemView);
            title = itemView.findViewById(R.id.vTitle);
            date = itemView.findViewById(R.id.vDate);
            minAge = itemView.findViewById(R.id.vMinAge);
            cap = itemView.findViewById(R.id.vCap);
            OnEventListener = onEventListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            OnEventListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener{
        void onEventClick(int position);
    }
}
