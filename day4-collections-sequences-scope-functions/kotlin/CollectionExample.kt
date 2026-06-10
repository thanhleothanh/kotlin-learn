/**
 * Day 4 - Collections in Kotlin
 *
 * Kotlin collections are MUCH richer than Java's:
 *   - Read-only by default (listOf, mapOf)
 *   - Mutable separate (mutableListOf, mutableMapOf)
 *   - Rich stdlib functions (map, filter, reduce, etc.)
 *   - No Streams API needed!
 *   - Operator overloading for collections
 */

// ═══════════════════════════════════════════════════════════════
// 1. READ-ONLY vs MUTABLE COLLECTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Read-only collections (cannot be modified)
// List<T>, Set<T>, Map<K, V>

// Kotlin: Mutable collections (can be modified)
// MutableList<T>, MutableSet<T>, MutableMap<K, V>

// Java equivalent:
// List<T> — mutable!
// Collections.unmodifiableList(list) — immutable wrapper
// List.of() — immutable (Java 9+)

// ═══════════════════════════════════════════════════════════════
// 2. COLLECTION CREATION
// ═══════════════════════════════════════════════════════════════

fun demonstrateCreation() {
    // Read-only collections
    val list = listOf("Alice", "Bob", "Charlie")
    val set = setOf(1, 2, 3)
    val map = mapOf("Alice" to 25, "Bob" to 30)

    // Mutable collections
    val mutableList = mutableListOf("Alice", "Bob")
    val mutableSet = mutableSetOf(1, 2, 3)
    val mutableMap = mutableMapOf("Alice" to 25, "Bob" to 30)

    // Empty collections
    val emptyList = emptyList<String>()
    val emptySet = emptySet<Int>()
    val emptyMap = emptyMap<String, Int>()

    // Collection builder (returns read-only)
    val builtList = buildList {
        add("Alice")
        add("Bob")
        add("Charlie")
    }

    println("List: $list")
    println("Mutable: $mutableList")
    println("Built: $builtList")
}

// ═══════════════════════════════════════════════════════════════
// 3. BASIC OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateBasicOperations() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Filter
    val evens = numbers.filter { it % 2 == 0 }
    println("Evens: $evens")

    // Map
    val doubled = numbers.map { it * 2 }
    println("Doubled: $doubled")

    // Reduce
    val sum = numbers.reduce { acc, i -> acc + i }
    println("Sum: $sum")

    // Fold (with initial value)
    val product = numbers.fold(1) { acc, i -> acc * i }
    println("Product: $product")

    // ForEach
    numbers.forEach { print("$it ") }
    println()

    // Chaining
    val result = numbers
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(3)
    println("Chained: $result")
}

// ═══════════════════════════════════════════════════════════════
// 4. STRING OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateStringOperations() {
    val words = listOf("Hello", "World", "Kotlin", "Is", "Great")

    // Join to string
    val joined = words.joinToString(", ")
    println("Joined: $joined")

    // Join with prefix/suffix
    val html = words.joinToString(prefix = "<ul>", postfix = "</ul>", separator = "</li><li>") {
        "<li>$it</li>"
    }
    println("HTML: $html")

    // Grouping
    val grouped = words.groupBy { it.length }
    println("Grouped by length: $grouped")

    // Partition
    val (longWords, shortWords) = words.partition { it.length > 4 }
    println("Long: $longWords")
    println("Short: $shortWords")
}

// ═══════════════════════════════════════════════════════════════
// 5. MAP OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateMapOperations() {
    val map = mapOf("Alice" to 25, "Bob" to 30, "Charlie" to 35)

    // Filter
    val adults = map.filter { (_, age) -> age >= 30 }
    println("Adults: $adults")

    // Map values
    val names = map.map { (name, _) -> name }
    println("Names: $names")

    // Map values
    val doubledAges = map.mapValues { (_, age) -> age * 2 }
    println("Doubled ages: $doubledAges")

    // Get or default
    val aliceAge = map.getOrDefault("Alice", 0)
    val daveAge = map.getOrDefault("Dave", 0)
    println("Alice: $aliceAge, Dave: $daveAge")

    // Compute if absent
    val mutableMap = mutableMapOf("Alice" to 25)
    mutableMap.computeIfAbsent("Bob") { 30 }
    println("Computed: $mutableMap")
}

