package com.example.swpp.tododapp.tasks

import android.view.LayoutInflater
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.swpp.tododapp.data.Task
import com.example.swpp.tododapp.databinding.TaskItemBinding
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {
    private fun scenario(block: (TaskItemBinding, LocalDate) -> Unit) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        var failure: Throwable? = null
        instrumentation.runOnMainSync {
            val today = LocalDate.now()
            val binding = TaskItemBinding.inflate(LayoutInflater.from(instrumentation.targetContext))
            try {
                // Given: a reused, initially visible label.
                binding.dueDateText.text = "old"
                binding.dueDateText.visibility = View.VISIBLE
                block(binding, today)
                assertEquals("Discard a run crossing local midnight", today, LocalDate.now())
            } catch (error: Throwable) {
                failure = error
            } finally {
                binding.unbind()
            }
        }
        failure?.let { throw it }
    }

    private fun bind(binding: TaskItemBinding, completed: Boolean, due: LocalDate?) {
        // When: use the real layout and generated data binding, including its adapter.
        binding.task = Task(title = "Spec task", description = "Binding test", isCompleted = completed, dueDate = due)
        binding.executePendingBindings()
    }

    private fun label(binding: TaskItemBinding, expected: String) {
        // Then: independently specified text and visibility, never computed by the SUT.
        assertEquals(expected, binding.dueDateText.text.toString())
        if (expected.isEmpty()) {
            assertNotEquals(View.VISIBLE, binding.dueDateText.visibility)
        } else {
            assertEquals(View.VISIBLE, binding.dueDateText.visibility)
        }
    }

    @Test
    fun setDueDateLabel_completedNoDate_expectedView() = scenario { b, today ->
        bind(b, true, null)
        label(b, "CLEAR")
    }

    @Test
    fun setDueDateLabel_completedWithDate_expectedView() = scenario { b, today ->
        bind(b, true, today.plusDays(7))
        label(b, "CLEAR")
    }

    @Test
    fun setDueDateLabel_today_expectedView() = scenario { b, today ->
        bind(b, false, today)
        label(b, "D-Day")
    }

    @Test
    fun setDueDateLabel_tomorrow_expectedView() = scenario { b, today ->
        bind(b, false, today.plusDays(1))
        label(b, "D-1")
    }

    @Test
    fun setDueDateLabel_yesterday_expectedView() = scenario { b, today ->
        bind(b, false, today.minusDays(1))
        label(b, "D+1")
    }

    @Test
    fun setDueDateLabel_activeNoDate_expectedView() = scenario { b, today ->
        bind(b, false, null)
        label(b, "")
    }

    @Test
    fun setDueDateLabel_completedToActiveNoDate_expectedView() = scenario { b, today ->
        bind(b, true, null)
        label(b, "CLEAR")
        bind(b, false, null)
        label(b, "")
    }

    @Test
    fun setDueDateLabel_nullAfterCompleted_expectedView() = scenario { b, today ->
        bind(b, true, null)
        label(b, "CLEAR")
        b.task = null
        b.executePendingBindings()
        label(b, "")
    }

    @Test
    fun setDueDateLabel_dateCleared_expectedView() = scenario { b, today ->
        bind(b, false, today.plusDays(1))
        label(b, "D-1")
        bind(b, false, null)
        label(b, "")
    }

    @Test
    fun setDueDateLabel_hiddenToDated_expectedView() = scenario { b, today ->
        b.dueDateText.text = ""
        b.dueDateText.visibility = View.GONE
        bind(b, false, today.plusDays(1))
        label(b, "D-1")
    }

    @Test
    fun setDueDateLabel_completedToActiveDated_expectedView() = scenario { b, today ->
        bind(b, true, today.plusDays(1))
        label(b, "CLEAR")
        bind(b, false, today.plusDays(1))
        label(b, "D-1")
    }

}
