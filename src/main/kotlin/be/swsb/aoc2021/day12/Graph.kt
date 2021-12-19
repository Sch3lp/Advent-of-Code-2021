package be.swsb.aoc2021.day12

typealias Node = String
typealias Path = List<Node>
typealias PathBuilder = MutableList<Node>
typealias Edge = Pair<Node, Node>

class Graph(private val edgeList: List<Edge>) {

    private val uniqueNodes = edgeList.flatMap { edge -> edge.toList() }.toSet()
    private val adjacencyList: Map<Node, List<Edge>> by lazy {
        val directedEdges = edgeList.groupBy { edge -> edge.first }
        val reversedEdges = edgeList.groupBy ({ edge -> edge.second } ){ (first,second) -> second to first }
        val mergedEdges = (directedEdges.keys + reversedEdges.keys)
            .associateWith { node ->
                directedEdges.getOrDefault(node, emptyList()) + reversedEdges.getOrDefault(node, emptyList())
            }
        mergedEdges
    }

    private val visited: MutableMap<Node, Boolean>
        get() = uniqueNodes.associateWith { false }.toMutableMap()

    private fun neighboursOf(node: Node) = adjacencyList[node]?.map { it.second } ?: emptyList()

    fun findAllPaths(from: String = "start", to: String = "end"): List<Path> {
        return depthFirstSearch(from, to, visited, path = mutableListOf(from), paths = mutableListOf())
    }

    fun findAllPaths2(from: String = "start", to: String = "end"): List<Path> {
        return depthFirstSearch2(from, to, visited, path = mutableListOf(from), paths = mutableListOf())
    }

    private fun depthFirstSearch(
        from: Node,
        to: Node,
        visited: MutableMap<Node, Boolean>,
        path: PathBuilder = mutableListOf(),
        paths: MutableList<Path>
    ): MutableList<Path> {
        if (from == to) {
            paths += path.toList()
            return paths
        }
        visited[from] = from.isSmall()
        neighboursOf(from).forEach { node ->
            if (!visited.getValue(node)) {
                path += node
                depthFirstSearch(node, to, visited, path, paths)
                path.removeLast()
            }
        }
        visited[from] = false
        return paths
    }

    private fun depthFirstSearch2(
        from: Node,
        to: Node,
        visited: MutableMap<Node, Boolean>,
        path: PathBuilder = mutableListOf(),
        paths: MutableList<Path>
    ): MutableList<Path> {
        if (from == to) {
            paths += path.toList()
            return paths
        }
        visited[from] = from.isSmall()
        neighboursOf(from).forEach { node ->
            if (!visited.getValue(node)) {
                path += node
                depthFirstSearch(node, to, visited, path, paths)
                path.removeLast()
            }
        }
        visited[from] = false
        return paths
    }


    companion object {
        fun from(input: String): Graph = from(input.split("\n"))
        fun from(input: List<String>): Graph {
            val edges = input.map { edgeAsString ->
                val (from, to) = edgeAsString.split("-")
                from to to
            }
            return Graph(edges)
        }
    }
}

fun Node.isSmall(): Boolean = this.isAllLowercase()
fun String.isAllLowercase(): Boolean = this == this.lowercase()