package ru.sidey383.cube.file;

public interface StatusPrinter {

    void printStatus(int progress, int total);

    void complete();

    void interrupted();

}
