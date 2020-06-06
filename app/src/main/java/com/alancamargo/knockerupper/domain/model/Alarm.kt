package com.alancamargo.knockerupper.domain.model

import java.time.DayOfWeek
import java.util.*

data class Alarm(
        val id: String = UUID.randomUUID().toString(),
        val label: String,
        val triggerTime: Long,
        val ringtone: String?,
        val frequency: List<DayOfWeek>,
        val vibrate: Boolean,
        val deleteOnDismiss: Boolean,
        val isActive: Boolean,
        val code: String?
) {

    fun hasCode() = code != null

}