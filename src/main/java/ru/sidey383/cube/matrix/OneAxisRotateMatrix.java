package ru.sidey383.cube.matrix;

public class OneAxisRotateMatrix extends SimpleMatrix {


    public OneAxisRotateMatrix(Matrix matrix) {
        super(matrix);
    }

    public OneAxisRotateMatrix(double[][] matrix) {
        super(matrix);
    }

    public OneAxisRotateMatrix(int dimension, int a, int b, double angle) {
        super(new double[dimension][dimension]);
        if(a == b || a >= dimension || b >= dimension) {
            throw new IllegalArgumentException("wrong plane");
        }
        for(int i = 0; i < dimension; i++) {
            values[i][i] = 1;
        }
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        values[a][a] = cos;
        values[b][b] = cos;
        values[a][b] = a < b ? -sin : sin;
        values[b][a] = a > b ? -sin : sin;
    }

}
