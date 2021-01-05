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


## layout example
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

## LICENSE
	Copyright 2020 Kunal Pasricha

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
