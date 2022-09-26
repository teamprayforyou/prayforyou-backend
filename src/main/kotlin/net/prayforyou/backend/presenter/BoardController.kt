package net.prayforyou.backend.presenter

import net.prayforyou.backend.application.board.BoardService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {


    @PostMapping("")
    fun createContent(

    ) {
//        boardService.save()

    }

    @GetMapping("/{boardId}")
    fun readContent(
        @PathVariable("boardId") boardId: Long
    ) {
//        boardService.getById(boardId)
    }

    fun updateContent() {

    }

    @GetMapping("/boardId")
    fun pressGoodByBoard(
        @PathVariable("boardId") boardId: Long
    ) {
//        boardService.pressGoodByBoard(boardId)
    }

    fun pressBad() {

    }

}