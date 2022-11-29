package com.example.tetris;

public class TetrisThread extends Thread{
    private TetrisPanelMethod tp;

    // TetrisPanel 객체 레퍼런스 저장
    public TetrisThread(TetrisPanelMethod tp){
        this.tp = tp;
    }

    @Override
    public void run() {
        int speed = 700; // 떨어지는 속도 초기화(0.7초)
        tp.resetPanel(); // 테트리스 판 초기화
        while(true){
            tp.createBlock(); // 블록 생성

            // 블록 떨어짐(0.5초에서 점점 빨라짐)
            while(tp.dropBlock()){
                try{
                    Thread.sleep(speed);
                }
                catch(InterruptedException ex) { return; }
                if(speed>100) speed -= 1;
            }   

            if(tp.checkGameOver()){
                tp.showResult();
                tp.setFocusable(false);
                break;
            }
        }
    }
}
