# SmartExoPlayer

![Works with Android](https://img.shields.io/badge/Works_with-Android-green?style=flat-square)
[![](https://jitpack.io/v/Kunalapk/SmartExoPlayer.svg)](https://jitpack.io/#Kunalapk/SmartExoPlayer)
[![YourActionName Actions Status](https://github.com/Kunalapk/SmartExoPlayer/workflows/Android%20CI/badge.svg)](https://github.com/Kunalapk/SmartExoPlayer/actions)


### Let's do it quickly!
---------------------------
The simplest way to start

```kotlin

allprojects {
     repositories {
	maven { url 'https://jitpack.io' }
     }
}


dependencies {
     implementation 'com.github.Kunalapk:SmartExoPlayer:TAG'
}
```


```xml
<com.kunalapk.smartexoplayer.SmartExoPlayerView
	android:id="@+id/playerView"
	android:layout_width="match_parent"
	android:layout_height="300dp"
	android:background="@color/black"
	app:setPlayIcon="@drawable/ic_play_button"
	app:setPauseIcon="@drawable/ic_pause"
	/>
```
