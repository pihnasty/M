package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Combinatorics {

    public static void forDynamyc(int N, int n, int k, List<List<Long>> sample, List<Long> row) {
        if (n==0) {
            row = new ArrayList<>();
            for (long i=n; i<N; i++) {
                row.add(i);
            }
            sample.add(row);
            return;
        }
        for (long i = k; i < N; i++) {
            if (k == 0) {
                row = new ArrayList<>();
                for (int i2 = 0; i2 < n; i2++) {
                    row.add((long) -1);
                }
            }
            if (k == 0 || i > row.get(k - 1)) {
                row.set(k, i);
                if (k < n - 1) {
                    forDynamyc(N, n, k + 1, sample, row);
                } else {
                    List<Long> cloneRow = new ArrayList<>();
                    for (int iClone = 0; iClone < n; iClone++) {
                        cloneRow.add(row.get(iClone));
                    }
                    sample.add(cloneRow);
                }
            }
        }
    }

    public static List<List<Long>> reverse (int N, List<List<Long>> reverseFactorNumbersList) {
        List<List<Long>>  factorNumbersList = new ArrayList<>();
        List<Long> fullFactorNumbersRow = new ArrayList<>();
        for (int i=0; i<N; i++) {
            fullFactorNumbersRow .add((long) i);
        }
        reverseFactorNumbersList.forEach(
            reverseFactorNumbersRow -> {
                List<Long> factorNumbersRow = (List<Long>) ((ArrayList<Long>) fullFactorNumbersRow).clone();
                factorNumbersRow.removeAll(reverseFactorNumbersRow);
                factorNumbersList.add(factorNumbersRow);
            }
        );
        Collections.reverse(factorNumbersList);
        return factorNumbersList;
    }

    public static List<List<Long>> getVariants(int n, int fromN) {
        List<List<Long>> variants  = new ArrayList<>();
        if (n< fromN /2) {
            Combinatorics.forDynamyc(fromN,n,0,variants, null );
        } else {
            Combinatorics.forDynamyc(fromN, fromN -n,0,variants, null );
            if(n<fromN) {
                variants = Combinatorics.reverse (fromN, variants);
            }
        }
        return variants;
    }


}
