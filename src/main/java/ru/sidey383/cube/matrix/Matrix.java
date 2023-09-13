package ru.sidey383.cube.matrix;

public interface Matrix {

    double[] multiple(double[] dot);

    double get(int i, int j);

    int getISize();

    int getJSize();

    Matrix multiple(Matrix matrix);

    public double[][] getValues();

}
