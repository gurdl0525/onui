package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.QDiary.diary
import com.example.onui.domain.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
@Transactional(readOnly = true)
@Repository
class QDiaryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : QDiaryRepository {

    override fun findThreeDayAgoByUser(user: User) = queryFactory.selectFrom(diary)
        .orderBy(diary.createdAt.desc())
        .where(diary.user.eq(user).and(diary.createdAt.after(LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.of(0,0,0)))))
        .limit(3)
        .fetch().toMutableList()

    override fun findSevenDayAgoByUser(user: User) = queryFactory.selectFrom(diary)
        .orderBy(diary.createdAt.desc())
        .where(diary.user.eq(user).and(diary.createdAt.after(LocalDateTime.of(LocalDate.now().minusDays(6), LocalTime.of(0,0,0)))))
        .fetch().map { it.toResponse() }.toMutableList()

    override fun findOneMonthAgoByUser(user: User) = queryFactory.selectFrom(diary)
        .orderBy(diary.createdAt.desc())
        .where(diary.user.eq(user).and(diary.createdAt.after(LocalDateTime.of(LocalDate.now().minusDays(29), LocalTime.of(0,0,0)))))
        .fetch().toMutableList()
}