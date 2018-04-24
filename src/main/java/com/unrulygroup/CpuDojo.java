package com.unrulygroup;

import java.util.HashMap;
import java.util.Map;

public class CpuDojo {
    private int[] memory;
    private int accumalator;
    private int counter;
    private int x, y;
    private boolean equal;
    private Map<Integer, Runnable> opMap = new HashMap<>();

    public CpuDojo() {
        memory = new int[256];
        accumalator = 0;
        counter = 0;
        int[] data = {4, 128, 1, 0x77, 8, 5, 1, 0x68,
                8, 5, 1, 0x6F, 8, 5, 1, 0x20, 8, 5, 1, 0x6c, 8, 5,
                1, 0x65, 8, 5, 1, 0x74, 8, 5, 1, 0x20, 8, 5, 1, 0x74, 8, 5,
                1, 0x68, 8, 5, 1, 0x65, 8, 5, 1, 0x20, 8, 5, 1, 0x64,
                8, 5, 1, 0x6F, 8, 5, 1, 0x67, 8, 5, 1, 0x73, 8, 5, 1,
                0x20, 8, 5, 1, 0x6F, 8, 5, 1, 0x75, 8, 5, 1, 0x74, 8, 5,
                1, 0x20, 8, 5, 10, 3, 1, 0x77, 8, 5, 1, 0x68, 8, 5, 1,
                0x6F, 8, 5, 1, 0x20, 8, 5, 9, 6, 0, 7, -21, 0};

        for (int i=0; i < data.length; i++) {
            memory[i] = data[i];
        }

        opMap.put(0, this::noop);
        opMap.put(1, this::lda);
        opMap.put(2, this::adc);
        opMap.put(3, this::sta);
        opMap.put(4, this::ldx);
        opMap.put(5, this::inx);
        opMap.put(6, this::cmy);
        opMap.put(7, this::bne);
        opMap.put(8, this::sta_x);
        opMap.put(9, this::dey);
        opMap.put(10, this::ldy);

    }

    public static void main(String[] args) {
        CpuDojo cpuDojo = new CpuDojo();

        cpuDojo.run();
    }

    public void run() {
        int currentOp = Integer.MAX_VALUE;

        while (currentOp > 0) {
            currentOp = memory[counter];

            opMap.get(currentOp).run();

            counter++;
        }
        printMemory();
        printMessage();
    }

    private void noop() {}


    private void lda() {
        int val = getNextMemVal();
        accumalator = val;
    }

    private void adc() {
        int val = getNextMemVal();
        accumalator += val;
    }

    private void sta() {
        int address = getNextMemVal();
        if (address > memory.length-1) throw new OutOfMemoryError("You've only got " + memory.length + "  bytes!");
        memory[address] = accumalator;
    }

    private void ldx() {
        x = getNextMemVal();
    }

    private void inx() {
        x++;
    }

    private void cmy() {
        int val = getNextMemVal();
        equal = y == val;
    }

    private void bne() {
        int val = getNextMemVal();
        if (equal) counter += val;
    }

    private void sta_x() {
        memory[x] = accumalator;
    }

    private void dey() {
        y--;
    }

    private void ldy() {
        y = getNextMemVal();
    }

    private int getNextMemVal() {
        return memory[++counter];
    }

    private void printMemory() {
        System.out.print("Memory: ");

        for(int val: memory) {
            System.out.print(val + "\t");
        }

        System.out.print("Accumulator: " + accumalator + "\t");

        System.out.println();
    }

    private void printMessage() {
        System.out.print("Message: ");

        for (int val : memory) {
            System.out.print((char) val);
        }
    }

}
