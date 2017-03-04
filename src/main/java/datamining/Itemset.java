package datamining;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by arjunsubramanyamvaralakshmi on 2/05/17.
 */
public class Itemset implements Comparable <Object>
{
    List<String> itemList;
    int support;

    public Itemset(String key, Integer value) {
        itemList = new LinkedList<>();
        itemList.add(key);
        support = value;
    }

    public Itemset(Itemset a, Itemset b)
    {
           itemList = new LinkedList<>();
           itemList.addAll(a.itemList);
           itemList.add(b.itemList.get(b.itemList.size() - 1));
           support = 0;
    }

    @Override
    public int compareTo(Object object) {

        Itemset itemset = (Itemset) object;
        int i = 0;

        while( i < this.itemList.size())
        {
            if( !this.itemList.get(i).equals(itemset.itemList.get(i)) )
                return this.itemList.get(i).compareTo(itemset.itemList.get(i));
            i++;
        }
        return 0;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(itemList.get(0));
        for (int i = 1; i < itemList.size(); i++)
            sb.append(" " + itemList.get(i));

        return sb.toString() +" (" + support + ")";

    }
}
