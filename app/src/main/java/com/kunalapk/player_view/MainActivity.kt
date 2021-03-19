package com.kunalapk.player_view

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.kunalapk.smartexoplayer.SmartExoPlayerView
import com.kunalapk.smartexoplayer.utils.SmartLogger


class MainActivity : AppCompatActivity() {

    companion object{
        val TAG:String = javaClass.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val smartExoPlayerView = findViewById<SmartExoPlayerView>(R.id.playerView)


        smartExoPlayerView.apply {
            setMuteUnMuteIcon(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_speaker_mute),ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_speaker),  ConstraintLayout.LayoutParams(100, 100).apply {
                bottomToBottom = ConstraintSet.PARENT_ID
                rightToRight = ConstraintSet.PARENT_ID
            })
            addPlayerListener(playerListener)
        }

        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4?_=1"))

        smartExoPlayerView.loadMedia(mediaSource, "https://www.w3schools.com/html/pic_trulli.jpg")
    }

    private val playerListener: Player.EventListener = object: Player.EventListener{

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            super.onLoadingChanged(isLoading)
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            if(isPlaying){
            }else{
            }
            SmartLogger.debug(TAG,"onIsPlayingChangedMainActivity - $isPlaying")

        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
        }
    }

}