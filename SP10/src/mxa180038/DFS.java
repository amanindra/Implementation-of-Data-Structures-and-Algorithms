/** Starter code for SP8
 *  @author Manindra Kumar Anantaneni,  Venkata Naga Sai Rohith Reddy Isukapalli
 */

// change to your netid
package mxa180038;

import rbk.Graph;
import rbk.Graph.*;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
     int topNum = g.size();
     boolean hasCycle = false; //To flag if the graph has a cycle
     static int cno; // To keep track of the nodes and know which component they belong to

    public static class DFSVertex implements Factory {
        int cno; // To label the vertex with the component number to which it belongs
        boolean seen; // To keep track of the vertex and know whether it has been visited while performing a DFS
        boolean marked; // Used to keep track of if it is processed or being processed while determining if the graph has a cycle or not
        Vertex parent; // Assign the parent to the child
        boolean onStack; // Used to keep track of a node if it is in a stack for determining a back edge
        int top;
        public DFSVertex(Vertex u) {
            seen = false;
            onStack = false;
            parent = null;
            cno = 0;
            top = 0;
        }

        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        findCycle(g, g.getVertex(1) );
    }

    Deque<Vertex> finishedList = new LinkedList();

    /**
     * Returns an DFS object with Depth First Search performed on the graph
     * @return
     */
    private DFS depthFirstSearch(){
        DFS d = this;
        d.dfs(g);
        return d;
    }

    /**
     * During SCC we perform DFS twice, and we want to reset the values
     * @param iterable We pass a graph or a list
     */

    void initialize(Iterable<Vertex> iterable){
        for(Vertex u: iterable){
            get(u).seen = false;
            get(u).cno = 0;
        }
    }

    /**
     * Utility method that performs DFS
     * @param iterable
     */

    void dfs(Iterable<Vertex> iterable){
        initialize(iterable);
        finishedList = new LinkedList<>();
        cno = 0;

        for(Vertex u:iterable){
            if(!get(u).seen) {
                cno++;
                dfsVisit(u);
            }
        }
    }

    //Helper method for dfs
    private void dfsVisit(Vertex u){
        get(u).cno = cno;
        if(!get(u).seen) {
            get(u).seen = true;
            for (Edge e : g.incident(u)){
                Vertex v = e.otherEnd(u);
                if (!get(v).seen) {
                        get(v).parent = u;
                        dfsVisit(v);
                }
            }
            get(u).top = topNum--;
            finishedList.addFirst(u);
        }
    }

    /**
     * To check if the graph has a cycle or not by checking whether it has a back edge or not
     * @param g
     * @param v
     */

    private void findCycle(Graph g, Vertex v){
        get(v).marked = true;
        get(v).onStack = true;

        for(Edge w: g.incident(v)){
            Vertex u = w.otherEnd(v);
            if(!get(u).marked){
                findCycle(g, u);
            }
            else if(get(u).onStack){
                hasCycle = true;
                return;
            }
            get(u).onStack = false;
        }
    }

    /**
     * We return a DFS object. We perform the DFS twice. First, with the graph in the order of the graph,
     * and the second time with a graph that is reverse, and in the order of the finishedlist of the from the first DFS
     * @return
     */

    private DFS stronglyConnectedComponents(){
        DFS d = this;
        d.dfs(g);
        List<Vertex> list = (List<Vertex>) d.finishedList;

        g.reverseGraph();
        d.dfs(list);
        g.reverseGraph();
        return d;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() throws Exception {
        if (!this.hasCycle)
        return (List<Vertex>) this.depthFirstSearch().finishedList;
        throw new Exception("Cycle found");
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents(){
        this.dfs(g);
        return maxValue();
    }

    public int maxValue(){
        int[] cno = new int[g.size()];
        int i = 0;
        for(Vertex u: g) {
            cno[i] = get(u).cno;
            i++;
        }
        int max = Arrays.stream(cno).max().getAsInt();
        return max;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public  static List<Vertex> topologicalOrder1(Graph g) throws Exception {
        DFS d = new DFS(g);
        d.depthFirstSearch();
        return d.topologicalOrder1();
    }



    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    /**
     * Used to display the items in the graph based on their component numbers
     */

    void display(){
        int i = 1;
        System.out.print("{");
        while(i<=maxValue()) {
            if(i>1) System.out.print(", ");
            System.out.print("{");
            int j = 1;
            for (Vertex u : g) {
                if (get(u).cno == i)
                {
                    if(j>1) System.out.print(", ");
                    System.out.print(u);
                    j++;
                }
            }
            System.out.print("}");
            i++;
        }
        System.out.print("}");
    }

    public static void main(String[] args) throws Exception {
        //String string = "10 12  1 3 1  1 8 1  2 4 1  3 1 1  4 7 1  5 4 1  5 10 1  6 8 1  6 10 1  8 2 1  8 5 1  10 9 1 0"; // Input with Cycle

        String string = "10 12  1 3 1  1 8 1  2 4 1  3 2 1  4 7 1  5 4 1  5 10 1  6 8 1  6 10 1  8 2 1  8 5 1  10 9 1 0";
        //String string = "11 17  1 11 1  2 3 1  2 7 1  3 10 1  4 1 1  4 9 1  5 4 1 5 7 1  5 8 1    6 3 1  7 8 1  8 2 1  9 11 1  10 6 1 11 3 1  11 4 1 11 6 1  0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        DFS d = new DFS(g);

        for(Vertex u: d.depthFirstSearch().finishedList){
            System.out.print(u + " ");
        }
        System.out.println();
        d.display();
        System.out.println("\n______________________________________________");

        for(Vertex u: d.stronglyConnectedComponents().finishedList){
            System.out.print(u + " ");
        }
        System.out.println();
        d.display();
        System.out.println("\n______________________________________________");
    }
}