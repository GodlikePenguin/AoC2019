package year2019.util

data class Planet(var position: Vector, var velocity: Vector) {
    constructor(p: Pair<Vector, Vector>) : this(p.first, p.second)

    fun update() {
        position = position + velocity
    }

    fun hasSameX(other: Planet): Boolean {
        return other.position.x == this.position.x && other.velocity.x == this.velocity.x
    }

    fun hasSameY(other: Planet): Boolean {
        return other.position.y == this.position.y && other.velocity.y == this.velocity.y
    }

    fun hasSameZ(other: Planet): Boolean {
        return other.position.z == this.position.z && other.velocity.z == this.velocity.z
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