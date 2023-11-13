package com.example.onui.domain.user.entity

import javax.persistence.*

@Entity(name = "theme")
class Theme(
    id: String
) {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(18)", nullable = false)
    var id: String = id
        protected set

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    var user: MutableList<User> = arrayListOf()
        protected set
}