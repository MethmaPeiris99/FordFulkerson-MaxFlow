import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * NAME   : Ellawalage Methma Charuki Peiris
 * IIT ID : 2019449
 * UoW ID : w1761353
 **/

public class Main {
    public static void main(String[] args) throws java.lang.Exception {
        File file = new File("ladder_1.txt");
        Scanner scanner = new Scanner(file);

        String numberOfNodesInString = scanner.nextLine(); //Store the first line value of the file as String
        System.out.println("Number of nodes: "+numberOfNodesInString+"\n");

        String numberOfNodes = numberOfNodesInString.replaceAll(" ",""); //Replace the spaces in the string
        int numberOfNodesInInt = Integer.parseInt(numberOfNodes); //Convert  the string value of nodes to an Integer value

        int[][] graph = new int[numberOfNodesInInt][numberOfNodesInInt]; //Initialize the graph which represents the flow network

        while (scanner.hasNextLine()){ //Get the values excluding the first line of the input file
            String rowColumnAndCapacityValues = scanner.nextLine(); //Get the 3 values of each line of the file to a string
            //System.out.println(rowColumnAndCapacityValues);

            List<String> inputs = Arrays.asList(rowColumnAndCapacityValues.split(" ")); /*Split the spaces between two values of each line
                                                                                                and add to a list*/
            //System.out.println(inputs);

            /*Create the graph by assigning the first index (row) of the string to the outer array and second index (column) to the inner array
             *Assign the third index value of the string (capacity) as the value of the inner array*/
            graph[Integer.parseInt(inputs.get(0))][Integer.parseInt(inputs.get(1))] = Integer.parseInt(inputs.get(2));

        }

        System.out.println("------------------------------------------------");
        System.out.println("-------------- NETWORK FLOW GRAPH --------------");
        System.out.println("------------------------------------------------");
        System.out.println();
        for (int i = 0; i < numberOfNodesInInt; i++) {
            for (int j = 0; j < numberOfNodesInInt; j++) {
                System.out.format("%8s", String.valueOf(graph[i][j]));
            }
            System.out.println();
        }

        //System.out.println(Arrays.deepToString(graph));

        long startTime = System.currentTimeMillis(); //Get the time before executing the algorithm in milliseconds

        System.out.println("\nMAXIMUM FLOW: "
                + MaxFlow.fordFulkerson(graph, 0, numberOfNodesInInt-1,numberOfNodesInInt)+"\n");

        long endTime = System.currentTimeMillis(); //Get the time after executing the algorithm in milliseconds

        long timeElapsed = endTime - startTime; //Get the time spend to execute the algorithm in milliseconds

        System.out.println("\nExecution time in milli seconds  : " + timeElapsed);
    }
}
