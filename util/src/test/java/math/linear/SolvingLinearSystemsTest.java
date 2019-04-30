package math.linear;

import org.apache.commons.math3.linear.*;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

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

}
