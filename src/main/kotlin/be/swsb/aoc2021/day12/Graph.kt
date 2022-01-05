package be.swsb.aoc2021.day12

typealias Node = String
typealias Path = List<Node>
typealias PathBuilder = MutableList<Node>
typealias Edge = Pair<Node, Node>

class Graph(private val edgeList: List<Edge>) {

    private val uniqueNodes = edgeList.flatMap { edge -> edge.toList() }.toSet()
    private val adjacencyList: Map<Node, List<Edge>> by lazy {
        val directedEdges = edgeList.groupBy { edge -> edge.first }
        val reversedEdges = edgeList.groupBy({ edge -> edge.second }) { (first, second) -> second to first }
        val mergedEdges = (directedEdges.keys + reversedEdges.keys)
            .associateWith { node ->
                directedEdges.getOrDefault(node, emptyList()) + reversedEdges.getOrDefault(node, emptyList())
            }
        mergedEdges
    }

    private val visited: MutableMap<Node, Boolean>
        get() = uniqueNodes.associateWith { false }.toMutableMap()

    private val visitedAmounts: MutableMap<Node, Int>
        get() = uniqueNodes.associateWith { node -> if (node.isSmall()) 0 else -99 }.toMutableMap()

    private fun neighboursOf(node: Node) = adjacencyList[node]?.map { it.second } ?: emptyList()

    fun findAllPaths(from: String = "start", to: String = "end"): List<Path> {
        return depthFirstSearch(from, to, visited, path = mutableListOf(from), paths = mutableListOf())
    }

    fun findAllPaths2(from: String = "start", to: String = "end"): List<Path> {
        return depthFirstSearch2(from, to, visitedAmounts, path = mutableListOf(from), paths = mutableListOf())
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

    var specialNode: Node? = null
    private fun depthFirstSearch2(
        from: Node,
        to: Node,
        visits: MutableMap<Node, Int>,
        path: PathBuilder = mutableListOf(),
        paths: MutableList<Path>
    ): MutableList<Path> {
        if (from == to) {
            resetSpecialNode()
            paths += path.toList()
            return paths
        }
        visits.visit(from)
//        neighboursOf(from).firstOrNull { it.isSmall() && !it.isEnd() && !it.isStart() }?.markSpecial()
        neighboursOf(from).forEach { node ->
            if (node.isSmall() && !node.isEnd() && !node.isStart()) node.markSpecial()
            if (visits.canBeRevisited(node)) {
                path += node
                depthFirstSearch2(node, to, visits, path, paths)
                path.removeLast()
            }
        }
        visits.unvisit(from)
        return paths
    }

    private fun MutableMap<Node, Int>.visit(from: Node) {
        this[from] = this[from]!! + 1
    }

    private fun MutableMap<Node, Int>.unvisit(from: Node) {
        this[from] = this[from]!! - 1
    }

    private fun MutableMap<Node, Int>.reset(from: Node) {
        this[from] = 0
    }

    private fun MutableMap<Node, Int>.canBeRevisited(from: Node): Boolean = when {
        from.isSpecial() -> this[from]!! < 2
        from.isSmall() -> this[from]!! < 1
        else -> true
    }

    private fun Node.isSpecial() = (this == specialNode)
    private fun Node.markSpecial() {
        if (specialNode == null) {
            specialNode = this.also { println("Marking $it as special") }
        }
    }
    private fun resetSpecialNode() { specialNode = null }
}

fun Node.isStart(): Boolean = this == "start"
fun Node.isEnd(): Boolean = this == "end"
fun Node.isSmall(): Boolean = this.isAllLowercase()
fun String.isAllLowercase(): Boolean = this == this.lowercase()