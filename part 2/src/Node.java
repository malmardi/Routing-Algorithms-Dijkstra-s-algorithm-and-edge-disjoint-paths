import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by malmardi on 11/25/2016.
 */
public class Node {

    String id;
    double cost;
    HashMap<String, Double> adjacencyList=new HashMap<>();
    HashMap<String,LinkedList<String>> selectedNodes=new HashMap<>();
    double minCost=100000;
    int minNode;

    public  Node(String _id)
    {
        id=_id;

    }

    /*public void shortest()
    {

        for(Integer key:adjacencyList.keySet())
        {
            if(adjacencyList.get(key)<minCost) {
                minCost = adjacencyList.get(key);
                minNode=key;

            }
        }
    }*/
}
