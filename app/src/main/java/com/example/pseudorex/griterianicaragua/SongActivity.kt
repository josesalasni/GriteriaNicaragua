package com.example.pseudorex.griterianicaragua

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_song.*
import java.io.IOException


class SongActivity : AppCompatActivity() {

    var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        setSupportActionBar(ToolbarSongs)

        //get data selected from the main activity

        val songName = intent.getStringExtra("Song_name")
        val Mp3Name = intent.getStringExtra("mp3_name")
        val lyric = intent.getStringExtra("lyric")

        supportActionBar?.title = songName

        textLyric.text = lyric


        //Check if exist or not
        val checkExistence =
            applicationContext.resources.getIdentifier(Mp3Name, "raw", applicationContext.packageName)

        if (checkExistence == 0) {
            Log.i("mensaje", "no hay mp3 de esta canción")
        }
        else{
            buttonPlay.visibility = View.VISIBLE
            //When the user clicks the button
            buttonPlay.setOnClickListener {

                //Handle play and pause button
                if (!mediaPlayer.isPlaying)
                {
                    buttonPlay.setImageResource(R.drawable.baseline_stop_24)
                    mediaPlayer = MediaPlayer.create(applicationContext, checkExistence)
                }

                if (mediaPlayer.isPlaying)
                {
                    buttonPlay.setImageResource(R.drawable.ic_play_icon)
                    mediaPlayer.stop()
                }


                mediaPlayer.start()
            }


        }
    }

    override fun onPause() {
        super.onPause()

        if (mediaPlayer != null)
        {
            mediaPlayer.stop()
        }
    }

    override fun onStop() {
        super.onStop()

        if (mediaPlayer != null)
        {
            mediaPlayer.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mediaPlayer != null)
        {
            mediaPlayer.stop()
            mediaPlayer.release()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (mediaPlayer != null && mediaPlayer.isPlaying)
        {
            mediaPlayer.stop()
        }
    }
}
