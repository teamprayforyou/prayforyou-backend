package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.board.BoardFeedBackService
import net.prayforyou.backend.application.board.BoardService
import net.prayforyou.backend.application.board.BoardCommentService
import net.prayforyou.backend.application.board.dto.GetAllPageBoardDto
import net.prayforyou.backend.application.board.dto.GetBoardDetailDto
import net.prayforyou.backend.global.common.CommonResponse
import net.prayforyou.backend.presenter.request.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService,
    private val boardFeedBackService: BoardFeedBackService,
    private val boardCommentService: BoardCommentService
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

    @PostMapping("")
    fun createBoard(
        @RequestBody request: CreateFreeBoardRequest
    ): CommonResponse<Boolean> {
        boardService.create(request)
        return CommonResponse.convert(true)
    }

    @PostMapping("/{boardId}")
    fun deleteBoard(
        @PathVariable("boardId") boardId: Long,
        @RequestBody request: DeleteBoardRequest
    ): CommonResponse<Boolean> {
        boardService.delete(boardId, request)
        return CommonResponse.convert(true)
    }

    @PatchMapping("")
    fun update(
        @RequestBody request: UpdateFreeBoardRequest
    ): CommonResponse<Boolean> {
        boardService.update(request)
        return CommonResponse.convert(true)
    }

    @PatchMapping("/feedback")
    fun updateFeedback(
        @RequestBody request: UpdateFeedBackRequest
    ): CommonResponse<Boolean> {
        boardFeedBackService.update(request)
        return CommonResponse.convert(true)
    }

    @PostMapping("/comment")
    fun createComment(
        @RequestBody request: CreateCommentRequest
    ): CommonResponse<Boolean> {
        boardCommentService.create(request)
        return CommonResponse.convert(true)
    }

    @PostMapping("/reply")
    fun createReply(
        @RequestBody request: CreateReplyRequest
    ): CommonResponse<Boolean> {
        boardCommentService.create(request)
        return CommonResponse.convert(true)
    }

    @PatchMapping("/reaction")
    fun updateReaction(
        @RequestBody request: UpdateReactionRequest
    ): CommonResponse<Boolean> {
        boardCommentService.update(request)
        return CommonResponse.convert(true)
    }

    @PostMapping("/reaction")
    fun deleteReaction(
        @RequestBody request: DeleteReactionRequest
    ): CommonResponse<Boolean> {
        boardCommentService.delete(request)
        return CommonResponse.convert(true)
    }
}