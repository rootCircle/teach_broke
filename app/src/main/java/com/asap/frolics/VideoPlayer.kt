package com.asap.frolics

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayer : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        /* Declaring Variables and
           Getting Data from VideoGallery Class
        */
        val videoID:String = intent.getStringExtra("VideoID").toString()
        val key:String = intent.getStringExtra("KEY").toString()
        val title:String = intent.getStringExtra("Title").toString()
        val description:String = intent.getStringExtra("Description").toString()

        // Initialising parameters in layout
        tvVideoDescription.text = description
        tvVideoTitle.text = title

        val playerView=findViewById<YouTubePlayerView>(R.id.youTubePlayerView)
        playerView.initialize(key, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, restored: Boolean) {
                if (!restored) {
                    youTubePlayer.cueVideo(videoID)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                Toast.makeText(applicationContext, "Something went wrong..!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}