package com.company;

import ru.vsu.cs.course1.graph.demo.GraphDemoFrame;
import ru.vsu.cs.util.ArrayUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

    private static int infinity = Integer.MAX_VALUE / 2;
    private static Map<Integer, Integer> distance = new TreeMap<>();

    public static void addRoad() throws FileNotFoundException {
        GraphDemoFrame graphDemoFrame = new GraphDemoFrame();
        Integer[] arr = ArrayUtils.toIntegerArray(graphDemoFrame.roads.getText());
        int firstPoint = 0;
        int secondPoint = 0;
        int inf = Integer.MAX_VALUE;
        int flag = 1;
        for (int i = 0; i <= arr.length; i++) {
            if ((flag == 1) && (i < arr.length)) {
                firstPoint = arr[i];
                flag++;
                i++;
            }
            if ((flag == 2) && (i < arr.length)) {
                secondPoint = arr[i];
                flag++;
                i++;
            }
            if (flag == 3) {
                inf = arr[i];
                flag = 1;
            }
            if (i < arr.length) {
                distance.put(firstPoint * 10 + firstPoint, 0);
                distance.put(secondPoint * 10 + secondPoint, 0);
                distance.put(firstPoint * 10 + secondPoint, inf);
                distance.put(secondPoint * 10 + firstPoint, inf);
                inf = Integer.MAX_VALUE;
            } else {
                break;
            }
        }
    }

    public static Integer[] getNodes() throws FileNotFoundException {
        Integer[] arr = ArrayUtils.readIntArrayFromFile("in.txt");
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            if (((i + 1) % 3) != 0) {
                set.add(arr[i]);
            }
        }
        return set.toArray(new Integer[0]);
    }

    public static int getMinRoads(int[][] matrix, int start) throws FileNotFoundException {
        int sumRoads = 0;
        int[] lambdas = new int[Graph.getNodes().length];
        ArrayList<Integer> labels = new ArrayList<Integer>();

        for (int j = 0; j < Graph.getNodes().length; j++) {
            lambdas[j] = infinity;
        }
        lambdas[start] = 0;

        for (int k = 1; k <= Graph.getNodes().length - 1; k++) {
            for (int i = 0; i < Graph.getNodes().length; i++) {
                for (int j = 0; j < Graph.getNodes().length; j++) {
                    labels.add(lambdas[j] + matrix[j][i]);
                }
                lambdas[i] = minOfArrayList(labels);
                labels.clear();
            }
        }

        for (int v: lambdas) {
            sumRoads += v;
        }
        return sumRoads;
    }


    private static int minOfArrayList(ArrayList<Integer> array) {
        int min = infinity;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) < min) {
                min = array.get(i);
            }
        }
        return min;
    }

    public static int minWay() throws FileNotFoundException {
        int min = Graph.getMinRoads(Graph.getReachabilityMatrix(), 0);
        for (int i = 0; i < Graph.getNodes().length; i++) {
            if (min > Graph.getMinRoads(Graph.getReachabilityMatrix(), i)) {
                min = Graph.getMinRoads(Graph.getReachabilityMatrix(), i);
            }
        }
        return min;
    }

    public static String cityWithMinWay(int way) throws FileNotFoundException {

        for (Integer i = 0; i < Graph.getNodes().length; i++) {
            if (way == Graph.getMinRoads(Graph.getReachabilityMatrix(), i)) {
                return i.toString();
            }
        }
        return null;
    }

    public  static void printMatrix(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println(i + " " + j + " " + arr[i][j] + " ");
            }
        }
    }

    public static int[][] getReachabilityMatrix() throws FileNotFoundException {
        int[][] reachabilityMatrix = new int[Graph.getNodes().length][Graph.getNodes().length];
        for (int i = 0; i < reachabilityMatrix.length; i++) {
            for (int j = 0; j < reachabilityMatrix[0].length; j++) {
                if (distance.get(i * 10 + j) != null) {
                    reachabilityMatrix[i][j] = (int) distance.get(i * 10 + j);
                } else {
                    reachabilityMatrix[i][j] = Integer.MAX_VALUE / 2;
                }
            }
        }
        return reachabilityMatrix;
    }
}





