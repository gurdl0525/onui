package com.example.onui.domain.user.entity

import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    id: UUID? = null,
    email: String,
    name: String
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
}