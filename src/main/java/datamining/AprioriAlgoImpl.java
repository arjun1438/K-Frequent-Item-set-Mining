package datamining;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by arjunsubramanyamvaralakshmi on 1/29/17.
 */
public class AprioriAlgoImpl implements AprioriAlgo{

    int minSup, minNumOfItems;
    List<String> rawTransactions;
    int transactionCount;
    List<TreeSet<Itemset>> frequentItemsetList;
    Map<String, BitSet> itemBitSetTransactionMap;

    public AprioriAlgoImpl(List<String> rawTransactions, int minSupport, int minNumOfItems) {
        this.itemBitSetTransactionMap = new HashMap<>();
        this.frequentItemsetList = new LinkedList<TreeSet<Itemset>>();
        this.rawTransactions = rawTransactions;
        this.minSup = minSupport;
        this.minNumOfItems = minNumOfItems;
    }

    public List<TreeSet<Itemset>> run() {
        createBitsetOfTransactions();
        TreeSet<Itemset> frequentItemsFromFirstPass = firstPass();
        frequentItemsetList.add(frequentItemsFromFirstPass);
        final TreeSet<Itemset>[] frequentItemSetFromPreviousPass = new TreeSet[]{frequentItemsetList.get(0)};
        CandidateGeneration candidateGeneration = CandidateGenerationImpl.createCandidateGenerationImpl(transactionCount, itemBitSetTransactionMap, minSup);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for(int pass = 2; !frequentItemSetFromPreviousPass[0].isEmpty(); pass++)
                {
                    TreeSet<Itemset> candidates = candidateGeneration.generateCandidateKeys(frequentItemSetFromPreviousPass[0]);
                    frequentItemsetList.add(candidates);
                    frequentItemSetFromPreviousPass[0] = candidates;
                }

            }
        });
        executorService.shutdown();
        while (!executorService.isTerminated())
        {

        }
        return frequentItemsetList;
    }

    public void createBitsetOfTransactions() {

        for(String line: rawTransactions)
        {
                String[] strArray = line.split("\\s+");
                if(strArray.length >= minNumOfItems) {
                    for (String str : strArray) {
                        BitSet bitSet = new BitSet();
                        if (!itemBitSetTransactionMap.containsKey(str)) {
                            bitSet.set(transactionCount);
                            itemBitSetTransactionMap.put(str, bitSet);
                        }

                        bitSet = itemBitSetTransactionMap.get(str);
                        bitSet.set(transactionCount);
                    }
                    transactionCount++;
                }
        }
    }

    public TreeSet<Itemset> firstPass() {
        TreeSet<Itemset> firstPassFrequentItemSet = new TreeSet<>();

        itemBitSetTransactionMap.forEach( (key, value) -> {
            if(value.cardinality() >= minSup)
            {
                firstPassFrequentItemSet.add(new Itemset(key, value.cardinality()));
            }
        });

        return firstPassFrequentItemSet;

    }
}
