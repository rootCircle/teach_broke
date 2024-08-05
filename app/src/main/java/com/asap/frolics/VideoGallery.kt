package com.asap.frolics

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asap.frolics.adapter.AlphaAdapter
import com.asap.frolics.model.AlphaChar


class VideoGallery : AppCompatActivity(), AlphaAdapter.onItemClickListener {
    private var recyclerView: RecyclerView? =null
    private var gridLayoutManager: GridLayoutManager? =null
    private var arrayList:ArrayList<AlphaChar> ? =null
    private var alphaAdapter: AlphaAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_gallary)

        recyclerView = findViewById(R.id.videoGalleryRecyclerView)
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        arrayList = ArrayList()
        arrayList = setDataInList()
        alphaAdapter = AlphaAdapter(applicationContext, arrayList!!,this)
        recyclerView?.adapter = alphaAdapter

    }

    private fun setDataInList() :ArrayList<AlphaChar>{
        var items:ArrayList<AlphaChar> = ArrayList()
        items.add(AlphaChar(R.drawable.ic_baseline_slow_motion_video_24,"Video 1"))
        items.add(AlphaChar(R.drawable.ic_firebase_logo,"Video 2"))
        items.add(AlphaChar(R.drawable.ic_baseline_assignment_24,"Video 3"))
        items.add(AlphaChar(R.drawable.ic_baseline_assignment_24,"Video 4"))
        items.add(AlphaChar(R.drawable.ic_baseline_assignment_24,"Video 5"))
        return items
    }

    override fun onClick(position: Int) {
        Toast.makeText(applicationContext, arrayList?.get(position)?.alphaChar, Toast.LENGTH_SHORT).show()

        var videoDesc =  """
            In this tutorial you will see the prototype of an  LCD 1602a connected to Arduino.
            Topic - LCD
            Chapter - Arduino
            """.trimIndent()

        var intent = Intent(applicationContext , VideoPlayer::class.java).apply {
            putExtra("Title",arrayList?.get(position)?.alphaChar)
            putExtra("API Key","<YOUTUBE API KEY>")
            putExtra("VideoID","kY-sNaWMVcY")
            putExtra("Description", videoDesc)
        }
        startActivity(intent)
    }

    override fun onLongClick(position: Int) {
        Toast.makeText(applicationContext,"Long Click ${arrayList?.get(position)?.alphaChar}",
            Toast.LENGTH_SHORT).show()
    }
}