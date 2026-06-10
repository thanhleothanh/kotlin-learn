/**
 * Day 4 - Sequences (Lazy Evaluation) in Kotlin
 *
 * Kotlin sequences are MUCH more efficient than eager collections:
 *   - Process elements one by one (lazy)
 *   - No intermediate collections created
 *   - Perfect for large datasets
 *   - Can be parallelized with concurrent()
 */

// ═══════════════════════════════════════════════════════════════
// 1. EAGER vs LAZY
// ═══════════════════════════════════════════════════════════════

fun eagerVsLazy() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // Eager: each operation creates a NEW list
    println("=== EAGER ===")
    val eagerResult = numbers
        .filter {
            println("Filter: $it")
            it % 2 == 0
        }
        .map {
            println("Map: $it")
            it * it
        }
    println("Result: $eagerResult")

    // Lazy: processes elements one by one
    println("\n=== LAZY ===")
    val lazyResult = numbers.asSequence()
        .filter {
            println("Filter: $it")
            it % 2 == 0
        }
        .map {
            println("Map: $it")
            it * it
        }
        .toList()
    println("Result: $lazyResult")
}

// ═══════════════════════════════════════════════════════════════
// 2. SEQUENCE OPERATIONS
// ═══════════════════════════════════════════════════════════════

fun sequenceOperations() {
    val numbers = (1..100).toList()

    // Lazy operations
    val result = numbers.asSequence()
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(5)
        .toList()

    println("First 5 even squares: $result")

    // Chaining with sequence
    val sum = numbers.asSequence()
        .filter { it > 50 }
        .map { it * 2 }
        .take(10)
        .sum()

    println("Sum of first 10 doubled numbers > 50: $sum")
}

// ═══════════════════════════════════════════════════════════════
// 3. GENERATE SEQUENCE (infinite sequences)
// ═══════════════════════════════════════════════════════════════

fun infiniteSequences() {
    // Generate infinite sequence
    val naturals = generateSequence(1) { it + 1 }
    println("First 10 naturals: ${naturals.take(10).toList()}")

    // Fibonacci sequence
    val fibonacci = generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }
        .map { it.first }
    println("First 10 Fibonacci: ${fibonacci.take(10).toList()}")

    // Generate with seed
    val powers = generateSequence(1) { it * 2 }
    println("First 10 powers of 2: ${powers.take(10).toList()}")

    // Sequence from function
    val random = generateSequence { (Math.random() * 100).toInt() }
    println("First 5 random: ${random.take(5).toList()}")
}

// ═══════════════════════════════════════════════════════════════
// 4. SEQUENCE vs COLLECTION (performance)
// ═══════════════════════════════════════════════════════════════

fun performanceComparison() {
    val largeList = (1..1_000_000).toList()

    // Eager: creates intermediate collections
    val startEager = System.currentTimeMillis()
    val eagerResult = largeList
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(10)
    val eagerTime = System.currentTimeMillis() - startEager

    // Lazy: processes one element at a time
    val startLazy = System.currentTimeMillis()
    val lazyResult = largeList.asSequence()
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(10)
        .toList()
    val lazyTime = System.currentTimeMillis() - startLazy

    println("Eager: ${eagerTime}ms, Result: $eagerResult")
    println("Lazy: ${lazyTime}ms, Result: $lazyResult")
    println("Lazy is ${eagerTime / lazyTime}x faster!")
}

// ═══════════════════════════════════════════════════════════════
// 5. SEQUENCE BUILDER
// ═══════════════════════════════════════════════════════════════

fun sequenceBuilder() {
    // Custom sequence using sequence builder
    val customSequence = sequence {
        var i = 0
        while (true) {
            yield(i++)  // Yield one element at a time
            if (i > 10) return@sequence  // Stop condition
        }
    }

    println("Custom sequence: ${customSequence.toList()}")

    // Sequence with yieldAll
    val nested = sequence {
        yieldAll(1..3)
        yieldAll(listOf("a", "b", "c"))
        yieldAll(mapOf("x" to 1, "y" to 2))
    }

    println("Nested sequence: ${nested.toList()}")
}

// ═══════════════════════════════════════════════════════════════
// 6. PRACTICAL EXAMPLES
// ═══════════════════════════════════════════════════════════════

fun practicalExamples() {
    // Example 1: Read large file lazily
    // val lines = File("large.txt").readLines().asSequence()
    //     .filter { it.isNotBlank() }
    //     .map { it.trim() }
    //     .take(100)

    // Example 2: Process API responses lazily
    val apiResponses = sequence {
        var page = 1
        while (true) {
            // Simulate API call
            val response = "Page $page data"
            yield(response)
            page++
            if (page > 10) return@sequence
        }
    }

    println("API responses: ${apiResponses.take(3).toList()}")

    // Example 3: Infinite random numbers
    val randomNumbers = generateSequence { (Math.random() * 100).toInt() }
        .distinct()
        .take(5)

    println("Unique random: ${randomNumbers.toList()}")

    // Example 4: Fibonacci until 100
    val fibUntil100 = generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }
        .map { it.first }
        .takeWhile { it <= 100 }
        .toList()

    println("Fibonacci until 100: $fibUntil100")
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {
    eagerVsLazy()
    sequenceOperations()
    infiniteSequences()
    performanceComparison()
    sequenceBuilder()
    practicalExamples()

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // list.stream()                     list.asSequence()
    // Stream.iterate()                  generateSequence()
    // stream().limit(n)                 .take(n)
    // stream().parallel()               .concurrent()
    // stream().toList()                 .toList()
    // stream().reduce()                 .reduce()
    // Collections.unmodifiableList()    listOf() (immutable)
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. asSequence() for lazy evaluation
    // 2. generateSequence() for infinite sequences
    // 3. take(n) to limit elements
    // 4. toList() to materialize sequence
    // 5. Sequences are more efficient for large datasets
    // 6. Use when you have many intermediate operations
    // 7. Don't use for small collections (overhead)
    //
    // ═══════════════════════════════════════════════════════════════
}
