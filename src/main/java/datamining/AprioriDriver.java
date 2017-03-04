package datamining;

import javafx.scene.shape.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.SyncFailedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TreeSet;

/**
 * Created by arjunsubramanyamvaralakshmi on 2/06/17.
 */
public class AprioriDriver {

    public static void main(String[] args)
    {
        if(args.length != 4)
        {
            System.out.println("Provide the arguments correctly see the sample below");
            System.out.println("java datamining.AprioriDriver <min support> <K (min number of items)> <input file> <output file>");
            System.exit(0);
        }

        Long startTime = System.currentTimeMillis();
        int minSupport = Integer.parseInt(args[0]);
        int minNumOfItems = Integer.parseInt(args[1]);
        String inputFileName = args[2];
        String outputFileName = args[3];

        try
        {
            List<String> rawTransactions = Files.readAllLines(Paths.get(inputFileName));
            AprioriAlgoImpl aprioriAlgo = new AprioriAlgoImpl(rawTransactions, minSupport, minNumOfItems);
            List<TreeSet<Itemset>> frequentItemsetList = aprioriAlgo.run();

            FileWriter fileWriter = new FileWriter(new File(outputFileName));

            frequentItemsetList.forEach( list -> {
                list.forEach( itemset -> {
                    try {
                        if(itemset.itemList.size() >= minNumOfItems)
                            fileWriter.write(itemset.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });

            Long endTime = System.currentTimeMillis();

            System.out.println("\n Time taken in seconds: "+(endTime - startTime)/1000);
            fileWriter.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
