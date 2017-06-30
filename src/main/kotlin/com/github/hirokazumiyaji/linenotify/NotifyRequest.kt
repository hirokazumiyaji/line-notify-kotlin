package com.github.hirokazumiyaji.linenotify

data class NotifyRequest(val message: String,
                         val imageThumbnail: String? = null,
                         val imageFullSize: String? = null,
                         val stickerPackageId: Int? = null,
                         val stickerId: Int? = null) {

    fun toFormData(): Map<String, String> {
        val map: Map<String, String> = mapOf("message" to message)
        imageThumbnail?.let {
            map.plus("imageThumbnail" to imageThumbnail)
        }
        imageFullSize?.let {
            map.plus("imageFullsize" to imageFullSize)
        }
        stickerPackageId?.let {
            map.plus("stickerPackageId" to stickerPackageId)
        }
        stickerId?.let {
            map.plus("stickerId" to stickerId)
        }
        return map
    }

}