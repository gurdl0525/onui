package com.example.onui.domain.diary.repository

import com.example.onui.domain.diary.entity.Diary
import com.example.onui.domain.diary.entity.QDiary.diary
import com.example.onui.domain.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
@Repository
class QDiaryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): QDiaryRepository {

    override fun find3DayAgoByUser(user: User): Diary? {

        val ago = LocalDateTime.now().minusDays(3)

        return queryFactory.selectFrom(diary)
            .orderBy(diary.createdAt.desc())
            .where(diary.user.eq(user).and(diary.createdAt.after(ago)))
            .limit(1)
            .fetchOne()
    }
}