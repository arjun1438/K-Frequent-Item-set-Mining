package datamining;

import java.util.*;

/**
 * Created by arjunsubramanyamvaralakshmi on 2/05/17.
 */
public class CandidateGenerationImpl implements CandidateGeneration{

    private final Map<String, BitSet> itemBitSetTransactionMap;
    int numOfTransactions;
    int minSup;

    private CandidateGenerationImpl(int transactionCount, Map<String, BitSet> itemBitSetTransactionMap, int minSup) {
        this.numOfTransactions = transactionCount;
        this.itemBitSetTransactionMap = itemBitSetTransactionMap;
        this.minSup = minSup;
    }

    public static CandidateGenerationImpl createCandidateGenerationImpl(int transactionCount, Map<String, BitSet> itemBitSetTransactionMap, int minSup) {
        return new CandidateGenerationImpl(transactionCount, itemBitSetTransactionMap, minSup);
    }


    public TreeSet<Itemset> generateCandidateKeys(TreeSet<Itemset> frequentItemSetFromPreviousPass) {
        return joinAndPrune(frequentItemSetFromPreviousPass);

    }

    private boolean containsAllSubsets(TreeSet<Itemset> frequentItemSetFromPreviousPass, Itemset candidate) {
        for(int i = 0; i < candidate.itemList.size(); i++)
        {
            String itemdeleted = candidate.itemList.remove(i);

            if(!frequentItemSetFromPreviousPass.contains(candidate))
                return false;

            candidate.itemList.add(i, itemdeleted);
        }
        return true;
    }

    public TreeSet<Itemset> joinAndPrune(TreeSet<Itemset> frequentItemSetFromPreviousPass) {
        TreeSet<Itemset> candidates = new TreeSet<>();
        Iterator<Itemset> iterator = frequentItemSetFromPreviousPass.iterator();
        Itemset candidate = null;

        while(iterator.hasNext())
        {
           Itemset a = iterator.next();
           Iterator<Itemset> tailSetIterator = frequentItemSetFromPreviousPass.tailSet(a).iterator();
           while (tailSetIterator.hasNext())
           {
               Itemset b = tailSetIterator.next();
               boolean success = verifyJoin(a, b);

               if(success)
               {
                   candidate = new Itemset(a, b);
                   candidate.support = getSupportForNewItemset(candidate);
                   if (candidate.support >= minSup && containsAllSubsets(frequentItemSetFromPreviousPass, candidate)) //pruning here
                       candidates.add(candidate);
               }
               

           }

        }

        return candidates;
    }

    private int getSupportForNewItemset(Itemset candidate) {
        BitSet bitSet = new BitSet(numOfTransactions);
        bitSet.set(0, numOfTransactions);

        candidate.itemList.forEach( item -> {
            bitSet.and(itemBitSetTransactionMap.get(item));
        });
        return bitSet.cardinality();
    }

    private boolean verifyJoin(Itemset a, Itemset b) {

        int i = 0;

        String itemOne;
        String itemTwo;

        for( i = 0; i < a.itemList.size() - 1; i++)
        {
            itemOne = a.itemList.get(i);
            itemTwo = b.itemList.get(i);

            if(!itemOne.equals(itemTwo))
                return false;
        }

        itemOne = a.itemList.get(i);
        itemTwo = b.itemList.get(i);

        return itemOne.compareTo(itemTwo) < 0;

    }
}
