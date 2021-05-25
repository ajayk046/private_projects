import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Graph {

	public int G_adj[][]; //adjacency matrix of the graph
	public double profit[]; //profits of each node
	public int n; //number of vertices
	public ArrayList<Integer> parents = new ArrayList<>();



	/**
	 *  Take filename as parameter to the constructor
	 *  The file contains graph information as follows
	 *  first line contains, n, the number of vertices
	 *  then there is a nxn matrix of 0s and 1s indicating the adjacency matrix
	 *  finally there are n entries of double marking the profit
	 *  store vertex number in a variable and load adjcency matrix information into a 2d array
	 *  load profit information into an 1d array
	 *  @param filename
	 */

	public Graph(String filename) throws IOException {
		//implementations here
		File file = new File(filename);
		Scanner sc = new Scanner(file);



		if (sc.hasNext()) {
			n = Integer.valueOf(sc.nextLine());

		}
		this.G_adj = new int [n][n];

		profit = new double[n];

		for (int i = 0; i < n; i++) {

			for (int j = 0; j < n; j++) {
				G_adj[i][j] = Integer.valueOf(sc.next());
			}
			sc.nextLine();
		}

		int index = 0;
		for (int i = 0; i < n; i++) {
			profit[i] = Double.valueOf(sc.next());
		}
	}




	/**
	 * The function takes a vertex and finds the height of the tree subrooted at that vertex
	 * To find the height of the entire tree you will pass the vertex at which the tree is rooted.(aka root of the tree)
	 *
	 * @param V
	 * @return height of the tree rooted at the vertex
	 */

	public int height(int V){
		ArrayList<Integer> visited = new ArrayList<>();
		int height = dfsHeight(V,visited);

		return height;
	}


	public int dfsHeight(int V, ArrayList<Integer> visited) {

		int height = -1;

		if (visited.contains(V)) {
			return -1;
		}

		visited.add(V);
		for (int i = 0 ;i < n ; i++) {
			if (G_adj[V][i] == 1) {
				int temp = dfsHeight(i,visited);
				if (height < temp) {
					height = temp;
				}
			}
		}

		height += 1;
		return height;

	}


	/**
	 * The function takes a vertex that would be used to make rooted tree
	 * make a rooted tree with V
	 * Then write your implementations to find a set of vertices that returns maximum profit
	 * @param V
	 * @return ArrayList of Integers
	 */
	public ArrayList<Integer> pebbling(int V){

		ArrayList<Integer> pebbled = new ArrayList<Integer>();

		ArrayList<ArrayList<Integer>> tree = new ArrayList<>();
		boolean visited [] = new boolean[n];

		makeRootTree(V,visited,tree);

		ArrayList<Integer> rootNode = new ArrayList<>(Arrays.asList(V));
		tree.add(rootNode);

		ArrayList<Double> pebbledProfit = new ArrayList<>();
		ArrayList<Double> unPebbledProfit = new ArrayList<>();

		ArrayList<Integer> calculatedNodes = calculateProfits(tree, pebbledProfit, unPebbledProfit);

		for(int i = calculatedNodes.size() - 1; i >= 0; i--) {
			int parentNode = getParentNode(tree, calculatedNodes.get(i));
			if(!pebbled.contains(parentNode)) {
				if(pebbledProfit.get(i) >= unPebbledProfit.get(i)) {
					pebbled.add(calculatedNodes.get(i));
				}
			}
		}

		return pebbled;
	}


	public ArrayList<Integer> calculateProfits (ArrayList<ArrayList<Integer>> tree, ArrayList<Double> pebbledProfit, ArrayList<Double> unPebbledProfit) {
		int parentIndex = 0;
		ArrayList<Integer> calculatedNodes = new ArrayList<>();
		for(int i =0; i<tree.size(); i++) {
			ArrayList<Integer> nodes = tree.get(i);
			parentIndex = -1;
			for(int j =0; j < nodes.size(); j++) {
				parentIndex = parents.indexOf(nodes.get(j));
				if(parentIndex == -1) {
					pebbledProfit.add(profit[nodes.get(j)]);
					unPebbledProfit.add(0.0);
				} else {
					double pebbledValue = profit[nodes.get(j)];
					double unPebbledValue = 0;
					ArrayList<Integer> children = tree.get(parentIndex);
					for(int k = 0; k < children.size(); k++) {
						int index = calculatedNodes.lastIndexOf(children.get(k));
						pebbledValue = pebbledValue + unPebbledProfit.get(index);
						unPebbledValue = unPebbledValue + Math.max(pebbledProfit.get(index), unPebbledProfit.get(index));
					}
					pebbledProfit.add(pebbledValue);
					unPebbledProfit.add(unPebbledValue);
				}
				calculatedNodes.add(nodes.get(j));
			}
		}
		return calculatedNodes;
	}

	public int getParentNode (ArrayList<ArrayList<Integer>> tree, Integer childNode) {
		int parentNode = -1;
		for(int i=0; i < tree.size(); i++) {
			ArrayList<Integer> nodes = tree.get(i);
			if(nodes.contains(childNode)) {
				parentNode = (i >= parents.size() ? -1 : parents.get(i));
			}
		}
		return parentNode;
	}


	public void makeRootTree (int V, boolean [] visited, ArrayList<ArrayList<Integer>> newList) {
		if (visited[V]) {
			return;
		}

		visited[V] = true;
		ArrayList<Integer> children = new ArrayList<>();

		for (int i = 0 ;i < n ; i++) {
			if (G_adj[V][i] == 1 && !visited[i]) {
				children.add(i);
				makeRootTree(i,visited,newList);
			}
		}

		if(children.size() >0) {
			parents.add(V);
			newList.add(children);
		}

	}


	public static void main(String[] args) throws IOException{
		Graph graph = new Graph("test/task2inputRoot (1).txt");

		for (int i = 0; i < graph.n ; i++) {
			for (int j = 0; j< graph.n ; j++) {
				System.out.print((graph.G_adj[i][j]));
			}
			System.out.println("");
		}

		System.out.println(graph.height(0));
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		graph.makeRootTree(0,new boolean[graph.n], list);
		System.out.println(graph.parents);
		System.out.println(list);
		System.out.println(graph.pebbling(0));
	}
}


//
//    visited[V] = true;
//
//            ArrayList<Integer> children = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//        if (G_adj[V][i] == 1) {
//        if (!visited[G_adj[V][i]]) {
//        children.add(V);
//        newList = makeRootTree(i,visited,newList);
//        }
//        }
//        }
//
//        newList.add(children);
//        return newList;