// ═══════════════════════════════════════════════════════════════
// 6. COLLECTION TRANSFORMATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateTransformations() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Chunk (split into groups)
    val chunks = numbers.chunked(3)
    println("Chunks: $chunks")

    // Window (sliding window)
    val windows = numbers.windowed(3)
    println("Windows: $windows")

    // Zip with index
    val names = listOf("Alice", "Bob", "Charlie")
    val indexed = names.zip(0..names.size)
    println("Indexed: $indexed")

    // FlatMap
    val nested = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    val flat = nested.flatMap { it }
    println("Flat: $flat")

    // Associate
    val words = listOf("Alice", "Bob", "Charlie")
    val associated = words.associate { it to it.length }
    println("Associated: $associated")
}

// ═══════════════════════════════════════════════════════════════
// 7. SORTING OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateSorting() {
    val numbers = listOf(5, 3, 8, 1, 9, 2, 7, 4, 6)
    val names = listOf("Charlie", "Alice", "Bob", "David")

    // Natural sort
    println("Sorted: ${numbers.sorted()}")
    println("Sorted desc: ${numbers.sortedDescending()}")

    // By property
    println("Names sorted: ${names.sorted()}")
    println("Names by length: ${names.sortedBy { it.length }}")

    // Shuffle
    println("Shuffled: ${numbers.shuffled()}")

    // Reverse
    println("Reversed: ${numbers.reversed()}")
}

// ═══════════════════════════════════════════════════════════════
// 8. AGGREGATE OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun demonstrateAggregates() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    println("Count: ${numbers.count()}")
    println("Sum: ${numbers.sum()}")
    println("Average: ${numbers.average()}")
    println("Min: ${numbers.minOrNull()}")
    println("Max: ${numbers.maxOrNull()}")
    println("First: ${numbers.first()}")
    println("Last: ${numbers.last()}")
    println("Any > 5: ${numbers.any { it > 5 }}")
    println("All > 0: ${numbers.all { it > 0 }}")
    println("None < 0: ${numbers.none { it < 0 }}")
    println("Contains 5: ${numbers.contains(5)}")
}

// ═══════════════════════════════════════════════════════════════
// 9. COLLECTION TYPES
// ═══════════════════════════════════════════════════════════════

fun demonstrateTypes() {
    // List (ordered, allows duplicates)
    val list = listOf(1, 2, 2, 3, 3, 3)
    println("List: $list")

    // Set (unordered, no duplicates)
    val set = setOf(1, 2, 2, 3, 3, 3)
    println("Set: $set")

    // MutableSet
    val mutableSet = mutableSetOf(1, 2, 3)
    mutableSet.add(4)
    mutableSet.add(2)  // Ignored (already exists)
    println("MutableSet: $mutableSet")

    // Map
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    println("Map: $map")

    // Pair creation
    val pair = "Alice" to 25  // Creates Pair("Alice", 25)
    println("Pair: $pair")
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {
    demonstrateCreation()
    demonstrateBasicOperations()
    demonstrateStringOperations()
    demonstrateMapOperations()
    demonstrateTransformations()
    demonstrateSorting()
    demonstrateAggregates()
    demonstrateTypes()

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // new ArrayList<>()                 mutableListOf()
    // List.of() (immutable)             listOf()
    // Collections.unmodifiableList()    listOf() (already immutable)
    // stream().filter().map().toList()  filter { }.map { }
    // stream().reduce()                 reduce { }
    // Collections.sort()                sorted()
    // map.getOrDefault()                map.getOrDefault()
    // Collections.frequency()           list.count { it == x }
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. listOf() = read-only, mutableListOf() = mutable
    // 2. mapOf() with "to" for pairs
    // 3. Chaining: filter{}.map{}.reduce{}
    // 4. it = implicit single parameter
    // 5. Last expression = return value
    // 6. No Streams API needed!
    // 7. buildList {} for complex construction
    //
    // ═══════════════════════════════════════════════════════════════
}
