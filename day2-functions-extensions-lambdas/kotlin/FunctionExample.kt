/**
 * Day 2 - Functions in Kotlin
 *
 * Kotlin functions have MANY advantages over Java:
 *   - Named arguments
 *   - Default parameter values
 *   - Single-expression functions
 *   - Extension functions
 *   - Higher-order functions
 *   - Infix functions
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC FUNCTION SYNTAX
// ═══════════════════════════════════════════════════════════════

// Kotlin: fun keyword, type after parameter, return type after parentheses
fun greet(name: String): String {
    return "Hello, $name!"
}

// Java equivalent:
// public static String greet(String name) {
//     return "Hello, " + name + "!";
// }

// ═══════════════════════════════════════════════════════════════
// 2. SINGLE-EXPRESSION FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: expression body (no return keyword needed!)
fun double(x: Int) = x * 2
fun isPositive(n: Int) = n > 0
fun add(a: Int, b: Int) = a + b

// Java equivalent:
// public static int double(int x) { return x * 2; }
// public static boolean isPositive(int n) { return n > 0; }

// ═══════════════════════════════════════════════════════════════
// 3. NAMED ARGUMENTS (Java doesn't have this!)
// ═══════════════════════════════════════════════════════════════

data class Person(val name: String, val age: Int, val email: String)

// Java: createPerson("Alice", 25, "alice@email.com")  — must pass in order
// Kotlin: can name arguments in any order!

// ═══════════════════════════════════════════════════════════════
// 4. DEFAULT PARAMETER VALUES (Java doesn't have this!)
// ═══════════════════════════════════════════════════════════════

fun formatString(text: String, mode: String = "UPPER"): String {
    return when (mode) {
        "UPPER" -> text.uppercase()
        "LOWER" -> text.lowercase()
        else -> text
    }
}

// Java equivalent:
// public static String formatString(String text) {
//     return formatString(text, "UPPER");  // Overload needed!
// }
// public static String formatString(String text, String mode) { ... }

// More complex example with multiple defaults
fun createUrl(
    host: String = "localhost",
    port: Int = 8080,
    path: String = "/",
    secure: Boolean = false
): String {
    val protocol = if (secure) "https" else "http"
    return "$protocol://$host:$port$path"
}

// ═══════════════════════════════════════════════════════════════
// 5. VARARG (variable arguments)
// ═══════════════════════════════════════════════════════════════

fun sum(vararg numbers: Int): Int {
    return numbers.sum()
}

// Can also use spread operator *
fun printNumbers(vararg numbers: Int) {
    println(numbers.joinToString(", "))
}

// ═══════════════════════════════════════════════════════════════
// 6. SINGLE-RETURN vs MULTIPLE RETURNS
// ═══════════════════════════════════════════════════════════════

// Java: Can only return one value
// Kotlin: Can return Pair or use data class for multiple returns

fun divide(a: Int, b: Int): Pair<Int, Int> {
    return Pair(a / b, a % b)  // quotient and remainder
}

// Better: use data class for named returns
data class DivideResult(val quotient: Int, val remainder: Int)

fun divideBetter(a: Int, b: Int): DivideResult {
    return DivideResult(a / b, a % b)
}

// ═══════════════════════════════════════════════════════════════
// 7. LOCAL FUNCTIONS (Java can't do this!)
// ═══════════════════════════════════════════════════════════════

fun processOrder(orderId: String) {
    // Local function — defined inside another function
    fun validate(id: String): Boolean {
        return id.isNotBlank() && id.startsWith("ORD-")
    }

    fun calculateTotal(items: List<Double>): Double {
        return items.sum()
    }

    if (validate(orderId)) {
        val items = listOf(99.99, 49.99, 29.99)
        println("Order $orderId total: $${calculateTotal(items)}")
    } else {
        println("Invalid order: $orderId")
    }
}

// Java equivalent: Extract to separate methods (can't define inside method)

// ═══════════════════════════════════════════════════════════════
// 8. FUNCTION OVERLOADING (same as Java)
// ═══════════════════════════════════════════════════════════════

fun display(text: String) = println("String: $text")
fun display(number: Int) = println("Int: $number")
fun display(list: List<*>) = println("List: ${list.size} items")

// But Kotlin prefers default args over overloading!

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic function
    println(greet("Alice"))

    // Single-expression functions
    println("Double 5: ${double(5)}")
    println("Is positive? ${isPositive(3)}")

    // Named arguments — can skip defaults!
    println(formatString("hello"))                    // Uses default "UPPER"
    println(formatString("hello", mode = "LOWER"))   // Named arg, skip nothing
    println(formatString("hello", mode = "title"))   // Named arg

    // Named arguments in any order
    val url1 = createUrl()                                        // All defaults
    val url2 = createUrl(port = 9090, path = "/api")             // Custom port and path
    val url3 = createUrl(secure = true, host = "example.com")    // Custom host, secure
    println("URL 1: $url1")
    println("URL 2: $url2")
    println("URL 3: $url3")

    // Varargs
    println("Sum: ${sum(1, 2, 3, 4, 5)}")
    printNumbers(1, 2, 3)

    // Multiple returns
    val (quotient, remainder) = divide(17, 5)
    println("17 / 5 = $quotient remainder $remainder")

    val result = divideBetter(17, 5)
    println("17 / 5 = ${result.quotient} remainder ${result.remainder}")

    // Local functions
    processOrder("ORD-001")
    processOrder("INVALID")

    // Overloading
    display("Hello")
    display(42)
    display(listOf(1, 2, 3))

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // public int add(int a, int b)      fun add(a: Int, b: Int): Int
    // public int add(int a, int b,      fun add(a: Int, b: Int,
    //               int c)                      c: Int = 0): Int
    // add(1, 2)                         add(1, 2)
    // add(1, 2, 3)                      add(1, 2, 3)
    // add(1, 2) + default overload     add(1, 2)  // c defaults to 0!
    // No named args                     add(a = 1, b = 2)
    // No local functions                fun local() { ... }
    // Return one value                  return Pair(a, b) or data class
    //
    // ═══════════════════════════════════════════════════════════════
}
