# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Return CLEAR for completed tasks regardless of due date; otherwise return an empty string without a date, D-Day today, D-N in the future, or D+N in the past. N is the total number of calendar days, including month/year/leap boundaries.

**Pre-conditions**
- today and each non-null dueDate are valid LocalDate values. isCompleted is a Boolean; dueDate may be null.

**Post-conditions**
- Success cases
  - Return `CLEAR`. `[Unit]`
    - Tip: Given dueDate=null, today=2026-01-15, isCompleted=true / When dueDateLabel is called / Then return exactly "CLEAR"; test `DueDateUtilsTest.dueDateLabel_completedNoDate_expectedLabel`.
  - Return `CLEAR`. `[Unit]`
    - Tip: Given dueDate=2026-01-22, today=2026-01-15, isCompleted=true / When dueDateLabel is called / Then return exactly "CLEAR"; test `DueDateUtilsTest.dueDateLabel_completedWithDate_expectedLabel`.
  - Return ``. `[Unit]`
    - Tip: Given dueDate=null, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly ""; test `DueDateUtilsTest.dueDateLabel_activeNoDate_expectedLabel`.
  - Return `D-Day`. `[Unit]`
    - Tip: Given dueDate=2026-01-15, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-Day"; test `DueDateUtilsTest.dueDateLabel_today_expectedLabel`.
  - Return `D-7`. `[Unit]`
    - Tip: Given dueDate=2026-01-22, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-7"; test `DueDateUtilsTest.dueDateLabel_futureSeven_expectedLabel`.
  - Return `D+7`. `[Unit]`
    - Tip: Given dueDate=2026-01-08, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D+7"; test `DueDateUtilsTest.dueDateLabel_pastSeven_expectedLabel`.
- Error cases
  - None. All inputs satisfying the pre-conditions are valid.
- Edge cases
  - Return `D-1`. `[Unit]`
    - Tip: Given dueDate=2026-01-16, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-1"; test `DueDateUtilsTest.dueDateLabel_tomorrow_expectedLabel`.
  - Return `D+1`. `[Unit]`
    - Tip: Given dueDate=2026-01-14, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D+1"; test `DueDateUtilsTest.dueDateLabel_yesterday_expectedLabel`.
  - Return `D-31`. `[Unit]`
    - Tip: Given dueDate=2026-02-15, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-31"; test `DueDateUtilsTest.dueDateLabel_monthAhead_expectedLabel`.
  - Return `D+31`. `[Unit]`
    - Tip: Given dueDate=2026-01-15, today=2026-02-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D+31"; test `DueDateUtilsTest.dueDateLabel_monthBehind_expectedLabel`.
  - Return `D-365`. `[Unit]`
    - Tip: Given dueDate=2027-01-15, today=2026-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-365"; test `DueDateUtilsTest.dueDateLabel_yearAhead_expectedLabel`.
  - Return `D+365`. `[Unit]`
    - Tip: Given dueDate=2026-01-15, today=2027-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D+365"; test `DueDateUtilsTest.dueDateLabel_yearBehind_expectedLabel`.
  - Return `D-2`. `[Unit]`
    - Tip: Given dueDate=2028-03-01, today=2028-02-28, isCompleted=false / When dueDateLabel is called / Then return exactly "D-2"; test `DueDateUtilsTest.dueDateLabel_leapCrossing_expectedLabel`.
  - Return `D+2`. `[Unit]`
    - Tip: Given dueDate=2028-02-28, today=2028-03-01, isCompleted=false / When dueDateLabel is called / Then return exactly "D+2"; test `DueDateUtilsTest.dueDateLabel_leapReverse_expectedLabel`.
  - Return `D-366`. `[Unit]`
    - Tip: Given dueDate=2029-01-15, today=2028-01-15, isCompleted=false / When dueDateLabel is called / Then return exactly "D-366"; test `DueDateUtilsTest.dueDateLabel_leapYearAhead_expectedLabel`.
  - Return `D-1`. `[Unit]`
    - Tip: Given dueDate=2026-02-01, today=2026-01-31, isCompleted=false / When dueDateLabel is called / Then return exactly "D-1"; test `DueDateUtilsTest.dueDateLabel_monthEnd_expectedLabel`.
  - Return `D-1`. `[Unit]`
    - Tip: Given dueDate=2027-01-01, today=2026-12-31, isCompleted=false / When dueDateLabel is called / Then return exactly "D-1"; test `DueDateUtilsTest.dueDateLabel_yearEnd_expectedLabel`.
  - Return `D-1`. `[Unit]`
    - Tip: Given dueDate=2028-02-29, today=2028-02-28, isCompleted=false / When dueDateLabel is called / Then return exactly "D-1"; test `DueDateUtilsTest.dueDateLabel_leapDay_expectedLabel`.

**Undefined behavior** (optional)
- None within the typed input domain; LocalDate.MIN/MAX are valid but are not exhaustively enumerated here.
