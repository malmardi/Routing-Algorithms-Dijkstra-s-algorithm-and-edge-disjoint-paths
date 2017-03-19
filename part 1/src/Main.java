import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;

import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * Created by malmardi on 11/25/2016.
 */
public class Main {


    static HashMap<String, Node> nodes = new HashMap<>();
    static HashMap<String, Node> nodesOld= new HashMap<>();;

    public static void main(String[] args) throws IOException {



        BufferedReader reader = new BufferedReader(new FileReader("graph.txt"));

        String test_split[];
        String text = null;
        int count = Integer.parseInt(reader.readLine());


        while ((text = reader.readLine()) != null) {
            test_split = text.split(" ");
            String id = test_split[1];
            String pred = test_split[0];
            double cost = Double.parseDouble(test_split[2]);


            if (!nodes.containsKey(id)) {
                nodes.put(id, new Node(id));
                nodes.get(id).adjacencyList.put(pred, cost);
                nodesOld.put(id, new Node(id));
                nodesOld.get(id).adjacencyList.put(pred, cost);
            } else {

                nodes.get(id).adjacencyList.put(pred, cost);
                nodesOld.get(id).adjacencyList.put(pred, cost);
            }

            if (!nodes.containsKey(pred)) {

                nodes.put(pred, new Node(pred));
                nodes.get(pred).adjacencyList.put(id, cost);
                nodesOld.put(pred, new Node(pred));
                nodesOld.get(pred).adjacencyList.put(id, cost);
            } else {

                nodes.get(pred).adjacencyList.put(id, cost);
                nodesOld.get(pred).adjacencyList.put(id, cost);
            }

        }

        /*for(Integer key:nodes.keySet())
        {
            nodes.get(key).shortest();
        }*/

        System.out.println("********************************************************************");
        System.out.println("Part1- Dijkstra Implementation");
        System.out.println("Notes: 1-Can accept nodes as letters or numbers. 2-Case sensitive");
        System.out.println("********************************************************************");
        Scanner reader2 = new Scanner(System.in);
        System.out.println("Please Enter Source Node: ");
        String source = reader2.nextLine();

        Scanner reader3 = new Scanner(System.in);
        System.out.println("Please Enter Destination Node: ");
        String destination = reader3.nextLine();


        Dijkstra obj = new Dijkstra();
        obj.algorithm(source, nodes);
        double oldCost = Double.parseDouble(nodes.get(source).selectedNodes.get(destination).get(1));
        LinkedList<String> path = new LinkedList<>();
        path.add(destination);
        String temp = nodes.get(source).selectedNodes.get(destination).get(0).toString();
        path.add(temp);
        while (!temp.equals(source)) {
            temp = nodes.get(source).selectedNodes.get(temp).get(0).toString();
            path.add(temp);
        }

        for (int i = path.size() - 1; i >= 1; i--) {
            System.out.print(path.get(i) + "->");

        }
        System.out.println(path.get(0) + "(cost:" + oldCost + ")");



    }


}
