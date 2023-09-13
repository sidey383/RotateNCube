package ru.sidey383.cube.matrix;

public class SimpleMatrix implements Matrix {

    protected double[][] values;

    protected int iSize;

    protected int jSize;

    public SimpleMatrix(Matrix matrix) {
        iSize = matrix.getISize();
        jSize = matrix.getJSize();
        values = new double[getISize()][getJSize()];
        for(int i = 0; i < getISize(); i++) {
            for(int j = 0; j < getJSize(); j++) {
                values[i][j] = matrix.get(i, j);
            }
        }
    }

    public SimpleMatrix(double[][] matrix) {
        iSize = matrix.length;
        if(iSize > 0) {
            jSize = matrix[0].length;
        } else {
            jSize = 0;
            return;
        }
        values = new double[getISize()][getJSize()];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                values[i][j] = matrix[i][j];
            }
        }
    }

    @Override
    public double[] multiple(double[] vector) {
        if(vector.length != getJSize())
            throw new IllegalArgumentException("Wrong dimension of vector");
        double[] newVector = new double[getISize()];
        for(int i = 0; i < getISize(); i++)
            for(int j = 0; j < getJSize(); j++)
                newVector[i] += get(i, j)*vector[j];
        return newVector;
    }

    @Override
    public Matrix multiple(Matrix matrix) {
        if(matrix.getISize() != getJSize())
            throw new IllegalArgumentException("Wrong dimension of matrix");
        double[][] newMatrix = new double[getISize()][matrix.getJSize()];
        for(int i = 0; i < getISize(); i++)
            for(int j = 0; j < matrix.getISize(); j++)
                for(int k = 0; k < getJSize(); k++) {
                    newMatrix[i][j] += get(i, k) * matrix.get(k, j);
                }
        return new SimpleMatrix(newMatrix);
    }

    @Override
    public double get(int i, int j) {
        return values[i][j];
    }

    @Override
    public int getISize() {
        return iSize;
    }

    @Override
    public int getJSize() {
        return jSize;
    }

    @Override
    public double[][] getValues() {
        return values.clone();
    }
}
