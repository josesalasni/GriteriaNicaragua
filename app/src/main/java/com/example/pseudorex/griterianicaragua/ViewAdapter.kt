package com.example.pseudorex.griterianicaragua

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.card_view.view.*

class ViewAdapter (val SongsList: List<Songs>, val context: Context) : RecyclerView.Adapter<ViewAdapter.ViewHolder> () {

    var Sonz = SongsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)  )
    }

    override fun getItemCount(): Int {
        return SongsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems( SongsList[position] )

        //Handle button click
        holder.itemView.buttonStart.setOnClickListener() {
            var intent = Intent(context,  SongActivity::class.java)
            intent.putExtra("Song_name", SongsList[position].name)
            intent.putExtra("mp3_name", SongsList[position].mp3)
            intent.putExtra("lyric", SongsList[position].lyrics)

            context.startActivity(intent)
        }
    }

    //Notify to filter the Songs
    fun updateList () {
        notifyDataSetChanged()
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bindItems (song: Songs) {
            itemView.Song_name.text = song.name

            //itemView.champion_name.text = champion.name
            //Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/" + versionNumber + "/img/champion/"+ champion.key + ".png" ).into(itemView.champion_image)
        }
    }
}