/**
 * Day 1 - Type Inference in Kotlin
 *
 * Kotlin's type inference is MUCH more powerful than Java's:
 *   - Works for local variables, properties (class fields), and function return types
 *   - Smart casts after null checks and type checks
 *   - Generic type inference works everywhere
 */

// ═══════════════════════════════════════════════════════════════
// 1. TOP-LEVEL TYPE INFERENCE (Java CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Top-level properties get type inference (Java: explicit type required)
val name = "Kotlin"                    // String
val count = 0                          // Int
val decimal = 3.14                     // Double
val isValid = true                     // Boolean
val list = listOf(1, 2, 3)            // List<Int>
val map = mapOf("a" to 1, "b" to 2)  // Map<String, Int>

// ═══════════════════════════════════════════════════════════════
// 2. CLASS PROPERTY TYPE INFERENCE (Java CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Class properties get type inference
class User {
    val name = "Alice"       // Inferred as String (Java: private final String name = "Alice")
    var age = 25             // Inferred as Int (Java: private int age = 25)
    val scores = listOf(90, 85, 95)  // Inferred as List<Int>
}

// Java equivalent:
// class User {
//     private final String name = "Alice";   // Must specify String
//     private int age = 25;                  // Must specify int
//     private final List<Integer> scores = List.of(90, 85, 95);  // Must specify List<Integer>
// }

// ═══════════════════════════════════════════════════════════════
// 3. FUNCTION RETURN TYPE INFERENCE (Java CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

// Kotlin: Return type inferred from expression body
fun add(a: Int, b: Int) = a + b   // Return type inferred as Int
fun greet(name: String) = "Hello, $name"  // Return type inferred as String
fun isPositive(n: Int) = n > 0     // Return type inferred as Boolean

// Java equivalent:
// public static int add(int a, int b) { return a + b; }  // Must say "int"
// public static String greet(String name) { return "Hello, " + name; }  // Must say "String"

// ═══════════════════════════════════════════════════════════════
// 4. SMART CASTS (Kotlin automatic after type checks)
// ═══════════════════════════════════════════════════════════════

fun processValue(value: Any): String {
    // Java 16+: if (value instanceof String s) { ... }
    // Kotlin: same but works with nullable, and more powerful

    return when (value) {
        is String -> "String of length ${value.length}"   // Smart cast: value is String
        is Int -> "Int: ${value * 2}"                     // Smart cast: value is Int
        is List<*> -> "List of size ${value.size}"        // Smart cast: value is List
        else -> "Unknown type"
    }
}

// Java equivalent:
// public static String processValue(Object value) {
//     if (value instanceof String s) {
//         return "String of length " + s.length();
//     } else if (value instanceof Integer i) {
//         return "Int: " + (i * 2);
//     } else if (value instanceof List<?> l) {
//         return "List of size " + l.size();
//     }
//     return "Unknown type";
// }

// ═══════════════════════════════════════════════════════════════
// 5. SMART CASTS after null checks
// ═══════════════════════════════════════════════════════════════

fun printLength(s: String?) {
    if (s != null) {
        // Smart cast: s is now String (not String?)
        println("Length: ${s.length}")  // No !! needed!
    }
}

// Java equivalent:
// public static void printLength(String s) {
//     if (s != null) {
//         System.out.println("Length: " + s.length());
//     }
// }

// ═══════════════════════════════════════════════════════════════
// 6. GENERIC TYPE INFERENCE
// ═══════════════════════════════════════════════════════════════

// Kotlin: Generic type inferred from arguments
val intList = listOf(1, 2, 3)          // List<Int>
val stringList = listOf("a", "b")      // List<String>
val mixedList = listOf(1, "two", 3.0)  // List<Any>

// Kotlin: Map type inference
val scores = mapOf("Alice" to 95, "Bob" to 87)  // Map<String, Int>

// Java: Also has diamond <> inference, but only for constructors
// var list = new ArrayList<String>();  // Java 10+

// ═══════════════════════════════════════════════════════════════
// 7. TYPE INFERENCE WITH LAMBDAS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Lambda parameter types inferred
val numbers = listOf(1, 2, 3, 4, 5)
val evens = numbers.filter { it % 2 == 0 }  // "it" is inferred as Int

// Java: Also infers lambda params since Java 8
// numbers.stream().filter(n -> n % 2 == 0)

// ═══════════════════════════════════════════════════════════════
// 8. EXPLICIT TYPES (when needed)
// ═══════════════════════════════════════════════════════════════

// Sometimes you need explicit types (ambiguity, API design)
val explicitList: List<Int> = listOf(1, 2, 3)
fun explicitAdd(a: Int, b: Int): Int = a + b

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {
    // Local variable type inference
    val message = "Hello"       // String
    val number = 42             // Int
    val decimal = 3.14          // Double
    val flag = true             // Boolean
    val list = listOf(1, 2, 3)  // List<Int>

    println("message: $message")
    println("number: $number")

    // Smart cast examples
    println(processValue("Hello"))   // String of length 5
    println(processValue(42))        // Int: 84
    println(processValue(listOf(1, 2))) // List of size 2

    // Null smart cast
    printLength("Kotlin")  // Length: 6
    printLength(null)      // (nothing printed)

    // ═══════════════════════════════════════════════════════════════
    // 9. COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // FEATURE                    KOTLIN              JAVA 17
    // ────────────────────────── ─────────────────── ───────────────────
    // Local variable inference   ✅ val/var x = ...   ✅ var x = ...
    // Property inference         ✅ val x = ...       ❌ Must specify type
    // Return type inference      ✅ fun f() = ...     ❌ Must specify type
    // Smart casts                ✅ Automatic         ⚠️ Java 16+ (limited)
    // Null type inference        ✅ String? vs String ❌ No null types
    // Lambda param inference     ✅ { it.length }     ✅ n -> n.length
    // Generic inference          ✅ listof(1,2)       ✅ diamond <>
    //
    // KEY TAKEAWAY:
    // Kotlin's type inference is UNIVERSAL — works everywhere
    // Java's var is LOCAL-ONLY — cannot be used for fields, returns, params
    //
    // ═══════════════════════════════════════════════════════════════
}
