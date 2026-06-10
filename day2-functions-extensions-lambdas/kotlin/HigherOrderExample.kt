/**
 * Day 2 - Higher-Order Functions & Lambdas in Kotlin
 *
 * Kotlin has MUCH cleaner lambda support than Java:
 *   - Concise syntax: { x -> x * 2 } or { it * 2 }
 *   - Function types: (Int) -> Int
 *   - Can return lambdas
 *   - Extension function lambdas: String.() -> Unit
 *   - Trailing lambda syntax
 *   - it keyword for single parameter
 */

// ═══════════════════════════════════════════════════════════════
// 1. FUNCTION TYPES (Kotlin's type-safe function references)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Function types are first-class citizens
// (Int) -> Int          — function taking Int, returning Int
// (String, Int) -> String — function taking String and Int, returning String
// () -> Unit            — function taking nothing, returning Unit (void)
// (Int) -> Unit         — function taking Int, returning Unit
// (String) -> Boolean?  — function taking String, returning nullable Boolean

// Java equivalent:
// Function<Integer, Integer>
// BiFunction<String, Integer, String>
// Consumer<Integer>
// Predicate<String>

// ═══════════════════════════════════════════════════════════════
// 2. HIGHER-ORDER FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Function as parameter
fun <T, R> mapList(list: List<T>, transform: (T) -> R): List<R> {
    return list.map(transform)  // Kotlin stdlib does this!
}

// Kotlin: Function with multiple parameters
fun <T> filterList(list: List<T>, predicate: (T) -> Boolean): List<T> {
    return list.filter(predicate)  // Kotlin stdlib!
}

// Kotlin: Function returning a function
fun getMultiplier(factor: Int): (Int) -> Int {
    return { number -> number * factor }  // Return lambda
}

// Java equivalent:
// public static Function<Integer, Integer> getMultiplier(int factor) {
//     return number -> number * factor;
// }

// Kotlin: Function with receiver (extension function lambda)
fun <T> T.applyTo(block: T.() -> Unit) {
    this.block()
}

// ═══════════════════════════════════════════════════════════════
// 3. LAMBDA SYNTAX
// ═══════════════════════════════════════════════════════════════

fun demonstrateLambdaSyntax() {

    val numbers = listOf(1, 2, 3, 4, 5)

    // Full syntax
    val doubled1 = numbers.map { x: Int -> x * 2 }

    // Inferred type (compiler knows it's Int)
    val doubled2 = numbers.map { x -> x * 2 }

    // "it" keyword for single parameter
    val doubled3 = numbers.map { it * 2 }

    // Method reference (same as Java)
    val doubled4 = numbers.map(Int::toDouble)

    // Multi-line lambda
    val processed = numbers.map { x ->
        val squared = x * x
        val doubled = squared * 2
        doubled  // Last expression is return value
    }

    println("Doubled: $doubled3")
    println("Processed: $processed")
}

// ═══════════════════════════════════════════════════════════════
// 4. TRAILING LAMBDA SYNTAX
// ═══════════════════════════════════════════════════════════════

// Kotlin: If last parameter is a lambda, move it outside parentheses
fun execute(name: String, action: () -> Unit) {
    println("Executing: $name")
    action()
}

// Java equivalent:
// public static void execute(String name, Runnable action) {
//     System.out.println("Executing: " + name);
//     action.run();
// }

fun demonstrateTrailingLambda() {

    // Without trailing lambda
    execute("Task 1") { println("  Doing work...") }

    // With trailing lambda (same thing)
    execute("Task 2") { println("  Doing work...") }

    // Kotlin: forEach with trailing lambda
    listOf(1, 2, 3).forEach { println("Number: $it") }

    // Java equivalent:
    // list.forEach(number -> System.out.println("Number: " + number));
}

// ═══════════════════════════════════════════════════════════════
// 5. IT KEYWORD (implicit parameter)
// ═══════════════════════════════════════════════════════════════

fun demonstrateItKeyword() {

    val names = listOf("Alice", "Bob", "Charlie")

    // "it" refers to the single parameter
    val lengths = names.map { it.length }
    val longNames = names.filter { it.length > 3 }
    val uppercased = names.map { it.uppercase() }

    println("Lengths: $lengths")
    println("Long names: $longNames")

    // Nested lambdas: use named parameters to avoid confusion
    names.filter { name ->
        name.length > 3
    }.map { name ->
        name.uppercase()
    }.forEach { name ->
        println("Long name: $name")
    }
}

// ═══════════════════════════════════════════════════════════════
// 6. EXTENSION FUNCTION LAMBDAS (Java CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Lambda with receiver
// String.() -> Unit — lambda that has "this" as String
fun buildString(block: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.block()  // "this" is StringBuilder inside block
    return sb.toString()
}

