package ru.kpfu.itis.main;

import java.util.Arrays;

public class DataMining {
    public static void main(String[] args) {
        double[][] graphMatrix = new double[6][6];
        graphMatrix[0][0] = 0;
        graphMatrix[0][1] = 1;
        graphMatrix[0][2] = 0;
        graphMatrix[0][3] = 0;
        graphMatrix[0][4] = 0;
        graphMatrix[0][5] = 0;
        graphMatrix[1][0] = 0;
        graphMatrix[1][1] = 0;
        graphMatrix[1][2] = 1;
        graphMatrix[1][3] = 0;
        graphMatrix[1][4] = 0;
        graphMatrix[1][5] = 0;
        graphMatrix[2][0] = 0;
        graphMatrix[2][1] = 0;
        graphMatrix[2][2] = 0;
        graphMatrix[2][3] = 0;
        graphMatrix[2][4] = 0;
        graphMatrix[2][5] = 0;
        graphMatrix[3][0] = 0;
        graphMatrix[3][1] = 0;
        graphMatrix[3][2] = 1;
        graphMatrix[3][3] = 0;
        graphMatrix[3][4] = 1;
        graphMatrix[3][5] = 0;
        graphMatrix[4][0] = 1;
        graphMatrix[4][1] = 1;
        graphMatrix[4][2] = 0;
        graphMatrix[4][3] = 0;
        graphMatrix[4][4] = 0;
        graphMatrix[4][5] = 0;
        graphMatrix[5][0] = 0;
        graphMatrix[5][1] = 0;
        graphMatrix[5][2] = 0;
        graphMatrix[5][3] = 1;
        graphMatrix[5][4] = 0;
        graphMatrix[5][5] = 0;

        double[][] graphTranspose = transposeMatrix(graphMatrix);

        double[] h = new double[6];
        double[] a = new double[6];
        Arrays.fill(h, 1.0);
        for (int i = 0; i < 20; i++) {
            double[] LtH = multiplyVector(graphTranspose, h);
            for (int j = 0; j < a.length; j++) {
                double max = getMax(LtH);
                a[j] = LtH[j]/max;
            }
            double[] La = multiplyVector(graphMatrix, a);
            for (int k = 0; k < h.length; k++) {
                double max = getMax(La);
                h[k] = La[k]/max;
            }
        }

        for (int i = 0; i < h.length; i++) {
            System.out.println(a[i] + " ");
        }
        System.out.println("------");
        for (int i = 0; i < a.length; i++) {
            System.out.println(h[i] + " ");
        }

    }

    public static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    public static double getMax(double[] inputArray) {
        double maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }

    public static double[] multiplyVector(double[][] matrix, double[] vector) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (vector.length != n) throw new RuntimeException("Illegal vector dimension");
        double[] result = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                result[i] += matrix[i][j] * vector[j];
        return result;
    }

}
