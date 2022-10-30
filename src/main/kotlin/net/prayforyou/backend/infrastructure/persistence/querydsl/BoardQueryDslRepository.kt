package net.prayforyou.backend.infrastructure.persistence.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import net.prayforyou.backend.domain.board.QBoard.*
import net.prayforyou.backend.domain.board.QBoardComment
import net.prayforyou.backend.domain.board.QBoardComment.*
import net.prayforyou.backend.domain.clan.QClan
import net.prayforyou.backend.domain.clan.QClan.*
import net.prayforyou.backend.domain.user.QUser.*
import net.prayforyou.backend.infrastructure.persistence.querydsl.dto.BoardJoinUserDto
import net.prayforyou.backend.infrastructure.persistence.querydsl.dto.QBoardJoinUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class BoardQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllByPage(pageable: Pageable): Page<BoardJoinUserDto> {
        val result =
            queryFactory
                .select(
                    QBoardJoinUserDto(
                        board.id,
                        board.user.id,
                        board.bad,
                        board.content,
                        board.createdAt,
                        board.good,
                        board.isDeleted,
                        board.title,
                        board.type,
                        board.updatedAt,
                        board.view,
                        user.email,
                        user.nickname,
                        clan.clanNickname
                    )
                )
                .from(board)
                .join(board.user, user)
                .rightJoin(user.clanId, clan)
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .orderBy(board.createdAt.desc())
                .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }
}