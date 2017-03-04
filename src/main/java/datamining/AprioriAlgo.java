package datamining;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by arjunsubramanyamvaralakshmi on 1/29/17.
 */
public interface AprioriAlgo {

    List<TreeSet<Itemset>> run();
    void createBitsetOfTransactions();
    TreeSet<Itemset> firstPass();
}
