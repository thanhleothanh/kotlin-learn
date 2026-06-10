/**
 * Day 1 - val/var in Kotlin
 *
 * Kotlin uses:
 *   - val → immutable reference (read-only, like Java's final)
 *   - var → mutable reference (re-assignable)
 *   - Type inference works everywhere (properties AND local variables)
 */

fun main() {

    // ═══════════════════════════════════════════════════════════════
    // 1. val (immutable — like Java's final)
    // ═══════════════════════════════════════════════════════════════

    val language = "Kotlin"    // Type inferred as String
    // language = "Java"       // ❌ COMPILE ERROR: Val cannot be reassigned

    val number: Int = 42      // Explicit type (type after variable name!)
    val decimal = 3.14        // Inferred as Double
    val isValid = true        // Inferred as Boolean

    // ═══════════════════════════════════════════════════════════════
    // 2. var (mutable — like Java's non-final)
    // ═══════════════════════════════════════════════════════════════

    var count = 0
    count = 1    // ✅ OK
    count = 2    // ✅ OK

    var name: String = "Alice"
    name = "Bob"  // ✅ OK

    // ═══════════════════════════════════════════════════════════════
    // 3. TYPE INFERENCE — Kotlin does it everywhere
    // ═══════════════════════════════════════════════════════════════

    // Local variables — type inferred
    val inferredString = "Hello"       // String
    val inferredInt = 42               // Int
    val inferredDouble = 3.14          // Double
    val inferredList = listOf(1, 2, 3) // List<Int>

    // Class properties — type inferred (Java CANNOT do this!)
    // In Kotlin, even properties get type inference
    // Java: private String name = "test";  // MUST specify String
    // Kotlin: private val name = "test"    // Inferred

    println("language: $language, count: $count")

    // ═══════════════════════════════════════════════════════════════
    // 4. val = REFERENCE immutability, NOT deep immutability
    // ═══════════════════════════════════════════════════════════════

    val list = mutableListOf(1, 2, 3)
    list.add(4)          // ✅ OK! List content changed, reference didn't
    println("list: $list")  // [1, 2, 3, 4]

    // To make it truly immutable:
    val immutableList = listOf(1, 2, 3)
    // immutableList.add(4)  // ❌ COMPILE ERROR: listOf returns read-only List

    // ═══════════════════════════════════════════════════════════════
    // 5. const val (compile-time constant)
    // ═══════════════════════════════════════════════════════════════

    // Kotlin: const val MAX_SIZE = 100
    // Equivalent Java: static final int MAX_SIZE = 100;
    // Must be: top-level or object property, primitive or String, no custom getter

    // ═══════════════════════════════════════════════════════════════
    // 6. LATEINIT (for deferred initialization)
    // ═══════════════════════════════════════════════════════════════

    // Kotlin has no "var" field without initial value (unlike Java)
    // Use lateinit for deferred initialization:
    lateinit var lateName: String
    lateName = "Initialized later"
    println("lateName: $lateName")

    // ═══════════════════════════════════════════════════════════════
    // 7. COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // Java                          →  Kotlin
    // ─────────────────────────────────────────────────
    // final int x = 5;              →  val x = 5
    // int x = 5;                    →  var x = 5
    // private final String s = "";  →  private val s = ""
    // private String s = "";        →  private var s = ""
    // static final int C = 10;     →  const val C = 10
    // String s; (late init)         →  lateinit var s: String
    //
    // KEY DIFFERENCES:
    // ─────────────────────────────────────────────────
    // 1. Java: var only for local vars. Kotlin: var everywhere
    // 2. Java: type BEFORE variable. Kotlin: type AFTER (val x: Int)
    // 3. Kotlin: const val for compile-time constants (not var!)
    // 4. Kotlin: lateinit for deferred init (Java: uninitialized field)
    // 5. val ≠ immutability of contents, only reference immutability
    //
    // ═══════════════════════════════════════════════════════════════
}
