package ru.sidey383.cube.matrix;

public class RotateMatrix extends SimpleMatrix {

    private static int getDimension(int angleCount) {
        double val = (1+Math.sqrt(1+8*angleCount))/2;
        if(val % 1 != 0)
            throw new IllegalArgumentException("wrong angle count");
        return (int)val;
    }

    public RotateMatrix(double[] angels) {
        super(new double[getDimension(angels.length)][getDimension(angels.length)]);
        double[][] oneVector = new double[getISize()][getJSize()];
        for(int i = 0; i < getISize(); i++) {
            oneVector[i][i] = 1;
        }
        Matrix matrix = new SimpleMatrix(oneVector);
        int angelNumber = 0;
        for(int i = 0; i < getISize(); i++) {
            for(int j = 0; j < i; j++) {
                matrix = matrix.multiple(new OneAxisRotateMatrix(getISize(), i, j, angels[angelNumber]));
                angelNumber++;
            }
        }
        this.values = matrix.getValues();
    }

}
