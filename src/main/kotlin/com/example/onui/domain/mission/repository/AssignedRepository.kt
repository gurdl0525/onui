package com.example.onui.domain.mission.repository

import com.example.onui.domain.mission.entity.Assigned
import com.example.onui.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssignedRepository: JpaRepository<Assigned, Assigned.IdClass> {

    fun findAllByUser(user: User): List<Assigned>?
}