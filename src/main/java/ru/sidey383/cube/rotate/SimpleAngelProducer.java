package ru.sidey383.cube.rotate;

import java.util.Arrays;

public class SimpleAngelProducer implements AngelProducer {

    private final int dimensions;

    public SimpleAngelProducer(int dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public double[] getAngels(double pose) {
        double[] angels = new double[dimensions*(dimensions-1)/2];
        Arrays.fill(angels, pose * Math.PI / 2);
        return angels;
    }
}
