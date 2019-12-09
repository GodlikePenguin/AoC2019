package year2019.util

class ZeroDefaultList(var inner: LongArray) {
    val size = inner.size

    operator fun get(index: Int): Long {
        try {
            return inner[index]
        } catch (e: IndexOutOfBoundsException) {}
        return 0
    }

    operator fun get(index: Long): Long {
        return get(index.toInt())
    }

    operator fun set(index: Int, value: Long) {
        if (index > inner.size) {
            val newInner = LongArray(index * 2)
            inner = inner.copyInto(newInner)
        }
        inner[index] = value
    }

    operator fun set(index: Long, value: Long) {
        return set(index.toInt(), value)
    }
}
