package year2019.util

fun arrayLCM(vararg elements: Int): Long {
    var lcm: Long = 1
    var divisor = 2
    while (true) {
        var counter = 0
        var divisible = false
        for (i in elements.indices) {
            if (elements[i] == 0) {
                return 0
            } else if (elements[i] < 0) {
                elements[i] = elements[i] * -1
            }
            if (elements[i] == 1) {
                counter++
            }

            if (elements[i] % divisor == 0) {
                divisible = true
                elements[i] = elements[i] / divisor
            }
        }

        if (divisible) {
            lcm *= divisor
        } else {
            divisor++
        }

        if (counter == elements.size) {
            return lcm
        }
    }
}