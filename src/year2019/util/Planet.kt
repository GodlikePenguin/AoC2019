package year2019.util

data class Planet(var position: Vector, var velocity: Vector) {
    constructor(p: Pair<Vector, Vector>) : this(p.first, p.second)

    fun update() {
        position = position + velocity
    }

    override fun toString(): String {
        return "$position$velocity"
    }

    override fun equals(other: Any?): Boolean {
        if (other is Planet) {
            return (position == other.position && velocity == other.velocity)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + velocity.hashCode()
        return result
    }
}