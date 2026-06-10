/**
 * Day 4 - Destructuring in Kotlin
 *
 * Kotlin's destructuring is powerful:
 *   - Auto-generated componentN() functions from data classes
 *   - Destructuring declarations: val (x, y) = point
 *   - Destructuring in lambdas: .map { (name, age) -> ... }
 *   - Maps and collections destructuring
 *   - Custom component functions
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC DESTRUCTURING
// ═══════════════════════════════════════════════════════════════

data class Point(val x: Int, val y: Int)
data class Person(val name: String, val age: Int, val email: String)

fun basicDestructuring() {
    // Destructuring data class
    val point = Point(3, 4)
    val (x, y) = point  // Calls component1() and component2()
    println("Point: x=$x, y=$y")

    // Destructuring with more fields
    val person = Person("Alice", 25, "alice@email.com")
    val (name, age, email) = person
    println("Person: $name, $age, $email")

    // Java equivalent:
    // var (x, y) = point;  // Java 16+ records only
    // String name = person.name();
    // int age = person.age();
}

// ═══════════════════════════════════════════════════════════════
// 2. DESTRUCTURING WITH UNDERSCORE (ignore fields)
// ═══════════════════════════════════════════════════════════════

fun destructuringWithUnderscore() {
    val person = Person("Bob", 30, "bob@email.com")

    // Ignore some fields with underscore
    val (name, _, email) = person  // Ignore age
    println("Name: $name, Email: $email")

    // Ignore first field
    val (_, age, _) = person
    println("Age: $age")

    // Java equivalent: No equivalent in Java!
}

// ═══════════════════════════════════════════════════════════════
// 3. DESTRUCTURING IN LAMBDAS
// ═══════════════════════════════════════════════════════════════

fun destructuringInLambdas() {
    val persons = listOf(
        Person("Alice", 25, "alice@email.com"),
        Person("Bob", 30, "bob@email.com"),
        Person("Charlie", 35, "charlie@email.com")
    )

    // Destructuring in lambda parameters
    val names = persons.map { (name, _, _) -> name }
    println("Names: $names")

    // Destructuring with filter
    val adults = persons.filter { (_, age, _) -> age >= 30 }
        .map { (name, age, _) -> "$name ($age)" }
    println("Adults: $adults")

    // Destructuring in forEach
    persons.forEach { (name, age, email) ->
        println("$name is $age years old, email: $email")
    }

    // Java equivalent:
    // List<String> names = persons.stream()
    //     .map(PersonRecord::name)
    //     .toList();
}

// ═══════════════════════════════════════════════════════════════
// 4. DESTRUCTURING MAPS
// ═══════════════════════════════════════════════════════════════

fun destructuringMaps() {
    val map = mapOf("Alice" to 25, "Bob" to 30, "Charlie" to 35)

    // Destructuring map entries
    for ((name, age) in map) {
        println("$name is $age years old")
    }

    // Destructuring in lambdas
    val descriptions = map.map { (name, age) -> "$name: $age years" }
    println("Descriptions: $descriptions")

    // Destructuring with filter
    val adults = map.filter { (_, age) -> age >= 30 }
        .map { (name, age) -> Pair(name.uppercase(), age) }
    println("Adults: $adults")

    // Java equivalent:
    // for (var entry : map.entrySet()) {
    //     String name = entry.getKey();
    //     int age = entry.getValue();
    // }
}

// ═══════════════════════════════════════════════════════════════
// 5. DESTRUCTURING COLLECTIONS
// ═══════════════════════════════════════════════════════════════

fun destructuringCollections() {
    // withIndex() for index + value
    val fruits = listOf("Apple", "Banana", "Cherry")
    for ((index, fruit) in fruits.withIndex()) {
        println("$index: $fruit")
    }

    // Destructuring in mapIndexed
    val indexed = fruits.mapIndexed { index, fruit ->
        "$index: $fruit"
    }
    println("Indexed: $indexed")

    // partition with destructuring
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val (evens, odds) = numbers.partition { it % 2 == 0 }
    println("Evens: $evens")
    println("Odds: $odds")

    // zip with destructuring
    val names = listOf("Alice", "Bob", "Charlie")
    val ages = listOf(25, 30, 35)
    val combined = names.zip(ages)
    for ((name, age) in combined) {
        println("$name is $age years old")
    }
}

// ═══════════════════════════════════════════════════════════════
// 6. CUSTOM COMPONENT FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Can define custom component functions!
class Pair<A, B>(val first: A, val second: B) {
    operator fun component1(): A = first
    operator fun component2(): B = second
}

// Or use the stdlib Pair class (already has component1/2)

// Custom class with component functions
class User(val name: String, val age: Int, val email: String) {
    operator fun component1(): String = name
    operator fun component2(): Int = age
    operator fun component3(): String = email
}

// ═══════════════════════════════════════════════════════════════
// 7. DESTRUCTURING IN LET
// ═══════════════════════════════════════════════════════════════

fun destructuringInLet() {
    val person: Person? = Person("Dave", 28, "dave@email.com")

    // Destructuring in let (nullable)
    person?.let { (name, age, email) ->
        println("Name: $name, Age: $age, Email: $email")
    }

    // Chained let with destructuring
    val result = person?.let { (name, age, _) ->
        "$name is $age"
    } ?: "No person"
    println(result)
}

// ═══════════════════════════════════════════════════════════════
// 8. PRACTICAL EXAMPLES
// ═══════════════════════════════════════════════════════════════

fun practicalExamples() {
    // Example 1: Parse key-value pairs
    val pairs = listOf("name=Alice", "age=25", "email=alice@email.com")
    val map = pairs.associate {
        val (key, value) = it.split("=")
        key to value
    }
    println("Parsed: $map")

    // Example 2: Process CSV
    val csvLines = listOf(
        "name,age,email",
        "Alice,25,alice@email.com",
        "Bob,30,bob@email.com"
    )
    val records = csvLines.drop(1).map { line ->
        val (name, age, email) = line.split(",")
        Person(name, age.toInt(), email)
    }
    println("Records: $records")

    // Example 3: Swap values using destructuring
    var a = 1
    var b = 2
    val (x, y) = Pair(b, a)  // Swap!
    a = x
    b = y
    println("After swap: a=$a, b=$b")

    // Example 4: Function returning multiple values
    fun getMinMax(numbers: List<Int>): Pair<Int, Int> {
        return Pair(numbers.minOrNull() ?: 0, numbers.maxOrNull() ?: 0)
    }

    val (min, max) = getMinMax(listOf(3, 1, 4, 1, 5, 9, 2, 6))
    println("Min: $min, Max: $max")
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {
    basicDestructuring()
    destructuringWithUnderscore()
    destructuringInLambdas()
    destructuringMaps()
    destructuringCollections()
    destructuringInLet()
    practicalExamples()

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // person.name()                     val (name, _, _) = person
    // entry.getKey() / entry.getValue() for ((k, v) in map)
    // for (int i = 0; i < list.size())  for ((i, v) in list.withIndex())
    // var (x, y) = record (Java 16+)    val (x, y) = point
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. component1(), component2(), ... for destructuring
    // 2. data class auto-generates componentN()
    // 3. Use _ to ignore fields
    // 4. Destructuring works in lambdas
    // 5. Maps and collections support destructuring
    // 6. Can define custom component functions
    // 7. Destructuring in let for nullable objects
    //
    // ═══════════════════════════════════════════════════════════════
}
