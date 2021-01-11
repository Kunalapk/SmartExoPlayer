package com.kunalapk.smartexoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.kunalapk.smartexoplayer.extensions.loadImage
import com.kunalapk.smartexoplayer.utils.SmartLogger

class SmartExoPlayerView : ConstraintLayout{

    private var TAG = javaClass.simpleName

    private var player:SimpleExoPlayer? = null
    private var playerView:PlayerView? = null
    private var mPlayBackState:Int = Player.STATE_IDLE



    private var controllerContainer:ConstraintLayout? = null
    private var controllerView:FrameLayout? = null
    private var playIconView:AppCompatImageView? = null
    private var pauseIconView:AppCompatImageView? = null
    private var posterView:AppCompatImageView? = null

    constructor(context: Context):super(context){ }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VideoPlayerView, 0, 0)


        addPlayerView()
        setPlayIcon(typedArray.getDrawable(R.styleable.VideoPlayerView_setPlayIcon))
        setPauseIcon(typedArray.getDrawable(R.styleable.VideoPlayerView_setPauseIcon))
    }

    private fun addPlayerView(){
        removeAllViews()
        player = SimpleExoPlayer.Builder(context).setLoadControl(DefaultLoadControl()).setTrackSelector(DefaultTrackSelector()).build()
        playerView = PlayerView(context)
        playerView?.player = player
        playerView?.useController = false
        player?.addListener(playerListener)
        addView(playerView,getConstraintLayoutCenterParams())
    }


    private fun getConstraintLayoutCenterParams():ConstraintLayout.LayoutParams{
        return getConstraintLayoutCenterParams(null,null)
    }

    private fun getConstraintLayoutCenterParams(mWidth:Int?,mHeight:Int?):ConstraintLayout.LayoutParams{
        return ConstraintLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT).apply {
            if(mWidth!=null){
                width = mWidth
            }
            if(mHeight!=null){
                height = mHeight
            }
            bottomToBottom = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            startToStart = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
        }
    }

    private val playerListener:Player.EventListener = object:Player.EventListener{

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            mPlayBackState = playbackState
            SmartLogger.debug(TAG,"onPlayerStateChanged - $playWhenReady, ${playbackState}")

        }

        override fun onLoadingChanged(isLoading: Boolean) {
            super.onLoadingChanged(isLoading)
            SmartLogger.debug(TAG,"onLoadingChanged - $isLoading")

        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            togglePlayAndPauseIcon(isPlaying)
            SmartLogger.debug(TAG,"onIsPlayingChanged - $isPlaying")

        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            SmartLogger.debug(TAG,"onPlayerError")

        }
    }

    fun setPlayIcon(drawable:Drawable?){
        if(playIconView == null && drawable!=null){
            playIconView = AppCompatImageView(context)
            initControllerView()
            controllerContainer?.addView(playIconView,getViewChildCount(controllerContainer),getConstraintLayoutCenterParams(180,180))
            playIconView?.setImageDrawable(drawable)
            playIconView?.setOnClickListener(onClickListener)
        }
    }

    fun setPauseIcon(drawable: Drawable?){
        if(pauseIconView == null && drawable!=null){
            pauseIconView = AppCompatImageView(context)
            initControllerView()
            controllerContainer?.addView(pauseIconView,getViewChildCount(controllerContainer),getConstraintLayoutCenterParams(180,180))
            pauseIconView?.setImageDrawable(drawable)
            pauseIconView?.setOnClickListener(onClickListener)
            pauseIconView?.visibility = View.GONE
        }
    }

    private fun getViewChildCount(constraintLayout:ConstraintLayout?):Int{
        if(constraintLayout!=null){
            return constraintLayout.childCount
        }else{
            return 0
        }
    }
    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when(it){
            playIconView -> {
                togglePlayAndPause(true)
            }
            pauseIconView -> {
                togglePlayAndPause(false)
            }
            controllerView -> {
                toggleControllerView()
            }
        }
    }


    private fun toggleControllerView(){
        if(controllerContainer?.isVisible==true){
            controllerContainer?.visibility = View.INVISIBLE
        }else{
            controllerContainer?.visibility = View.VISIBLE
        }
    }
    private fun togglePlayAndPause(play: Boolean){
        if(play){
            if(mPlayBackState==Player.STATE_ENDED){
                player?.seekTo(0)
            }
            player?.playWhenReady = true
        }else{
            player?.playWhenReady = false
        }
    }

    private fun togglePlayAndPauseIcon(isPlaying:Boolean){
        if(isPlaying){
            playIconView?.visibility = View.GONE
            pauseIconView?.visibility = View.VISIBLE
            posterView?.visibility = View.GONE
        }else{
            playIconView?.visibility = View.VISIBLE
            pauseIconView?.visibility = View.GONE
            posterView?.visibility = View.GONE
        }
    }


    fun loadMedia(mediaSource: MediaSource){
        player?.prepare(mediaSource)
    }

    fun loadMedia(mediaSource: MediaSource,posterUrl:String?){
        setPosterUrl(url = posterUrl)
        player?.prepare(mediaSource)
    }

    private fun initControllerView(){
        if(controllerView==null){
            controllerView = FrameLayout(context)
            controllerContainer = ConstraintLayout(context)
            controllerView?.addView(controllerContainer,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            addView(controllerView,childCount,getConstraintLayoutCenterParams())
            controllerView?.setOnClickListener(onClickListener)
        }
    }

    fun setPosterUrl(url:String?){
        if(url!=null){
            if(posterView==null){
                posterView = AppCompatImageView(context)
            }
            if(childCount>0){
                addView(posterView,1,getConstraintLayoutCenterParams())
            }else{
                addView(posterView,childCount,getConstraintLayoutCenterParams())
            }
            posterView?.loadImage(url)
        }
    }

    fun reset(){

    }

    fun destroy(){
        playerView?.player?.stop()
        player = null
        playerView = null
        playIconView = null
        pauseIconView = null
        posterView = null
    }

}