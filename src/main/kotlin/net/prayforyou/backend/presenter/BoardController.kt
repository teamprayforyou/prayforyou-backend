package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.board.BoardService
import net.prayforyou.backend.application.board.dto.GetAllPageBoardDto
import net.prayforyou.backend.application.board.dto.GetBoardDetailDto
import net.prayforyou.backend.domain.board.Board
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.infrastructure.persistence.querydsl.dto.BoardJoinUserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping("")
    fun getBoard(
        pageable: Pageable
    ): CommonResponse<Page<GetAllPageBoardDto>> =
        CommonResponse.convert(boardService.get(pageable))

    @GetMapping("/{boardId}")
    fun readBoard(
        @PathVariable("boardId") boardId: Long
    ): CommonResponse<GetBoardDetailDto> =
        CommonResponse.convert(boardService.getById(boardId))

    fun updateContent() {

    }
}