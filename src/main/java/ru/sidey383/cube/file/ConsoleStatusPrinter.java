package ru.sidey383.cube.file;

public class ConsoleStatusPrinter implements StatusPrinter {

    private final int STATUS_SIZE = 20;

    @Override
    public void printStatus(int progress, int total) {
        int count = progress * STATUS_SIZE / total;
        System.out.print("\b".repeat(STATUS_SIZE + 2));
        System.out.print("[" + "=".repeat(count) + " ".repeat(STATUS_SIZE - count) + "]");
    }

    @Override
    public void complete() {
        System.out.print("\b".repeat(STATUS_SIZE + 2));
        System.out.println("Complete!");
    }

    @Override
    public void interrupted() {
        System.out.print("\b".repeat(STATUS_SIZE + 2));
        System.out.println("Interrupted!");
    }

}