// Usage:
// buildString {
//     append("Hello")
//     append(" ")
//     append("World")
// }

// Java equivalent: StringBuilder builder = new StringBuilder(); builder.append(...); etc.

// ═══════════════════════════════════════════════════════════════
// 7. LAMBDA WITH RECEIVER vs REGULAR LAMBDA
// ═══════════════════════════════════════════════════════════════

// Regular lambda: (String) -> Unit
fun printLength(s: String) { println(s.length) }

// Extension lambda: String.() -> Unit
fun printLengthExt(s: String) { println(s.length) }

// Both can be called the same way, but extension lambda has "this" reference

// ═══════════════════════════════════════════════════════════════
// 8. CLOSURES (capturing variables)
// ═══════════════════════════════════════════════════════════════

fun demonstrateClosures() {

    // Kotlin: Can capture mutable variables (Java: only effectively final!)
    var counter = 0
    val increment = { counter++ }

    increment()
    increment()
    increment()
    println("Counter: $counter")  // 3

    // Java equivalent:
    // AtomicInteger counter = new AtomicInteger(0);
    // Runnable increment = () -> counter.incrementAndGet();
    // (Can't use plain int because it must be effectively final!)

    // Kotlin: Capturing in loops
    val functions = mutableListOf<() -> Int>()
    for (i in 1..5) {
        functions.add { i * 2 }  // Each lambda captures its own "i"
    }
    println("Results: ${functions.map { it() }}")  // [2, 4, 6, 8, 10]

    // Java: This would capture the LOOP variable (all get 10)!
    // List<Supplier<Integer>> functions = new ArrayList<>();
    // for (int i = 1; i <= 5; i++) {
    //     functions.add(() -> i * 2);  // Error! i must be effectively final
    // }
}

// ═══════════════════════════════════════════════════════════════
// 9. FUNCTION TYPES vs JAVA FUNCTIONAL INTERFACES
// ═══════════════════════════════════════════════════════════════

// Kotlin: Function types
fun applyInt(f: (Int) -> Int, x: Int): Int = f(x)

// Java: Functional interface
// public static int applyInt(Function<Integer, Integer> f, int x) { return f.apply(x); }

// Kotlin: Can pass lambda directly
fun useApplyInt() {
    val result = applyInt({ x -> x * 2 }, 5)
    val result2 = applyInt({ it * 2 }, 5)
    val result3 = applyInt(Int::times, 5)  // Method reference
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val names = listOf("Alice", "Bob", "Charlie", "David")

    // Basic higher-order functions
    val doubled = mapList(numbers) { it * 2 }
    val evens = filterList(numbers) { it % 2 == 0 }
    println("Doubled: $doubled")
    println("Evens: $evens")

    // Function returning function
    val triple = getMultiplier(3)
    println("Triple 5: ${triple(5)}")

    // Lambda syntax
    demonstrateLambdaSyntax()

    // Trailing lambda
    demonstrateTrailingLambda()

    // it keyword
    demonstrateItKeyword()

    // Build string with extension lambda
    val greeting = buildString {
        append("Hello")
        append(" ")
        append("World")
    }
    println("Built: $greeting")

    // Closures
    demonstrateClosures()

    // Kotlin stdlib functions with lambdas
    val result = numbers
        .filter { it % 2 == 0 }
        .map { it * it }
        .fold(0) { acc, i -> acc + i }
    println("Result: $result")  // 220 (4 + 16 + 36 + 64 + 100)

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // (x) -> x * 2                      { x -> x * 2 }
    // x -> x * 2                        { it * 2 }
    // Function<Integer, Integer>        (Int) -> Int
    // Runnable action                   () -> Unit
    // Supplier<T>                       () -> T
    // Consumer<T>                       (T) -> Unit
    // Predicate<T>                      (T) -> Boolean
    // list.stream().filter(...)         list.filter { ... }
    // AtomicInteger for mutable capture var counter (mutable!)
    // Collections.unmodifiableList()    listOf(...) (immutable)
    // Comparator.comparingInt(...)      compareBy { it.length }
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. Lambda syntax: { x -> x * 2 } or { it * 2 }
    // 2. Trailing lambda: move lambda outside parentheses
    // 3. "it" for single parameter
    // 4. Function types: (Int) -> Int
    // 5. Extension lambdas: String.() -> Unit
    // 6. Can capture mutable variables (Java: only effectively final!)
    // 7. Last expression is return value
    //
    // ═══════════════════════════════════════════════════════════════
}
