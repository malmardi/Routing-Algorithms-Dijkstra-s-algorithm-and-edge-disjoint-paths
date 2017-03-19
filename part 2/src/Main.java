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
        System.out.println("Shortest Disjoint Path Pair Implementation");
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


            nodes.get(path.get(i)).adjacencyList.replace(path.get(i - 1), 10000.0);
            nodes.get(path.get(i - 1)).adjacencyList.replace(path.get(i), -nodes.get(path.get(i - 1)).adjacencyList.get(path.get(i)));
        }


        obj.algorithm2(source, nodes);

        double newCost = Double.parseDouble(nodes.get(source).selectedNodes.get(destination).get(1));

        LinkedList<String> path2 = new LinkedList<>();
        path2.add(destination);
        String temp2 = nodes.get(source).selectedNodes.get(destination).get(0).toString();
        path2.add(temp2);
        while (!temp2.equals(source)) {
            temp2 = nodes.get(source).selectedNodes.get(temp2).get(0).toString();
            path2.add(temp2);
        }

        LinkedList<String> edges1 = new LinkedList<>();

        for (int i = path.size() - 1; i >= 1; i--) {

            String s = path.get(i) + "," + path.get(i - 1);
            edges1.add(s);
        }

        LinkedList<String> edges2 = new LinkedList<>();
        for (int i = path2.size() - 1; i >= 1; i--) {

            String s = path2.get(i - 1) + "," + path2.get(i);
            edges2.add(s);
        }

        String node1 = "";
        String node2 = "";
        boolean interlace = false;
        for (int i = 0; i < edges1.size(); i++) {
            if (edges2.contains(edges1.get(i))) {
                interlace = true;
                String s[] = edges1.get(i).split(",");
                node1 = s[0].substring(0, 1);
                node2 = s[1].substring(0, 1);
                break;
            }

        }

        if (interlace == false) {
            for (int i = path.size() - 1; i >= 1; i--) {
                System.out.print(path.get(i) + "->");

            }
            System.out.println(path.get(0) + "(cost:" + oldCost + ")");

            for (int i = path2.size() - 1; i >= 1; i--) {
                System.out.print(path2.get(i) + "->");

            }
            System.out.println(path2.get(0) + "(cost:" + newCost + ")");

        } else {
            boolean flag = false;

            oldCost = 0;
            String pre = source;
            String post = "";
            for (int i = path.size() - 1; i >= 1; i--) {
                pre = path.get(i);
                post = path.get(i - 1);

                System.out.print(path.get(i) + "->");
                if (path.get(i).equals(node1)) {
                    flag = true;
                    break;
                }
                else
                {
                    if (!post.equals(""))
                        oldCost = oldCost + nodesOld.get(pre).adjacencyList.get(post);

                }
            }
            if (flag == true) {
                int index = 0;
                for (int i = path2.size() - 1; i >= 1; i--) {
                    if (path2.get(i).equals(node1))
                        index = i;
                }
                pre = path2.get(index);
                post = path2.get(index - 1);
                oldCost = oldCost + nodesOld.get(pre).adjacencyList.get(post);

                for (int i = index - 1; i >= 1; i--) {

                    pre = path2.get(i);
                    post = path2.get(i - 1);
                    oldCost = oldCost + nodesOld.get(pre).adjacencyList.get(post);
                    System.out.print(path2.get(i) + "->");
                }

            }
            System.out.println(path.get(0) + "(cost:" + oldCost + ")");

            flag = false;
            newCost=0;
            pre=source;
            post="";
            for (int i = path2.size() - 1; i >= 1; i--) {
                pre = path2.get(i);
                post = path2.get(i - 1);
                System.out.print(path2.get(i) + "->");
                if (path2.get(i).equals( node2)) {
                    flag = true;
                    break;
                }
                else
                {
                    if (!post.equals(""))
                        newCost = newCost + nodesOld.get(pre).adjacencyList.get(post);

                }

            }
            if (flag == true) {
                int index = 0;
                for (int i = path.size() - 1; i >= 1; i--) {
                    if (path.get(i).equals(node2))
                        index = i;
                }

                pre = path.get(index);
                post = path.get(index - 1);
                newCost = newCost + nodesOld.get(pre).adjacencyList.get(post);

                for (int i = index - 1; i >= 1; i--) {
                    pre = path.get(i);
                    post = path.get(i - 1);
                    newCost = newCost + nodesOld.get(pre).adjacencyList.get(post);

                    System.out.print(path.get(i) + "->");
                }

            }
            System.out.println(path2.get(0) + "(cost:" + newCost + ")");
        }


        System.out.println();


    }


}
