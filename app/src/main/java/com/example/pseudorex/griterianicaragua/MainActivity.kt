package com.example.pseudorex.griterianicaragua

import android.icu.util.TimeUnit
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import com.example.pseudorex.griterianicaragua.R.attr.background
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ViewAdapter : ViewAdapter

    var SongList = ArrayList<Songs>()
    var SongListSearch = ArrayList<Songs>()
    var mNewVersion = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)

        setData()


    }

    fun setData() {
        var db = dbHelper(this.applicationContext).getDatabase()

        //Get all Songs data
        val query = "SELECT * FROM Songs"
        val cursor = db.rawQuery(query, null)

        //Loop for insert in list
        try {
            while (cursor.moveToNext()) {
                var id = Integer.parseInt(cursor.getString(0))
                var name = cursor.getString(1)
                var mp3name = cursor.getString(2)
                var lyric = cursor.getString(3)

                SongList.add(
                    Songs(
                        id = id,
                        name = name,
                        mp3 = mp3name,
                        lyrics = lyric
                    )
                )

                SongListSearch.add(
                    Songs(
                        id = id,
                        name = name,
                        mp3 = mp3name,
                        lyrics = lyric
                    )
                )
            }
        } finally {
            cursor.close();
        }

        db.close()


        //Filling the recycler View
        ViewAdapter = ViewAdapter(SongListSearch, this@MainActivity)

        Recycler.adapter = ViewAdapter

        Recycler.layoutManager = LinearLayoutManager(this@MainActivity)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        //Handle the search text
        val searchItem = menu.findItem(R.id.toolbar_search)

        var searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                var text = newText.toLowerCase()
                SongListSearch.clear()

                //If the data has finished filling the recyclerview
                if (SongList.isEmpty() == false)
                {
                    for(i in SongList.indices) {
                        if (SongList[i].name.toLowerCase().contains(text)  ){
                            SongListSearch.add(
                                Songs(
                                    id = SongList[i].id,
                                    name = SongList[i].name ,
                                    mp3 = SongList[i].mp3,
                                    lyrics = SongList[i].lyrics
                                )
                            )
                        }
                    }


                    ViewAdapter.notifyDataSetChanged()
                }


                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })
        return true
    }
}