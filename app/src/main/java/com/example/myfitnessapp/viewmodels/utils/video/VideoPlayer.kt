package com.example.myfitnessapp.viewmodels.utils.video

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun VideoPlayer(videoUrl: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                //settings.javaScriptEnabled = true
                loadUrl(videoUrl)
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}