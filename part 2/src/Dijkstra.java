import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by malmardi on 11/26/2016.
 */
public class Dijkstra {

    HashMap<String, LinkedList<String>> distances = new HashMap<>();

    public void algorithm(String source, HashMap<String, Node> nodes) {
        //Initialization
        distances = new HashMap<>();
        for (String key : nodes.keySet()) {
            if (!key.equals(source)) {
                LinkedList<String> temp = new LinkedList<>();
                temp.add(source);
                temp.add("1000000.0");
                distances.put(key, temp);
            }
        }

        for (String key : nodes.get(source).adjacencyList.keySet()) {
            if (distances.containsKey(key)) {

                LinkedList<String> temp = new LinkedList<>();
                temp.add(source);
                temp.add(nodes.get(source).adjacencyList.get(key).toString());
                distances.replace(key, temp);
            }
        }

        LinkedList<String> N = new LinkedList<>();
        N.add(source);

        String oldNode = source;
        while (N.size() < nodes.size()) {

            double minCost = 100000.0;
            String minNode = "";
            for (String key : distances.keySet()) {
                if (Double.parseDouble(distances.get(key).get(1)) < minCost) {
                    minCost = Double.parseDouble(distances.get(key).get(1));
                    minNode = key;
                }
            }

            LinkedList<String> temp = new LinkedList<>();
            temp.add(distances.get(minNode).get(0).toString());
            temp.add(Double.toString(minCost));


            nodes.get(source).selectedNodes.put(minNode, temp);


            double oldCost = minCost;
            distances.remove(minNode);

            for (String key : nodes.get(minNode).adjacencyList.keySet()) {
                if (distances.containsKey(key))
                    if (Double.parseDouble(distances.get(key).get(1)) >= (nodes.get(minNode).adjacencyList.get(key) + oldCost)) {
                        LinkedList<String> temp2 = new LinkedList<>();
                        temp2.add(minNode);
                        temp2.add(Double.toString(nodes.get(minNode).adjacencyList.get(key) + oldCost));
                        distances.replace(key, temp2);

                    }
            }
            oldNode = minNode;
            N.add(minNode);


        }


    }

    public void algorithm2(String source, HashMap<String, Node> nodes) {
        //Initialization
        distances = new HashMap<>();
        for (String key : nodes.keySet()) {
            if (!key.equals(source)) {
                LinkedList<String> temp = new LinkedList<>();
                temp.add(source);
                temp.add("1000000.0");
                distances.put(key, temp);
            }
        }

        for (String key : nodes.get(source).adjacencyList.keySet()) {
            if (distances.containsKey(key)) {

                LinkedList<String> temp = new LinkedList<>();
                temp.add(source);
                temp.add(Double.toString(nodes.get(source).adjacencyList.get(key)));
                distances.replace(key, temp);
            }
        }

        LinkedList<String> N = new LinkedList<>();
        N.add(source);

        String oldNode = source;
        boolean flag = false;
        boolean flag2 = true;
        HashMap<String, Double> oldCostMap=new HashMap<>();
        while (flag2==true) {


            double minCost = 100000.0;
            String minNode = "";
            for (String key : distances.keySet()) {
                if (Double.parseDouble(distances.get(key).get(1)) < minCost) {
                    minCost = Double.parseDouble(distances.get(key).get(1));
                    minNode = key;
                }
            }
            if(!oldCostMap.containsKey(minNode))
                oldCostMap.put(minNode,minCost);
            else oldCostMap.replace(minNode,minCost);


            LinkedList<String> temp = new LinkedList<>();
            temp.add(distances.get(minNode).get(0));
            temp.add(Double.toString(minCost));


            nodes.get(source).selectedNodes.put(minNode, temp);


            double oldCost = minCost;
            distances.remove(minNode);
            flag = false;

            for (String key : nodes.get(minNode).adjacencyList.keySet()) {
                if (distances.containsKey(key) ) {
                    if (Double.parseDouble(distances.get(key).get(1)) >= (nodes.get(minNode).adjacencyList.get(key) + oldCost)) {
                        LinkedList<String> temp2 = new LinkedList<>();
                        temp2.add(minNode);
                        temp2.add(Double.toString(nodes.get(minNode).adjacencyList.get(key) + oldCost));
                        distances.replace(key, temp2);
                        flag = true;

                    }
                } else {

                    if(key.equals(source))
                        continue;
                    LinkedList<String> temp3 = new LinkedList<>();
                    temp3.add(key);
                    temp3.add(Double.toString(oldCostMap.get(key)));
                    distances.put(key, temp3);

                    if (Double.parseDouble(distances.get(key).get(1)) >= (nodes.get(minNode).adjacencyList.get(key) + oldCost)) {
                        LinkedList<String> temp2 = new LinkedList<>();
                        temp2.add(minNode);
                        temp2.add(Double.toString(nodes.get(minNode).adjacencyList.get(key) + oldCost));
                        distances.replace(key, temp2);
                        flag = true;

                    }
                    else distances.remove(key);
                }
            }
            if (flag == false)
                if (distances.size()==0)
                    flag2 = false;
            oldNode = minNode;
            N.add(minNode);


        }


    }
}
