package com.example.onui.domain.timeline.repository

import com.example.onui.domain.diary.entity.QDiary.diary
import com.example.onui.domain.timeline.presentation.dto.response.TimelineResponse
import com.example.onui.domain.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import com.vane.badwordfiltering.BadWordFiltering
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class QTimelineRepositoryImpl(
    private val query: JPAQueryFactory, private val badWordFiltering: BadWordFiltering
) : QTimelineRepository {

    override fun findPageByDate(pageable: Pageable, date: LocalDate, user: User): Page<TimelineResponse> {
        val query = query.selectFrom(diary).where(
            diary.isPosted.eq(true).and(
                diary.day.eq(date.dayOfMonth).and(
                    diary.month.eq(date.monthValue).and(
                        diary.year.eq(date.year)
                    )
                )
            )
        ).orderBy(diary.createdAt.desc()).offset(pageable.offset).limit(pageable.pageSize.toLong())

        val iterable = query.fetch().map {
            if (user.onFiltering) it.toFilteringTimelineResponse(badWordFiltering)
            else it.toTimelineResponse()
        }

        return PageImpl(iterable, pageable, iterable.size.toLong())
    }
}