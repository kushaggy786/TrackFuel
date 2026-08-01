package com.trackfuel.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@JvmInline
value class DayKey(val isoDate: String) {
    fun toLocalDate(): LocalDate = LocalDate.parse(isoDate)
    companion object {
        fun from(date: LocalDate): DayKey = DayKey(date.toString())
    }
}

interface Clock {
    fun now(): Instant
}

class SystemClock : Clock {
    override fun now(): Instant = Instant.now()
}

interface DateProvider {
    fun zoneId(): ZoneId
    fun today(): DayKey
    fun dayKeyFor(instant: Instant = SystemClock().now()): DayKey
}

class DefaultDateProvider(
    private val clock: Clock = SystemClock(),
    private val timeZoneSupplier: () -> ZoneId = { ZoneId.systemDefault() }
) : DateProvider {
    override fun zoneId(): ZoneId = timeZoneSupplier()
    override fun today(): DayKey = DayKey.from(LocalDate.now(clock.now(), zoneId()))
    override fun dayKeyFor(instant: Instant): DayKey = DayKey.from(LocalDate.now(instant, zoneId()))
}

fun calculateAge(birthDate: LocalDate, onDate: LocalDate): Int =
    Period.between(birthDate, onDate).years.coerceAtLeast(0)
