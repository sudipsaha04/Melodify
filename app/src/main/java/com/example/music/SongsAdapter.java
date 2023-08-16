package com.example.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Model.UploadSong;

import java.io.IOException;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsAdapterViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<UploadSong> arrayListSongs;

    public SongsAdapter(Context context,List<UploadSong> arrayListSongs,RecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.arrayListSongs = arrayListSongs;
        this.recyclerViewInterface = recyclerViewInterface;

    }


    @Override
    public SongsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.song_item,viewGroup,false);
       return new SongsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongsAdapterViewHolder holder, int i) {
        UploadSong uploadSong = arrayListSongs.get(i);
        holder.titleTxt.setText(uploadSong.getSongTitle());
        holder.durationTxt.setText(uploadSong.getSongDuration());


    }

    @Override
    public int getItemCount() {
        return arrayListSongs.size();
    }

    public class SongsAdapterViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView titleTxt,durationTxt;


        public SongsAdapterViewHolder(View itemView) {
            super(itemView);

            titleTxt = (TextView) itemView.findViewById(R.id.song_title);
            durationTxt =(TextView) itemView.findViewById(R.id.song_duration);
            itemView.setOnClickListener(this);
        }

         @Override
        public void onClick(View v) {

            /*try {
                ((ShowSongsActivity)context).playSong(arrayListSongs,getAdapterPosition());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            if(recyclerViewInterface!=null)
            {
                int pos = getAdapterPosition();

                if(pos!= RecyclerView.NO_POSITION)
                {
                    recyclerViewInterface.onItemClick(pos);
                }
            }
        }

        /*@Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                ((ShowSongsActivity) context).playSong(arrayListSongs, adapterPosition);
            }
        }*/
    }
}
