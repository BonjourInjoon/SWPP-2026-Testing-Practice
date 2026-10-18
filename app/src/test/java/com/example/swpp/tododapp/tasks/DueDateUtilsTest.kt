package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {
    @Test
    fun dueDateLabel_completedNoDate_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(null, today, true)
        // Then
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_completedWithDate_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-22"), today, true)
        // Then
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_activeNoDate_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(null, today, false)
        // Then
        assertEquals("", actual)
    }

    @Test
    fun dueDateLabel_today_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-15"), today, false)
        // Then
        assertEquals("D-Day", actual)
    }

    @Test
    fun dueDateLabel_futureSeven_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-22"), today, false)
        // Then
        assertEquals("D-7", actual)
    }

    @Test
    fun dueDateLabel_pastSeven_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-08"), today, false)
        // Then
        assertEquals("D+7", actual)
    }

    @Test
    fun dueDateLabel_tomorrow_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-16"), today, false)
        // Then
        assertEquals("D-1", actual)
    }

    @Test
    fun dueDateLabel_yesterday_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-14"), today, false)
        // Then
        assertEquals("D+1", actual)
    }

    @Test
    fun dueDateLabel_monthAhead_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-02-15"), today, false)
        // Then
        assertEquals("D-31", actual)
    }

    @Test
    fun dueDateLabel_monthBehind_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-02-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-15"), today, false)
        // Then
        assertEquals("D+31", actual)
    }

    @Test
    fun dueDateLabel_yearAhead_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2027-01-15"), today, false)
        // Then
        assertEquals("D-365", actual)
    }

    @Test
    fun dueDateLabel_yearBehind_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2027-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-01-15"), today, false)
        // Then
        assertEquals("D+365", actual)
    }

    @Test
    fun dueDateLabel_leapCrossing_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2028-02-28")
        // When
        val actual = dueDateLabel(LocalDate.parse("2028-03-01"), today, false)
        // Then
        assertEquals("D-2", actual)
    }

    @Test
    fun dueDateLabel_leapReverse_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2028-03-01")
        // When
        val actual = dueDateLabel(LocalDate.parse("2028-02-28"), today, false)
        // Then
        assertEquals("D+2", actual)
    }

    @Test
    fun dueDateLabel_leapYearAhead_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2028-01-15")
        // When
        val actual = dueDateLabel(LocalDate.parse("2029-01-15"), today, false)
        // Then
        assertEquals("D-366", actual)
    }

    @Test
    fun dueDateLabel_monthEnd_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-01-31")
        // When
        val actual = dueDateLabel(LocalDate.parse("2026-02-01"), today, false)
        // Then
        assertEquals("D-1", actual)
    }

    @Test
    fun dueDateLabel_yearEnd_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2026-12-31")
        // When
        val actual = dueDateLabel(LocalDate.parse("2027-01-01"), today, false)
        // Then
        assertEquals("D-1", actual)
    }

    @Test
    fun dueDateLabel_leapDay_expectedLabel() {
        // Given: exact dates and expected label come from the reviewed spec.
        val today = LocalDate.parse("2028-02-28")
        // When
        val actual = dueDateLabel(LocalDate.parse("2028-02-29"), today, false)
        // Then
        assertEquals("D-1", actual)
    }

}
