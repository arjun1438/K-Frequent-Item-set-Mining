package datamining;

import java.util.TreeSet;

/**
 * Created by arjunsubramanyamvaralakshmi on 2/05/17.
 */

interface CandidateGeneration {

    TreeSet<Itemset> generateCandidateKeys(TreeSet<Itemset> frequentItemSetFromPreviousPass);
    TreeSet<Itemset> joinAndPrune(TreeSet<Itemset> frequentItemSetFromPreviousPass);

}
