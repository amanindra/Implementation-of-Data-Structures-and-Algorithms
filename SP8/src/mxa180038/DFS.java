/** Starter code for SP8
 *  @author Manindra Kumar Anantaneni,  Venkata Naga Sai Rohith Reddy Isukapalli
 */

// change to your netid
package mxa180038;

import mxa180038.Graph.*;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
     int topNum = g.size();
     boolean hasCycle = false;


    public static class DFSVertex implements Factory {
        int cno;
        boolean seen;
        boolean marked;
        Vertex parent;
        boolean onStack;
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


    public static DFS depthFirstSearch(Graph g) throws Exception {

        DFS dfs = new DFS(g);
        int cno = 0;

        for(Vertex u:g){
            if(!dfs.get(u).seen) dfs.dfsVisit(u, ++cno);
        }

        if(dfs.hasCycle) throw new Exception("Cycle Found");

        return dfs;

    }


    //Helper method for dfs
    public void dfsVisit(Vertex u, int cno){

        get(u).cno = cno;

        if(!get(u).seen) {
            get(u).seen = true;

            Deque<Edge> inout = new LinkedList<>();

            for(Edge e: g.adj(u).outEdges) inout.add(e);
            for(Edge e: g.adj(u).inEdges) inout.add(e);


            for (Edge e : inout) {
                Vertex v = e.otherEnd(u);
                if (!get(v).seen) {
                        get(v).parent = u;
                        dfsVisit(v, cno);
                }
            }

            get(u).top = topNum--;

            finishedList.addFirst(u);

        }
    }



    public void findCycle(Graph g, Vertex v){
        get(v).marked = true;
        get(v).onStack = true;

        for(Edge w: g.adj(v).outEdges){
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

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() throws Exception {
        //findCycle(g, g.getVertex(0) );
        if (!this.hasCycle)
        return (List<Vertex>) this.depthFirstSearch(g).finishedList;
        throw new Exception("Cycle found");
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() throws Exception {
        depthFirstSearch(g);
        int[] cno = new int[g.size()];
        int i = 0;
        for(Vertex u: g) {
            cno[i] = get(u).cno;
            i++;
        }
        return maxValue(cno);
    }

    public int maxValue(int array[]){
        int max = Arrays.stream(array).max().getAsInt();
        return max;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public  static List<Vertex> topologicalOrder1(Graph g) throws Exception {
        DFS d = new DFS(g);

        depthFirstSearch(g);
        //d.hasCycle = hasCycle;
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        //String string = "10 12  1 3 1  1 8 1  2 4 1  3 1 1  4 7 1  5 4 1  5 10 1  6 8 1  6 10 1  8 2 1  8 5 1  10 9 1 0"; // Input with Cycle

        String string = "10 12  1 3 1  1 8 1  2 4 1  3 2 1  4 7 1  5 4 1  5 10 1  6 8 1  6 10 1  8 2 1  8 5 1  10 9 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        DFS d = depthFirstSearch(g);

        int numcc = d.connectedComponents();
        System.out.println("Number of components: " + numcc + "\nu\tcno");
        for(Vertex u: g) {
            System.out.println(u + "\t" + d.cno(u));
        }

        for(Vertex u: topologicalOrder1(g)){
            System.out.println(u);
        }
    }
}