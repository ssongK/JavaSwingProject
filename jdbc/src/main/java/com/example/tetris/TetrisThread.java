package com.example.tetris;

public class TetrisThread extends Thread{
    private TetrisPanelMethod tp;

    // TetrisPanel 객체 레퍼런스 저장
    public TetrisThread(TetrisPanelMethod tp){
        this.tp = tp;
    }

    @Override
    public void run() {
        // 테트리스 판 초기화
        tp.resetPanel();
        while(true){
            // 블록 생성
            tp.createBlock();
            // 블록 떨어짐(0.5초마다)
            while(tp.dropBlock()){
                System.out.println("drop");
                try{
                    Thread.sleep(500);
                }
                catch(InterruptedException ex) { return; }
            }   
            System.out.println("stop");
            if(tp.checkGameOver()){
                tp.showResult();
                tp.setFocusable(false);
                // interrupt();
                break;
            }
        }
    }
}
