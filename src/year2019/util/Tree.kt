package year2019.util

class TreeNode<T>(var value: T) {
    var parent:TreeNode<T>? = null
    var children:MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(node:TreeNode<T>) {
        children.add(node)
        node.parent = this
    }

    fun calculateDepth():Int {
        return (parent?.calculateDepth() ?: -1) + 1
    }

    fun getChain(): List<TreeNode<T>> {
        val chain: MutableList<TreeNode<T>> = ArrayList()
        chain.add(this)
        chain.addAll(this.parent?.getChain() ?: emptyList())
        return chain
    }

    fun equals(other: TreeNode<T>): Boolean { //This is technically invalid, but the question ensures uniqueness of Node keys so it's safe enough
        return this.value == other.value
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}