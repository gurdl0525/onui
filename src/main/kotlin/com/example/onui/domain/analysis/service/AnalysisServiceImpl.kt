package com.example.onui.domain.analysis.service

import com.example.onui.domain.analysis.presentation.dto.response.MonthlyChangeResponse
import com.example.onui.domain.analysis.presentation.dto.response.MoodeChangeResponse
import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.entity.Mood
import com.example.onui.domain.diary.presentation.response.DiaryResponse
import com.example.onui.domain.diary.repository.DiaryRepository
import com.example.onui.domain.diary.repository.QDiaryRepository
import com.example.onui.global.common.facade.UserFacade
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.math.pow
import kotlin.math.sqrt

@Service
@Transactional(readOnly = true)
class AnalysisServiceImpl(
    private val diaryRepository: DiaryRepository,
    private val userFacade: UserFacade,
    private val qDiaryRepository: QDiaryRepository
) : AnalysisService {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    @Transactional
    override fun test() {

        val user = userFacade.getCurrentUser()

        diaryRepository.deleteAllByUser(user)

        val now = LocalDateTime.now()

        var i = now.minusDays(30);

        while (i.isBefore(now)) {
            diaryRepository.save(Diary(user, null, Mood.values().random(), mutableListOf("오누이 최고"), i))
            i = i.plusDays(1)
        }
    }

    override fun getMoodeChange(): MoodeChangeResponse {

        val user = userFacade.getCurrentUser()
        val now = LocalDateTime.now()

        val diaries =
            diaryRepository.findAllByUserAndYearAndMonthOrderByCreatedAtAsc(user, now.year, now.monthValue - 1)

        return moodCount(diaries)
    }

    override fun getMonthlyChange(): MonthlyChangeResponse {

        val user = userFacade.getCurrentUser()

        val diaries = qDiaryRepository.findOneMonthAgoByUser(user)

        val response = moodCount(diaries)

        val sigma = getSigma(response).toInt()

        val message = if (sigma < 10) {
            "현재 감정은 완만한 편이에요!"
        } else {
            "표준편차는 $sigma 이에요! (10이하가 완만하고 안정적인편)"
        }

        return MonthlyChangeResponse.of(diaries.map { it.toResponse() }.toMutableList(), message)
    }

    private fun moodCount(diaries: MutableList<Diary>): MoodeChangeResponse {

        val response = MoodeChangeResponse.setUp()

        diaries.map {
            when (it.mood) {
                Mood.BAD -> response.bad += 1
                Mood.WORST -> response.worst += 1
                Mood.FINE -> response.fine += 1
                Mood.GOOD -> response.good += 1
                Mood.NOT_BAD -> response.notBad += 1
            }
        }

        return response
    }

    private fun getSigma(response: MoodeChangeResponse): Double {
        val worst = 1 * response.worst
        val bad = 2 * response.bad
        val notBad = 3 * response.notBad
        val fine = 4 * response.fine
        val good = 5 * response.good
        val e = (worst + bad + notBad + fine + good) / 5

        logger.info { e }

        val powWorst = (e - worst).toDouble().pow(2) * response.worst
        val powBad = (e - bad).toDouble().pow(2) * response.bad
        val powNotBad = (e - notBad).toDouble().pow(2) * response.notBad
        val powFine = (e - fine).toDouble().pow(2) * response.fine
        val powGood = (e - good).toDouble().pow(2) * response.good

        return sqrt((powWorst + powBad + powNotBad + powFine + powGood) / (response.worst + response.good + response.bad + response.notBad + response.fine))
    }
}