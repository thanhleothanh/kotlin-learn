/**
 * Day 1 - Null Safety in Kotlin
 *
 * Kotlin's null safety is one of its biggest advantages over Java:
 *   - Types are NON-NULL by default
 *   - Nullable types marked with ? (e.g., String?)
 *   - Safe call operator: ?.
 *   - Non-null assertion: !!
 *   - Elvis operator: ?:
 *   - Let function for scoped operations
 */

fun main() {

    // ═══════════════════════════════════════════════════════════════
    // 1. NON-NULL TYPES (default in Kotlin)
    // ═══════════════════════════════════════════════════════════════

    val name: String = "Kotlin"
    // val nullName: String = null  // ❌ COMPILE ERROR: null cannot be a String

    // ═══════════════════════════════════════════════════════════════
    // 2. NULLABLE TYPES (explicit with ?)
    // ═══════════════════════════════════════════════════════════════

    val nullableName: String? = null   // ✅ OK — String? can be null
    val nonNullName: String = "Kotlin" // ✅ OK

    println("nullableName: $nullableName")
    println("nonNullName: $nonNullName")

    // ═══════════════════════════════════════════════════════════════
    // 3. SAFE CALL OPERATOR ?.
    // ═══════════════════════════════════════════════════════════════

    // If nullableName is null, expression returns null (no NPE!)
    val length1: Int? = nullableName?.length
    println("length1 (null): $length1")  // null

    val nonNull: String? = "Hello"
    val length2: Int? = nonNull?.length
    println("length2 (non-null): $length2")  // 5

    // Chained safe calls
    data class Address(val city: String?)
    data class Person(val address: Address?)

    val person: Person? = Person(Address("Hanoi"))
    val city: String? = person?.address?.city
    println("city: $city")  // Hanoi

    // ═══════════════════════════════════════════════════════════════
    // 4. NON-NULL ASSERTION !! (use sparingly!)
    // ═══════════════════════════════════════════════════════════════

    // !! converts nullable to non-null, throws NPE if null
    // val length3: Int = nullableName!!.length  // ❌ KotlinNullPointerException!

    val safeName: String = "Kotlin"
    val length3: Int = safeName!!.length  // ✅ OK — safeName is not null
    println("length3: $length3")

    // ═══════════════════════════════════════════════════════════════
    // 5. ELVIS OPERATOR ?: (default value)
    // ═══════════════════════════════════════════════════════════════

    // If left side is null, use right side
    val length4: Int = nullableName?.length ?: 0
    println("length4: $length4")  // 0

    val displayName: String = nullableName ?: "Unknown"
    println("displayName: $displayName")  // Unknown

    // Elvis with early return
    fun processName(name: String?): String {
        val nonNull: String = name ?: return "No name provided"
        return "Name: $nonNull"
    }
    println(processName(null))    // No name provided
    println(processName("Alice")) // Name: Alice

    // ═══════════════════════════════════════════════════════════════
    // 6. SAFE CASTS (as?)
    // ═══════════════════════════════════════════════════════════════

    val something: Any = "Hello"

    // Java: if (something instanceof String) { ... }
    // Kotlin: safe cast
    val safeString: String? = something as? String   // "Hello"
    val safeInt: Int? = something as? Int             // null (no exception!)

    println("safeString: $safeString")
    println("safeInt: $safeInt")

    // ═══════════════════════════════════════════════════════════════
    // 7. LET FUNCTION (scoped null check)
    // ═══════════════════════════════════════════════════════════════

    // let runs block only if not null
    nullableName?.let {
        println("Name is not null: $it")
    } ?: println("Name is null")

    // Java equivalent:
    // if (nullableName != null) {
    //     System.out.println("Name is not null: " + nullableName);
    // }

    // ═══════════════════════════════════════════════════════════════
    // 8. NULLABLE COLLECTIONS
    // ═══════════════════════════════════════════════════════════════

    // List<String?> — list of nullable strings
    val nullableList: List<String?> = listOf("A", null, "C")
    val nonNullList: List<String> = listOf("A", "B", "C")

    // List<String>? — nullable list itself
    val maybeNullList: List<String>? = null

    // ═══════════════════════════════════════════════════════════════
    // 9. FUNNEL OPERATOR ?.let + ?: combinations
    // ═══════════════════════════════════════════════════════════════

    fun greetUser(name: String?) {
        name?.let { nonNull ->
            println("Hello, $nonNull!")
        } ?: println("Hello, stranger!")
    }

    greetUser("Alice")  // Hello, Alice!
    greetUser(null)     // Hello, stranger!

    // ═══════════════════════════════════════════════════════════════
    // 10. PLATFORM TYPES (Java ↔ Kotlin interop)
    // ═══════════════════════════════════════════════════════════════

    // When Kotlin calls Java code, Java types become "platform types"
    // Platform types can be treated as nullable or non-nullable
    // This is where most Kotlin NPEs come from!

    // ═══════════════════════════════════════════════════════════════
    // 11. COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA (manual checks)          KOTLIN (type-safe)
    // ─────────────────────────────  ──────────────────────────────
    // if (s != null) s.length()      s?.length
    // s != null ? s.length : 0       s?.length ?: 0
    // if (s == null) throw ...       s!!  (rarely used)
    // if (s instanceof String) ...   s as? String
    // Optional.ofNullable(s)         s?.let { ... }
    // Objects.requireNonNull(s)      s ?: throw ...
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. Default to non-null types (String, Int, List<T>)
    // 2. Use nullable types ONLY when null is a valid value
    // 3. Prefer ?. over !! (safe over unsafe)
    // 4. Use ?: for default values
    // 5. Use let for scoped operations on nullable
    // 6. Be careful with Java interop (platform types)
    //
    // ═══════════════════════════════════════════════════════════════
}
