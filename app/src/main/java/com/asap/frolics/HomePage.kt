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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_page.*


class HomePage : AppCompatActivity(), AlphaAdapter.onItemClickListener {
    private var recyclerView:RecyclerView ? =null
    private var gridLayoutManager:GridLayoutManager ? =null
    private var arrayList:ArrayList<AlphaChar> ? =null
    private var alphaAdapter:AlphaAdapter ? =null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        auth= Firebase.auth

        recyclerView = findViewById(R.id.my_recycler_view)
        gridLayoutManager = GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        arrayList = ArrayList()
        arrayList = setDataInList()
        alphaAdapter = AlphaAdapter(applicationContext, arrayList!!,this)
        recyclerView?.adapter = alphaAdapter
        val user: FirebaseUser? = auth.currentUser
        getUserInfo(user)
        buttonHomepageLogout.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intent)
        }
    }

    private fun setDataInList() :ArrayList<AlphaChar>{
        val items:ArrayList<AlphaChar> = ArrayList()
        items.add(AlphaChar(R.drawable.ic_baseline_slow_motion_video_24,"Video Player"))
        items.add(AlphaChar(R.drawable.ic_firebase_logo,"Video Lectures"))
        items.add(AlphaChar(R.drawable.ic_baseline_assignment_24,"Assignment"))
        items.add(AlphaChar(R.drawable.ic_firebase_logo,"Sample 1"))
        items.add(AlphaChar(R.drawable.ic_firebase_logo,"Sample 2"))
        items.add(AlphaChar(R.drawable.ic_firebase_logo,"Sample 3"))
        return items
    }

    override fun onClick(position: Int) {
        when (position) {
            0 -> {
                startActivity(Intent(applicationContext,VideoPlayer::class.java))
            }
            1 -> {
                startActivity(Intent(applicationContext,VideoGallery::class.java))
            }
            else -> {
                Toast.makeText(applicationContext, arrayList?.get(position)?.alphaChar, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLongClick(position: Int) {
        Toast.makeText(applicationContext,"Long Click ${arrayList?.get(position)?.alphaChar}",Toast.LENGTH_SHORT).show()
    }

    private fun getUserInfo(user: FirebaseUser?){
        val mail:String
        val uid:String
        if(user!=null) {
            mail=user.email.toString()
            uid= user.uid
            Toast.makeText(applicationContext,mail,Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext,uid,Toast.LENGTH_SHORT).show()
        }
    }
}