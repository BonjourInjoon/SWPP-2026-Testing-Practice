# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Render the task list due-date label from task state. Completed tasks show CLEAR; active tasks use calendar-day labels; active tasks without a date and a null task leave no visible label. Rebinding the same view must replace prior content.

**Pre-conditions**
- Call on the Android main thread with a valid TextView. task may be null. Concrete examples below use local today=2026-01-15. Instrumentation tests capture device-local today once per test and use today +/- 1; discard a run crossing midnight. Observe exact text and visible/non-visible state; the intent does not choose GONE versus INVISIBLE.

**Post-conditions**
- Success cases
  - Render "CLEAR" and VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=true, due=null), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe "CLEAR" and VISIBLE; test `TasksListBindingsTest.setDueDateLabel_completedNoDate_expectedView`.
  - Render "CLEAR" and VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=true, due=2026-01-22), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe "CLEAR" and VISIBLE; test `TasksListBindingsTest.setDueDateLabel_completedWithDate_expectedView`.
  - Render "D-Day" and VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=false, due=2026-01-15), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe "D-Day" and VISIBLE; test `TasksListBindingsTest.setDueDateLabel_today_expectedView`.
  - Render "D-1" and VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=false, due=2026-01-16), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe "D-1" and VISIBLE; test `TasksListBindingsTest.setDueDateLabel_tomorrow_expectedView`.
  - Render "D+1" and VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=false, due=2026-01-14), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe "D+1" and VISIBLE; test `TasksListBindingsTest.setDueDateLabel_yesterday_expectedView`.
  - Render empty text and visibility other than VISIBLE. `[Integration]`
    - Tip: Given Android main-thread binding with local today=2026-01-15 (concrete example; tests use device-local today and matching offsets), task(completed=false, due=null), initial text="old" and VISIBLE / When setDueDateLabel is invoked through the real task_item data binding / Then observe empty text and visibility other than VISIBLE; test `TasksListBindingsTest.setDueDateLabel_activeNoDate_expectedView`.
- Error cases
  - None. All inputs satisfying the pre-conditions are valid.
- Edge cases
  - No additional boundary examples in this initial draft.

**Undefined behavior** (optional)
- Concurrent calls off the Android main thread; clock changes during a call.
