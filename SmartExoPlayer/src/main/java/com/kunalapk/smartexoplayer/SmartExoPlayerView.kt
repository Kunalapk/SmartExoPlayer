package com.kunalapk.smartexoplayer

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.kunalapk.smartexoplayer.extensions.loadImage
import com.kunalapk.smartexoplayer.utils.SmartLogger

class SmartExoPlayerView : PlayerView{

    private var TAG = javaClass.simpleName

    private var mPlayer:SimpleExoPlayer? = null
    private var mPlayBackState:Int = Player.STATE_IDLE

    private var posterView:AppCompatImageView? = null
    private var clMuteUnMuteContainer:ConstraintLayout? = null


    constructor(context: Context):super(context){
        addPlayerView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VideoPlayerView, 0, 0)

        addPlayerView()
    }

    private fun addPlayerView(){
        mPlayer = SimpleExoPlayer.Builder(context).setLoadControl(DefaultLoadControl()).setTrackSelector(DefaultTrackSelector()).build()
        player = mPlayer
        useController = true
        mPlayer?.addListener(playerListener)
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
            if(isPlaying){
                posterView?.visibility = View.GONE
                clMuteUnMuteContainer?.visibility = View.VISIBLE
            }else{
                clMuteUnMuteContainer?.visibility = View.GONE
            }
            SmartLogger.debug(TAG,"onIsPlayingChanged - $isPlaying")

        }

        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            SmartLogger.debug(TAG,"onPlayerError")

        }
    }

    fun addPlayerListener(playerEventListener:Player.EventListener){
        mPlayer?.addListener(playerEventListener)
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when(it){

        }
    }


    fun loadMedia(mediaSource: MediaSource){
        mPlayer?.prepare(mediaSource)
    }

    fun loadMedia(mediaSource: MediaSource,posterUrl:String?){
        setPoster(url = posterUrl)
        mPlayer?.prepare(mediaSource)
    }

    fun loadMedia(mediaSource: MediaSource, posterUrl:String?, posterScaleType: ImageView.ScaleType){
        setPoster(url = posterUrl , scaleType = posterScaleType)
        mPlayer?.prepare(mediaSource)
    }

    fun loadMedia(mediaSource: MediaSource,drawable: Int){
        setPoster(drawable = drawable)
        mPlayer?.prepare(mediaSource)
    }

    fun setPoster(drawable: Int){
        if(posterView==null){
            posterView = AppCompatImageView(context)
        }
        if(childCount>0){
            addView(posterView,1,getConstraintLayoutCenterParams())
        }else{
            addView(posterView,childCount,getConstraintLayoutCenterParams())
        }
        posterView?.setImageDrawable(ContextCompat.getDrawable(context,drawable))
    }

    fun setPoster(url:String?){
        setPoster(url,ImageView.ScaleType.CENTER_CROP)
    }

    fun setPoster(url:String?, scaleType:ImageView.ScaleType){
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
            posterView?.scaleType = scaleType
        }
    }

    fun reset(){

    }

    fun destroy(){
        mPlayer?.stop()
        mPlayer = null
        posterView = null
    }

}