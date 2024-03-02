package com.example.alarmapp.useCase

import com.example.alarmapp.data.model.TimerModel
import com.example.alarmapp.data.repository.SharedPrefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface SharedPrefUseCase {
    fun getTimerModel(): TimerModel

    fun saveTimerModel(timerModel: TimerModel)
    fun getTimerModelFlow(): Flow<TimerModel>
}

class SharedPrefUseCaseImpl @Inject constructor(
    private val repo: SharedPrefRepository
) : SharedPrefUseCase {
    override fun getTimerModel(): TimerModel {
        val focus = repo.getFocusTime()
        val shortBreak = repo.getShortBreakTime()
        val longBreak = repo.getLongBreakTime()
        val mode = repo.getCurrMode()
        val timeLeft = repo.getTimeLeft()

        return TimerModel(focus, shortBreak, longBreak, mode, timeLeft)
    }

    override fun saveTimerModel(timerModel: TimerModel) {
        repo.setFocusTime(timerModel.focusTime)
        repo.setShortBreak(timerModel.shortBreak)
        repo.setLongBreak(timerModel.longBreak)
        repo.setCurrMode(timerModel.curMode)
        repo.setTimeLeft(timerModel.timeLeft)
    }


    override fun getTimerModelFlow(): Flow<TimerModel> {
        return flowOf(getTimerModel())
    }
}