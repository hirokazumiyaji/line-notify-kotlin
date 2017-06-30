package com.github.hirokazumiyaji.linenotify

data class StatusResponse(val status: Int,
                          val message: String,
                          val targetType: String? = null,
                          val target: String? = null)
