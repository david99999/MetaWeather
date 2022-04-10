package com.demo.metaweather.utils

import android.widget.ImageView
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import javax.inject.Inject
import coil.ImageLoader as CoilImageLoader

interface ImageLoader {
    fun loadImageIntoImageView(path: String, imageView: ImageView)
}

class CoilImageLoader @Inject constructor() : ImageLoader {

    override fun loadImageIntoImageView(path: String, imageView: ImageView) {

        val imageLoader = CoilImageLoader.Builder(imageView.context)
            .componentRegistry { add(SvgDecoder(imageView.context)) }
            .build()

        val request = ImageRequest.Builder(imageView.context)
            .data(path)
            .target(imageView)
            .build()

        imageLoader.enqueue(request)
    }
}
