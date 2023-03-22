package com.gdsc.wero.domain.board.application;

import com.gdsc.wero.domain.board.api.dto.request.BoardReqDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardInfoResDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardResListDto;
import com.gdsc.wero.domain.board.domain.Board;
import com.gdsc.wero.domain.board.domain.repository.BoardRepository;
import com.gdsc.wero.domain.board.exception.BoardNotExistException;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    /**
     * 게시물 조회
     */
    public BoardResListDto getBoardList(){
        List<BoardInfoResDto> collect = boardRepository.getBoardList()
                .stream()
                .map(BoardInfoResDto::new)
                .collect(Collectors.toList());

        return new BoardResListDto(collect);

    }


    /**
     * 게시물 저장
     */
    public void saveBoard(BoardReqDto boardReqDto, String img, String email, String provider) {
        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Board board = Board.createBoard(boardReqDto, img, user);

        boardRepository.save(board);

        log.info("================ POST IS BEING SAVED ==================");

    }

    /**
     * 게시물 수정
     */
    public void updateBoard(BoardReqDto boardReqDto, String img, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotExistException("This Post does not exist | boardId : " + boardId));

        board.updateBoard(boardReqDto, img);

        log.info("================ POST IS BEING UPDATED ==================");

    }

    /**
     * 게시물 삭제
     */
    public void deleteBoard(Long boardId){

        boardRepository.deleteById(boardId);

        log.info("================ POST IS BEING DELETED ==================");
    }
}
