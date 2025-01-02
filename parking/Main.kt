package parking

data class Car(val licensePlate: String, val color: String) {
    override fun toString(): String {
        return "$licensePlate $color"
    }
}

class ParkingLot(private val size: Int) {
    private val slots: Array<Car?> = arrayOfNulls(size)

    fun park(carName: String) {
        var parkingSpot = -1

        for (i in slots.indices) {
            if (slots[i] == null) {
                val carSplit = carName.split(" ")
                slots[i] = Car(carSplit[0], carSplit[1])
                parkingSpot = i
                break
            }
        }
        if (parkingSpot == -1) {
            println("Sorry, the parking lot is full.")
            return
        }


        println("${slots[parkingSpot]?.color} car parked in spot ${parkingSpot + 1}.")
    }

    fun leave(index: String) {
        val target = index.toIntOrNull()


        if (target == null || (target - 1) !in slots.indices) {
            println("Invalid Spot.")
            return
        }

        val actualTarget = target - 1

        if (slots[actualTarget] == null) {
            println("There is no car in spot $target.")
        } else {
            println("Spot $target is free.")
            slots[actualTarget] = null
        }
    }

    fun printStatus() {
        var carFound = false
        for (i in slots.indices) {
            if (slots[i] != null) {
                carFound = true
                println("${i + 1} ${slots[i]}")
            }
        }
        if (!carFound) {
            println("Parking lot is empty.")
        }

    }

    fun printRegistrationByColor(targetColor: String) {
        val registrationList = mutableListOf<String>()

        for (car in slots) {
            if (car != null) {
                if (car.color.equals(targetColor, ignoreCase = true)) {
                    registrationList.add(car.licensePlate)
                }
            }
        }
        if (registrationList.isEmpty()) {
            println("No cars with color $targetColor were found.")
        } else {
            println(registrationList.joinToString(", "))
        }
    }

    fun printSpotByColor(targetColor: String) {
        val spotList = mutableListOf<Int>()

        for ((index, car) in slots.withIndex()) {
            if (car != null) {
                if (car.color.equals(targetColor, ignoreCase = true)) {
                    spotList.add((index + 1))
                }
            }
        }
        if (spotList.isEmpty()) {
            println("No cars with color $targetColor were found.")
        } else {
            println(spotList.joinToString(", "))
        }
    }

    fun printSpotByRegistration(targetRegistration: String) {
        val spotList = mutableListOf<Int>()

        for ((index, car) in slots.withIndex()) {
            if (car != null) {
                if (car.licensePlate.equals(targetRegistration, ignoreCase = true)) {
                    spotList.add((index + 1))
                }
            }
        }
        if (spotList.isEmpty()) {
            println("No cars with registration number $targetRegistration were found.")
        } else {
            println(spotList.joinToString(", "))
        }
    }



}


fun createParkingLot(size: Int): ParkingLot {
    println("Created a parking lot with $size spots.")
    return ParkingLot(size)
}

fun main() {
    var parkingLot = ParkingLot(0)

    while (true) {
        val initialInput = readln().split(" ")

        when {
            initialInput.contains("park")
                    || initialInput.contains("spot_by_color")
                    || initialInput.contains("reg_by_color")
                    || initialInput.contains("spot_by_reg")
                    || initialInput.contains("leave")
                    || initialInput.contains("status") -> println("Sorry, a parking lot has not been created.")

            initialInput[0] == "exit" -> return
            initialInput.contains("create") -> {
                parkingLot = createParkingLot(initialInput[1].toIntOrNull() ?: 1)
                break
            }

            else -> {
                println("Invalid input")
                continue
            }
        }
    }
    while (true) {
        val input = readln().split(" ")

        when {
            input[0] == "park" -> parkingLot.park(input[1] + " " + input[2])
            input[0] == "leave" -> parkingLot.leave(input[1])
            input[0] == "create" -> parkingLot = createParkingLot(input[1].toIntOrNull() ?: 1)
            input[0] == "reg_by_color" -> parkingLot.printRegistrationByColor(input[1])
            input[0] == "spot_by_color" -> parkingLot.printSpotByColor(input[1])
            input[0] == "spot_by_reg" -> parkingLot.printSpotByRegistration(input[1])
            input[0] == "status" -> parkingLot.printStatus()
            input[0] == "exit" -> break
            else -> println("Invalid input")
        }
    }
}

