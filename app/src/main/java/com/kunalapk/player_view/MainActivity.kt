package com.kunalapk.player_view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.kunalapk.smartexoplayer.SmartExoPlayerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val smartExoPlayerView = findViewById<SmartExoPlayerView>(R.id.playerView)


        smartExoPlayerView.apply {
            setMuteUnMuteIcon(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_speaker_mute),ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_speaker),  ConstraintLayout.LayoutParams(100, 100).apply {
                bottomToBottom = ConstraintSet.PARENT_ID
                rightToRight = ConstraintSet.PARENT_ID
            }
            )
        }

        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        val mediaSource = ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(Uri.parse("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4?_=1"))

        smartExoPlayerView.loadMedia(mediaSource, "https://www.w3schools.com/html/pic_trulli.jpg")
    }

}