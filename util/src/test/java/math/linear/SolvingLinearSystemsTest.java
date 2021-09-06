package math.linear;

import org.apache.commons.math3.linear.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SolvingLinearSystemsTest {

    double eps = 0.001;

    @Test
    public void  getSolutionTest () {

        SolvingLinearSystems solvingLinearSystems = new SolvingLinearSystems();

        double[][] arrayCoefficients = new double[][]
                {{2, 3, -2},
            {-1, 7, 6},
            {4, -3, -5}};

        double[] arrayfreeTerm = new double[] {1, -2, 1};



        List<List<Double>> listCoefficients = SolvingLinearSystems.convertArrayToList2(arrayCoefficients);
        List<Double> listfreeTerm = SolvingLinearSystems.convertArrayToList(arrayfreeTerm);

         List<Double> solution =  solvingLinearSystems.getSolution(listCoefficients,listfreeTerm);

        double[] result = new double[3];

       for (int row = 0; row < arrayCoefficients.length; row++) {
            for (int column = 0; column < arrayCoefficients[row].length; column++) {
                result[row] = result[row] + arrayCoefficients[row][column] * solution.get(column);
            }
        }

        System.out.println();
    }

    @Test
    public void convertListToArrayTest() {
        double[] arrayOriginal = new double [] {1.0,2.0,3.0,4.0};
        List<Double> list = SolvingLinearSystems.convertArrayToList(arrayOriginal);
        double[] array = SolvingLinearSystems.convertListToArray(list);
        assertArrayEquals(array , arrayOriginal, eps );
    }

    @Test
    public void convertListToArray2Test() {
        double[][] arrayOriginal = new double [][] {{1.0,2.0,3.0,4.0}
                                                   ,{1.1,2.1,3.1,4.1}
                                                   ,{1.2,2.2,3.2,4.2}};

        List<List<Double>> list =  SolvingLinearSystems.convertArrayToList2(arrayOriginal);
        double[][] array = SolvingLinearSystems.convertListToArray2(list);
        assertArrayEquals(array , arrayOriginal);
    }

    @Test
    public void solution200x200() {
        int N =  4000;

        Long startTime = System.currentTimeMillis();


        double[][] g = new double [N][N];
        double[] x = new double [N];
        double[] b = new double [N];

          Random random = new Random(System.currentTimeMillis());

        for (int i = 0 ; i<N; i++)  {
            for (int j = 0 ; j<N; j++) {
                g[i][j]=random.nextDouble();
            }
        }


        for (int i = 0 ; i<N; i++)  {
            x[i]=i+1;
        }
        for (int i = 0 ; i<N; i++)  {
            b[i] = 0.0;
            for (int j = 0 ; j<N; j++) {
                b[i] = b[i]+ g[i][j]*x[j];
            }
        }
        Long putElementTime = System.currentTimeMillis();
        System.out.println("Time="  +(putElementTime-startTime));


        RealMatrix coefficients =
                new Array2DRowRealMatrix(g,
                        false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();


        RealVector constants = new ArrayRealVector( b, false);
        RealVector solution = solver.solve(constants);

        Long solutionTime = System.currentTimeMillis();
        System.out.println("Time="+(solutionTime-putElementTime));

        System.out.println(" --------------- ");


    }

    @Test
    public void multiplyTest() {

        List<Double> xS = new ArrayList<>();
        xS.add(0.9);
        xS.add(0.1);
        xS.add(0.8);

        List<List<Double>> wS = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        List<Double> row3 = new ArrayList<>();
        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);
        row3.add(0.1);        row3.add(0.5);        row3.add(0.6);
        wS.add(row1);
        wS.add(row2);
        wS.add(row3);


        List<Double> xStest = new ArrayList<>();
        xStest.add(1.16);
        xStest.add(0.42);
        xStest.add(0.62);

        List<Double> output = SolvingLinearSystems.multiply(wS,xS);


        for(int i=0; i<output.size();i++) {
            assertEquals(xStest.get(i), output.get(i), 0.001);
        }
        System.out.println();
    }

    @Test
    public void inverseMatrixTest() {

        List<List<Double>> wS = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        List<Double> row3 = new ArrayList<>();
        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);
        row3.add(0.1);        row3.add(0.5);        row3.add(0.6);
        wS.add(row1);
        wS.add(row2);
        wS.add(row3);

        List<List<Double>> wSinverse = SolvingLinearSystems.inverseMatrix(wS);
        List<List<Double>> wSinverse2 = SolvingLinearSystems.inverseMatrix(wSinverse);

        for(int i1=0; i1<wSinverse2.size(); i1++) {
            for(int i2=0; i2<wSinverse2.get(0).size(); i2++) {
                assertEquals(wS.get(i1).get(i2), wSinverse2.get(i1).get(i2), 0.001);
            }
        }

//===================================================================================================
        List<List<Double>> wSrow4 = new ArrayList<>();
        List<Double> row4 = new ArrayList<>();
        row4.add(1.2);        row4.add(2.0);        row4.add(4.0);
        wSrow4.add(row4);


  //      List<List<Double>> wSinverse3 = SolvingLinearSystems.inverseMatrix(wSrow4);


        System.out.println();
    }

    @Test
    public void transponeMatrixTest() {

        List<List<Double>> wS = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        List<Double> row3 = new ArrayList<>();
        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);
        row3.add(0.1);        row3.add(0.5);        row3.add(0.6);
        wS.add(row1);
        wS.add(row2);
        wS.add(row3);

        List<List<Double>> wStranspone = SolvingLinearSystems.transponeMatrix(wS);


        for(int i1=0; i1<wStranspone.size(); i1++) {
            for(int i2=0; i2<wStranspone.get(0).size(); i2++) {
                assertEquals(wS.get(i2).get(i1), wStranspone.get(i1).get(i2), 0.001);
            }
        }



        System.out.println();
    }

    @Test
    public void proportionalChangeMatrixTest() {

        List<List<Double>> wS = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        List<Double> row3 = new ArrayList<>();
        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);
        row3.add(0.1);        row3.add(0.5);        row3.add(0.6);
        wS.add(row1);
        wS.add(row2);
        wS.add(row3);

        List<Double> changeRow = new ArrayList<>();
        changeRow.add(0.5);        changeRow.add(0.25);        changeRow.add(0.1);


        List<List<Double>> wSchange = SolvingLinearSystems.proportionalChangeMatrix(wS,changeRow);


        for(int i1=0; i1<wSchange.size(); i1++) {
            for(int i2=0; i2<wSchange.get(0).size(); i2++) {
                assertEquals(wS.get(i1).get(i2)*changeRow.get(i1), wSchange.get(i1).get(i2), 0.001);
            }
        }



        System.out.println(wSchange);
    }

    @Test
    public void multiplyColumnToRowTest() {


        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        row1.add(1.0);        row1.add(2.0);        row1.add(3.0);
        row2.add(4.0);        row2.add(5.0);        row2.add(6.0);



        List<List<Double>> wSchange = SolvingLinearSystems.multiplyColumnToRow(row1,row2);


//        for(int i1=0; i1<wSchange.size(); i1++) {
//            for(int i2=0; i2<wSchange.get(0).size(); i2++) {
//                assertEquals(wS.get(i1).get(i2)*changeRow.get(i1), wSchange.get(i1).get(i2), 0.001);
//            }
//        }



        System.out.println(wSchange);
    }

    @Test
    public void subtractMatrixTest() {

        List<List<Double>> wS = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        List<Double> row3 = new ArrayList<>();
        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);
        row3.add(0.1);        row3.add(0.5);        row3.add(0.6);
        wS.add(row1);
        wS.add(row2);
        wS.add(row3);


        List<List<Double>> wS2 = new ArrayList<>();
        List<Double> row12 = new ArrayList<>();
        List<Double> row22 = new ArrayList<>();
        List<Double> row32 = new ArrayList<>();
        row12.add(0.9);        row12.add(0.3);        row12.add(0.4);
        row22.add(0.2);        row22.add(0.8);        row22.add(0.2);
        row32.add(0.1);        row32.add(0.5);        row32.add(0.6);
        wS2.add(row12);
        wS2.add(row22);
        wS2.add(row32);

        List<List<Double>> deltaWs2 = SolvingLinearSystems.subtract(wS,wS2);


        for(int i1=0; i1<deltaWs2.size(); i1++) {
            for(int i2=0; i2<deltaWs2.get(0).size(); i2++) {
                assertEquals(0,deltaWs2.get(i1).get(i2), 0.001);
            }
        }



        System.out.println(deltaWs2);
    }

    @Test
    public void multiplyValueAndMatrixTest () {
        List<List<Double>> matrix = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();

        row1.add(0.9);        row1.add(0.3);        row1.add(0.4);
        row2.add(0.2);        row2.add(0.8);        row2.add(0.2);

        matrix.add(row1);
        matrix.add(row2);

        Double value = 10.0;

        List<List<Double>> result = SolvingLinearSystems.multiply(value , matrix);


        for(int i1=0; i1<result.size(); i1++) {
            for(int i2=0; i2<result.get(0).size(); i2++) {
                assertEquals(matrix.get(i1).get(i2)*value,result.get(i1).get(i2), 0.001);
            }
        }

        System.out.println(result);

    }

}
