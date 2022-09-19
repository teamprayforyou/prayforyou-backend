//package net.prayforyou.backend.domain.banner
//
//import net.prayforyou.backend.domain.banner.enums.BannerType
//import net.prayforyou.backend.domain.user.enums.UserType
//import java.time.LocalDateTime
//import javax.persistence.*
//
//@Entity
//@Table(name = "bannder")
//class Banner(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long? = null,
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type")
//    var type: BannerType,
//
//    @Column(name = "siteUrl")
//    var siteUrl: String,
//
//    @Column(name = "image_url")
//    var imageUrl: String,
//
//    @Column(name = "started_at")
//    var startedAt: LocalDateTime,
//
//    @Column(name = "ended_at")
//    var endedAt: LocalDateTime,
//
//    @Column(name = "created_at")
//    var createdAt: LocalDateTime,
//
//    @Column(name = "updated_at")
//    var updatedAt: LocalDateTime
//)