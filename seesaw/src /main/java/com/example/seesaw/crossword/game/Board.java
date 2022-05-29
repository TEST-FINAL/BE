package com.example.seesaw.crossword.game;

import com.example.seesaw.crossword.dto.CrossWordResponseDto;
import com.example.seesaw.crossword.repository.CrosswordRepository;
import com.example.seesaw.crossword.repository.QuizNumRepository;

import java.util.*;

public class Board {
    // 차원 정하는 변수 dimension
    private int dim;
    // 게임판 단어 추가할 비어있는 List
    private ArrayList<Word> wordsOnBoard = new ArrayList<>();

    // 각 방향에 대한 추가된 단어 수
    private int upWords = 0;
    private int rightWords = 0;

    // 각 방향에 대한 최대 단어 수
    public final int maxPerDirection = 9;

    // 모든 단어들의 좌표
//    private HashMap<String, Integer> numsAtCoords = new HashMap<>();

    // 플레이어가 찾은 단어
//    private ArrayList<Word> foundWords = new ArrayList<>();

    // 게임판에 배치된 모든 문자 좌표 <letter, list of coordinates>
    private HashMap<String, ArrayList<String>> letterPositions = new HashMap<>();

    // 완성된 게임판
    private String[][] fullBoard;

    // 현재 게임판에 있는 단어
    private String[][] partialBoard;

    // 빈 게임판
//    private final String emptySquare = "-";

    // 아직 게임 시작 전 사각형
//    private final String unsolvedSquare = "?";

    // 단어에 숫자가 할당?
//    private boolean numsSet = false;

    //repositoru
    private final CrosswordRepository crosswordRepository;
    private final QuizNumRepository quizNumRepository;

    // 게임판 만들 생성자
    public Board(int dim, CrosswordRepository crosswordRepository, QuizNumRepository quizNumRepository) {
        this.dim = dim;
        this.fullBoard = new String[dim][dim];
        this.partialBoard = new String[dim][dim];
        this.crosswordRepository = crosswordRepository;
        this.quizNumRepository = quizNumRepository;
    }

    // 모든 단어가 배치될경우 true로 리턴.
    public boolean allWordsPlaced() {
        if (numOfPlacedWords() == (maxPerDirection * (Direction.values().length))) {
            return true;
        }
        if (numOfPlacedWords() > (maxPerDirection * (Direction.values().length))) {
            throw new IllegalArgumentException(numOfPlacedWords() + ">" + (maxPerDirection * (Direction.values().length)));
        }
        return false;
    }

    // 단어 다 찾으면 true로 리턴  (사용 x)
//    public boolean allWordsFound() {
//        if (foundWords.size() == wordsOnBoard.size()) {
//            return true;
//        }
//        return false;
//    }

    // 유저의 현재 진행상황을 출력
//    public void printProgress() {
//        if (!numsSet) {
//            setNums();
//            numsSet = true;
//        }
//
//        String currentPos;
//        for (int y=0; y<dim; y++) {
//            for (int x=0; x < dim; x++) {
//                currentPos = x + " " + y;
//                if (fullBoard[x][y] == null) {
//                    partialBoard[x][y] = emptySquare;
//                } else {
//                    if (getFoundWordLetter(currentPos) == null) {
//                        partialBoard[x][y] = unsolvedSquare;
//                    } else {
//                        partialBoard[x][y] = getFoundWordLetter(currentPos);
//                    }
//                }
//            }
//        }
//
//
//        String output = "";
//        for (int y=0; y<dim; y++) {
//            for (int x=0; x<dim; x++) {
//                currentPos = x + " " + y;
//                if (partialBoard[x][y] == null) {
//                    output += emptySquare + " ";
//                    continue;
//                }
//
//                if (partialBoard[x][y].equals(unsolvedSquare)) {
//                    if (numsAtCoords.containsKey(Direction.UP.name().charAt(0) + currentPos)) {
//                        output += numsAtCoords.get(Direction.UP.name().charAt(0) + currentPos) + " ";
//                    } else if (numsAtCoords.containsKey(Direction.RIGHT.name().charAt(0) + currentPos)) {
//                        output += numsAtCoords.get(Direction.RIGHT.name().charAt(0) + currentPos) + " ";
//                    } else {
//                        output += unsolvedSquare + " ";
//                    }
//                    continue;
//                }
//                // partialBoard[x][y] must contain part of a found word
//                output += partialBoard[x][y] + " ";
//            }
//            output += "\n";
//        }
//        System.out.println(output);
//        //출력
//        printClues();
//    }

