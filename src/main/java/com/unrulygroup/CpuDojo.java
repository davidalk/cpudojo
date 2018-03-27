package com.unrulygroup;

public class CpuDojo {
    private int[] memory;
    private int accumalator;
    private int counter;

    public CpuDojo() {
        memory = new int[16];
        accumalator = 0;
        counter = 0;
        memory[0] = 1;
        memory[1] = 100;
        memory[2] = 2;
        memory[3] = 7;
        memory[4] = 3;
        memory[5] = 15;
        memory[6] = 0;
    }

    public static void main(String[] args) {
        CpuDojo cpuDojo = new CpuDojo();

        cpuDojo.run();
    }

    public void run() {
        int currentOp = Integer.MAX_VALUE;

        while (currentOp > 0) {
            currentOp = memory[counter];

            switch (currentOp) {
                case 0:
                    break;
                case 1:
                    lda(getNextMemVal());
                    break;
                case 2:
                    adc(getNextMemVal());
                    break;
                case 3:
                    sta(getNextMemVal());
                    break;
            }

            counter++;
            printMemory();
        }
    }


    private void lda(int val) {
        accumalator = val;
        System.out.println("Execute LDA with val: " + val);
    }

    private void adc(int val) {
        accumalator += val;
        System.out.println("Execute ADC with val: " + val + ", accumulator: " + accumalator);
    }

    private void sta(int address) {
        if (address > 15) throw new OutOfMemoryError("You've only got 16 bytes!");
        memory[address] = accumalator;
        System.out.println("Execute STA with address: " + address );
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

}
