/**
 * Day 2 - Infix Functions in Kotlin
 *
 * Kotlin's infix functions let you call functions WITHOUT dots and parentheses.
 * This makes code more readable and enables DSL-like syntax.
 *
 * Syntax: infix fun function(param) = value
 * Call:   a function b  (instead of a.function(b))
 *
 * Rules:
 *   - Must be member function or extension function
 *   - Must have exactly one parameter
 *   - Parameter must NOT have default value
 *   - Must NOT be vararg
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC INFIX FUNCTION
// ═══════════════════════════════════════════════════════════════

// Regular function
fun add(a: Int, b: Int): Int = a + b

// Infix function — same thing, but can call without dots and parens
infix fun Int.addInfix(b: Int): Int = this + b

// Java equivalent:
// public static int add(int a, int b) { return a + b; }
// (No infix equivalent in Java!)

// ═══════════════════════════════════════════════════════════════
// 2. INFIX WITH PAIR (the "to" function)
// ═══════════════════════════════════════════════════════════════

// Kotlin's "to" is an infix function!
// fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

fun demonstrateToFunction() {

    // Regular map creation
    val map1 = mapOf(Pair("Alice", 25), Pair("Bob", 30))

    // With "to" infix function (cleaner!)
    val map2 = mapOf("Alice" to 25, "Bob" to 30)

    // Both are the same!
    println("Map: $map2")

    // Java equivalent:
    // Map<String, Integer> map = Map.of("Alice", 25, "Bob", 30);
}

// ═══════════════════════════════════════════════════════════════
// 3. BITWISE OPERATIONS (infix)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Bitwise operations as infix functions
fun demonstrateBitwise() {
    val a = 5      // 101 in binary
    val b = 3      // 011 in binary

    val and = a and b    // 001 = 1
    val or = a or b      // 111 = 7
    val xor = a xor b    // 110 = 6
    val shl = a shl 1    // 1010 = 10 (shift left)
    val shr = a shr 1    // 10 = 2 (shift right)

    println("a and b = $and")    // 1
    println("a or b = $or")      // 7
    println("a xor b = $xor")    // 6
    println("a shl 1 = $shl")    // 10
    println("a shr 1 = $shr")    // 2

    // Java equivalent:
    // int and = a & b;
    // int or = a | b;
    // int xor = a ^ b;
    // int shl = a << 1;
    // int shr = a >> 1;
}

// ═══════════════════════════════════════════════════════════════
// 4. CUSTOM INFIX FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Create your own infix functions
infix fun String.repeat(times: Int): String {
    return this.repeat(times)
}

infix fun Int.power(exponent: Int): Int {
    var result = 1
    repeat(exponent) { result *= this }
    return result
}

// Kotlin already has "repeat" function, so let's use a different name
infix fun String.repeatNTimes(times: Int): String {
    return this.repeat(times)
}

// ═══════════════════════════════════════════════════════════════
// 5. INFIX WITH COLLECTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: "to" for creating pairs (used in maps)
fun demonstrateCollectionInfix() {

    // Creating maps with "to"
    val scores = mapOf(
        "Alice" to 95,
        "Bob" to 87,
        "Charlie" to 92
    )

    println("Scores: $scores")

    // Chaining with "to"
    val config = mapOf(
        "host" to "localhost",
        "port" to "8080",
        "debug" to "true"
    )

    println("Config: $config")
}

// ═══════════════════════════════════════════════════════════════
// 6. INFIX FUNCTION RULES
// ═══════════════════════════════════════════════════════════════

// Rules for infix functions:
// 1. Must be member function or extension function
// 2. Must have exactly ONE parameter
// 3. Parameter must NOT have default value
// 4. Parameter must NOT be vararg
// 5. Must be declared with "infix" keyword

// Examples:
class Calculator {
    // ✅ Valid infix
    infix fun add(b: Int): Int = this.hashCode() + b  // Example using hashCode

    // ❌ Invalid: has default parameter
    // infix fun addDefault(b: Int = 0): Int = ...

    // ❌ Invalid: vararg
    // infix fun addVararg(vararg b: Int): Int = ...
}

// Extension infix function
infix fun Int.isGreaterThan(other: Int): Boolean = this > other
infix fun Int.isLessThan(other: Int): Boolean = this < other

// ═══════════════════════════════════════════════════════════════
// 7. PRACTICAL EXAMPLES
// ═══════════════════════════════════════════════════════════════

// Example 1: Range checking
infix fun Int.isInRange(range: IntRange): Boolean = this in range

// Example 2: String multiplication
infix fun String.times(n: Int): String = this.repeat(n)

// Example 3: Pair creation (already in stdlib as "to")
// infix fun <A, B> A.to(other: B): Pair<A, B> = Pair(this, other)

// Example 4: Comparison
infix fun String.isLongerThan(other: String): Boolean = this.length > other.length

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic infix
    val sum1 = add(3, 5)           // Regular call
    val sum2 = 3 addInfix 5        // Infix call
    println("Sum1: $sum1, Sum2: $sum2")

    // Bitwise operations
    demonstrateBitwise()

    // to function
    demonstrateToFunction()

    // Custom infix
    val repeated = "Hello" repeatNTimes 3
    println("Repeated: $repeated")

    // Comparison infix
    val a = 10
    val b = 5
    println("$a is greater than $b? ${a isGreaterThan b}")
    println("$a is less than $b? ${a isLessThan b}")

    // Range checking
    val score = 85
    println("Score $score in range 0-100? ${score isInRange 0..100}")

    // String operations
    val str = "Kotlin"
    println("'$str' * 3 = ${str times 3}")
    println("'Hello' is longer than 'Hi'? ${"Hello" isLongerThan "Hi"}")

    // Collection infix
    demonstrateCollectionInfix()

    // Practical example: Builder pattern with infix
    html {
        head {
            title { "My Page" }
        }
        body {
            p { "Hello, World!" }
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // map.get("key")                    map["key"] or map get "key"
    // list.get(0)                       list[0] or list get 0
    // a << 2                            a shl 2
    // a & b                             a and b
    // Map.of("k", "v")                  mapOf("k" to "v")
    // new Builder().x().y()             builder x y
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. infix fun — one parameter, no default, no vararg
    // 2. a function b — no dots, no parentheses
    // 3. "to" creates Pair — used for map creation
    // 4. Standard library uses infix: and, or, xor, shl, shr
    // 5. Great for DSLs and readable code
    //
    // ═══════════════════════════════════════════════════════════════
}

// ═══════════════════════════════════════════════════════════════
// BONUS: HTML DSL EXAMPLE (infix functions in action)
// ═══════════════════════════════════════════════════════════════

open class HTMLNode {
    protected val children = mutableListOf<String>()
    override fun toString(): String = children.joinToString("\n")
}

class HTML : HTMLNode() {
    fun head(block: Head.() -> Unit) { children.add(Head().apply(block).toString()) }
    fun body(block: Body.() -> Unit) { children.add(Body().apply(block).toString()) }
}

class Head : HTMLNode() {
    fun title(block: () -> String) { children.add("  <title>${block()}</title>") }
}

class Body : HTMLNode() {
    fun p(block: () -> String) { children.add("  <p>${block()}</p>") }
}

fun html(block: HTML.() -> Unit): HTML = HTML().apply(block)