    // 주어진 위치에서 발견된 문자를 반환하고 발견된 문자가 없으면 null 리턴
//    private String getFoundWordLetter(String position) {
//        int [] currentXTrail;
//        int [] currentYTrail;
//        for (Word word : foundWords) {
//            currentXTrail = word.getXTrail();
//            currentYTrail = word.getYTrail();
//
//            for (int i=0; i<currentXTrail.length; i++) {
//                if (position.equals(currentXTrail[i] + " " + currentYTrail[i])) {
//                    return "" + word.getName().charAt(i);
//                }
//            }
//        }
//        return null;
//    }

    // 계산된 값들 response
    public List<CrossWordResponseDto> printClues() {
        List<CrossWordResponseDto> crossWordResponseDtos = new ArrayList<>();
        int id = 0;
        for (Word word : wordsOnBoard) {
            crossWordResponseDtos.add(new CrossWordResponseDto(id++, word, false));
        }
        return crossWordResponseDtos;
    }

    // 각 단어의 숫자를 할당
//    public void setNums() {
//        int upOn = 1;
//        int rightOn = 1;
//        String currentCoords;
//        Direction otherDirection;
//
//        for (Word word : wordsOnBoard) {
//            currentCoords = word.getPosition();
//
//            if (word.getDirection() == Direction.UP) {
//                otherDirection = Direction.RIGHT;
//            } else {
//                otherDirection = Direction.UP;
//            }
//
//            if (word.getDirection() == Direction.UP) {
//                word.setNum(upOn);
//                numsAtCoords.put(Direction.UP.name().charAt(0) + word.getPosition(), upOn);
//                upOn++;
//                continue;
//            }
//
//            if (word.getDirection() == Direction.RIGHT) {
//                word.setNum(rightOn);
//                numsAtCoords.put(Direction.RIGHT.name().charAt(0) + word.getPosition(), rightOn);
//                rightOn++;
//                continue;
//            }
//
//            throw new IllegalArgumentException("Direction is invalid");
//        }
//    }

