package com.example.myfitnessapp.viewmodels.utils

import android.os.Build.VERSION.SDK_INT
import android.content.Context
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}