package com.example.onui.domain.timeline.repository

import com.example.onui.domain.diary.entity.QDiary.diary
import com.example.onui.domain.diary.presentation.response.DiaryDetailResponse
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class QTimelineRepositoryImpl(
    private val query: JPAQueryFactory
) : QTimelineRepository {

    override fun findPageByDate(pageable: Pageable, date: LocalDate): Page<DiaryDetailResponse> {
        val query = query.selectFrom(diary).where(
            diary.isPosted.eq(true).and(
                diary.day.eq(date.dayOfMonth).and(
                    diary.month.eq(date.monthValue).and(
                        diary.year.eq(date.year)
                    )
                )
            )
        ).orderBy(diary.createdAt.desc()).offset(pageable.offset).limit(pageable.pageSize.toLong())

        val iterable = query.fetch().map { it.toDetailResponse() }

        return PageImpl(iterable, pageable, iterable.size.toLong())
    }
}