    // 게임판에 단어를 추가한다, 성공하면 true를 리턴
    public boolean tryAddWord(String givenWord) {
        Word word = new Word(givenWord);
        if ((rightWords>=maxPerDirection) && (upWords>=maxPerDirection)) {
            return false;
        }

        if (rightWords >= maxPerDirection) {
            word.removeDirectionFromIteration(Direction.RIGHT);
        }

        if (upWords >= maxPerDirection) {
            word.removeDirectionFromIteration(Direction.UP);
        }

        if (wordsOnBoard.size() == 0) {
            try {
                addWord(word, (dim/3) + " " + (dim/3), word.getDirectionIteration().get(0));
            } catch (Exception e) {
                addWord(word, "0 " + dim/2, Direction.RIGHT);
            }
            return true;
        }
        String name = word.getName();
        String adjustedPosition;

        for (int c=0; c<name.length(); c++) {
            if (!letterPositions.containsKey(name.charAt(c) + "")) {
                continue;
            }

            for (String possiblePosition : letterPositions.get(name.charAt(c) + "")) {
                for (Direction direction : word.getDirectionIteration()) {
                    adjustedPosition = adjustPosition(possiblePosition, direction, c);
                    if (spacedCanAdd(word, adjustedPosition, direction)) {
                        addWord(word, adjustedPosition, direction);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 게임판에 단어 추가 메서드
    private void addWord(Word word, String position, Direction direction) {
        word.setPosition(position);
        word.setDirection(direction);
        updateLetterPositions(word);
        wordsOnBoard.add(word);
        if (direction == Direction.UP) {
            upWords++;
        } else if (direction == Direction.RIGHT) {
            rightWords++;
        } else {
            throw new IllegalArgumentException("Direction is invalid.");
        }

        int[] xTrail = word.getXTrail();
        int[] yTrail = word.getYTrail();
        String name = word.getName();
        assert ((name.length() == xTrail.length) && (xTrail.length == yTrail.length));

        for (int i=0; i<name.length(); i++) {
            fullBoard[xTrail[i]][yTrail[i]] = "" + name.charAt(i);
        }
    }

    // 지정된 위치에 지정된 문자를 배치하도록 위치 조정 메서드
    private String adjustPosition(String originalPos, Direction direction, int charOn) {
        int x = Utils.getX(originalPos);
        int y = Utils.getY(originalPos);
        if (direction == Direction.UP) {
            return x + " " + (y-charOn);
        }
        if (direction == Direction.RIGHT) {

            return (x-charOn) + " " + y;
        }
        throw new IllegalArgumentException("현재 주어진 방향은 : " + direction);
    }

    // 새 단어로 문자 위치를 업데이트
    private void updateLetterPositions(Word word) {
        Objects.requireNonNull(word.getPosition());
        Objects.requireNonNull(word.getDirection());

        String name = word.getName().toUpperCase();
        ArrayList<String> currentValue;
        String currentKey;
        int[] xTrail = word.getXTrail();
        int[] yTrail = word.getYTrail();

        for (int c=0; c<name.length(); c++) {
            currentKey = "" + name.charAt(c);
            if (letterPositions.containsKey(currentKey)) {
                currentValue = letterPositions.get(currentKey);
                currentValue.add(xTrail[c] + " " + yTrail[c]);
            } else {
                currentValue = new ArrayList<>(Arrays.asList(xTrail[c] + " " + yTrail[c]));
            }
            letterPositions.put(currentKey, currentValue);
        }
    }

    // 단어가 게임판의 지정된 위치에 배치될 수 있는지 확인.
    private boolean tightCanAdd(Word word, String position, Direction direction) {
        int currentX = Utils.getX(position);
        int currentY = Utils.getY(position);
        String name = word.getName();

        if ((currentX<0) || (currentY<0)){
            return false;
        }

        for (int c=0; c<name.length(); c++) {
            if ((currentX >= dim) || (currentY >= dim)) {
                return false;
            }
            if ((fullBoard[currentX][currentY] != null) &&
                    (!fullBoard[currentX][currentY].equals(name.charAt(c) + ""))) {
                return false;
            }
            if (direction == Direction.UP) {
                currentY++;
                continue;
            }
            if (direction == Direction.RIGHT) {
                currentX++;
                continue;
            }
            throw new IllegalArgumentException("The given direction was: " + direction);
        }
        return true;
    }

    // 단어 간격이 잘 맞도록 단어를 추가할 수 있는지 확인.
    private boolean spacedCanAdd(Word word, String position, Direction direction) {
        if (!tightCanAdd(word, position, direction)) {
            return false;
        }

        int fixedY = Utils.getY(position);
        int plusCount = 0;
        int minusCount = 0;
        if (direction == Direction.RIGHT) {
            for (int x : Utils.getXTrail(position, direction, word.getName().length())) {
                try {
                    if (fullBoard[x][fixedY+1] != null) {
                        plusCount++;
                    } else {
                        plusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    plusCount = 0;
                }
                if (plusCount == 2) {
                    return false;
                }

                try {
                    if (fullBoard[x][fixedY-1] != null) {
                        minusCount++;
                    } else {
                        minusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    minusCount = 0;
                }
                if (minusCount == 2) {
                    return false;
                }
            }
        }

        int fixedX = Utils.getX(position);
        plusCount = 0;
        minusCount = 0;
        if (direction == Direction.UP) {
            for (int y : Utils.getYTrail(position, direction, word.getName().length())) {
                try {
                    if (fullBoard[fixedX+1][y] != null) {
                        plusCount++;
                    } else {
                        plusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    plusCount = 0;
                }
                if (plusCount == 2) {
                    return false;
                }

                try {
                    if (fullBoard[fixedX-1][y] != null) {
                        minusCount++;
                    } else {
                        minusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    minusCount = 0;
                }
                if (minusCount == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    // 게임판 단어 수 리턴
    public int numOfPlacedWords() {
        assert ((upWords + rightWords) == wordsOnBoard.size());
        return wordsOnBoard.size();
    }
}