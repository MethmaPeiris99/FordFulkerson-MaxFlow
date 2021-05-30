import java.lang.*;
import java.util.LinkedList;

public class MaxFlow {

    //----------------- Declaring the class variables -----------------//
    private static int numberOfVertices;
    private static int sourceNodeValue;
    private static int sinkNodeValue;

    /*Method used to find the shortest paths from the source node to other nodes in a input Residual graph by implementing the
      Breadth-First Search algorithm*/
    public static boolean implementBreadthFirstSearch(int[][] inputResidualGraph, int inputValueOfSourceNode, int inputValueOfSinkNode, int[] listOfVisitedPaths){

        boolean[] listOfVisitedNodes = new boolean[numberOfVertices]; /*Array used to indicate whether a particular node in the residual graph is visited or not
                                                                        when finding the shortest path to a particular node using Breadth-First Search.

                                                                       *Indexes of the array => Nodes in the Residual graph
                                                                       *Value of the index   => Indicates whether the node is visited or not
                                                                           Ex:- If the value of the index is true  => Node is visited,
                                                                                If the value of the index is false => Node is not visited*/
        for (int i = 0; i < numberOfVertices; i++) {
            listOfVisitedNodes[i] = false; //Mark all nodes as not visited
        }

        LinkedList<Integer> queue = new LinkedList<Integer>(); /*Linked list created to implement the Breath-First Search
                                                                *Queue data structure was implemented by using the Linked list*/

        queue.add(inputValueOfSourceNode); //Add the source node to the Linked list

        listOfVisitedNodes[inputValueOfSourceNode] = true; /*Mark the source node as "Visited" by changing the value to 'true'
                                                             in the array which is used to indicate visited nodes*/

        listOfVisitedPaths[inputValueOfSourceNode] = -1;

        //------- Implementation of the Breath-First Search algorithm -------//
        while (queue.size() != 0) { //Check whether the Linked list is empty or not
            int firstValueOfLinkedList = queue.poll(); /*Initialize the returned initial value which is the source node is deleted from the Linked list
                                                         by using the poll() method*/

            for (int v = 0; v < numberOfVertices; v++) {
                /*Check whether a particular node (v) is visited or not and Check whether there is an Edge from the first value of Linked list
                  to that particular node by checking the capacity value of the Edge.
                 *If the capacity is ZERO then there's no Edge connecting those nodes
                        Ex:- inputResidualGraph[firstValueOfLinkedList][3] = 0
                                -The capacity of the Edge from source node to node 3 is ZERO.
                                -Because of that there;s no Edge from source node to node 3.*/
                if (!listOfVisitedNodes[v] && inputResidualGraph[firstValueOfLinkedList][v] > 0) {

                    if (v == inputValueOfSinkNode) { //Check whether node v is equal to the sink node or not
                        listOfVisitedPaths[v] = firstValueOfLinkedList; /*Add the Edge between the sink node and the node v to the array
                                                                          which indicates the visited paths*/
                        return true;
                    }
                    queue.add(v); //Add nodes of the graph to the Linked list according to the incrementation of the value of nodes
                    listOfVisitedPaths[v] = firstValueOfLinkedList; /*Add the first value of the Linked list which is the value before the node v
                                                                      to the array which indicates the visited paths*/

                    listOfVisitedNodes[v] = true; //Initializing node v as a visited node
                }
            }
        }
        return false; //Return false if still unable to reached the sink node starting from source node
    }

    //-------- Method used to return the maximum flow of a given flow network --------//
    public static int fordFulkerson(int[][] inputGraph, int inputValueOfSourceNode, int inputValueOfSinkNode, int inputOfNodes) {

        numberOfVertices = inputOfNodes;

        int startingNodeValue;
        int endingNodeValue;

        int[][] residualGraph = new int[numberOfVertices][numberOfVertices]; //Initializing the residual graph
        int[][] updatedResidualGraph = new int[numberOfVertices][numberOfVertices]; /*Initializing the residual graph used to
                                                                                      display the implementation of ford fulkerson*/

        //------------- Creating the residual graph by using the input graph values -------------//
        for (startingNodeValue = 0; startingNodeValue < numberOfVertices; startingNodeValue++) {
            for (endingNodeValue = 0; endingNodeValue < numberOfVertices; endingNodeValue++)
                residualGraph[startingNodeValue][endingNodeValue] = inputGraph[startingNodeValue][endingNodeValue];
        }

        int[] listOfPaths = new int[numberOfVertices]; //Array used to store the augmented paths

        int maxFlowValue = 0; //Initializing the maximum flow value; Initially the flow value is zero

        while (implementBreadthFirstSearch(residualGraph, inputValueOfSourceNode, inputValueOfSinkNode, listOfPaths)) {
            int pathFlowValue = Integer.MAX_VALUE; /*Initializing the flow of a given path by assigning the maximum possible integer value an integer can have.
                                                    *ASSUMPTION : In the initial state the capacity of a given node is considered as an infinite value*/


            //Calculate the residual capacity of each augmenting path//
            for (endingNodeValue = inputValueOfSinkNode; endingNodeValue != inputValueOfSourceNode; endingNodeValue = listOfPaths[endingNodeValue]) {
                startingNodeValue = listOfPaths[endingNodeValue];
                pathFlowValue = Math.min(pathFlowValue, residualGraph[startingNodeValue][endingNodeValue]); //Initializing the minimum flow value
            }

            //Assign the residual capacity to both forward and backward edges of an augmenting path//
            for (endingNodeValue = inputValueOfSinkNode; endingNodeValue != inputValueOfSourceNode; endingNodeValue = listOfPaths[endingNodeValue]) {
                startingNodeValue = listOfPaths[endingNodeValue];
                residualGraph[startingNodeValue][endingNodeValue] -= pathFlowValue;
                residualGraph[endingNodeValue][startingNodeValue] += pathFlowValue;
            }

            // Add path flow to overall flow
            maxFlowValue += pathFlowValue;
        }

        // Return the overall flow
        return maxFlowValue;
    }



}


