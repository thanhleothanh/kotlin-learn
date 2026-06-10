/**
 * Day 1 - String Templates in Kotlin
 *
 * Kotlin has built-in string template support:
 *   - $variable — simple variable interpolation
 *   - ${expression} — complex expression interpolation
 *   - """ — raw/multi-line strings (no escaping needed)
 *   - Much more readable than Java's String.format()
 */

fun main() {

    val name = "Kotlin"
    val age = 10
    val pi = 3.14159
    val city = "Hanoi"

    // ═══════════════════════════════════════════════════════════════
    // 1. SIMPLE VARIABLE INTERPOLATION ($variable)
    // ═══════════════════════════════════════════════════════════════

    val greeting1 = "Hello, $name!"
    println(greeting1)  // Hello, Kotlin!

    // Java equivalent: "Hello, " + name + "!"
    // Kotlin: Much cleaner!

    // ═══════════════════════════════════════════════════════════════
    // 2. EXPRESSION INTERPOLATION (${expression})
    // ═══════════════════════════════════════════════════════════════

    val greeting2 = "Hello, $name! You are $age years old. Next year you'll be ${age + 1}."
    println(greeting2)

    // Complex expressions
    val length = "Kotlin".length
    println("Name: $name, Length: ${name.length}")  // Can access properties directly

    // Method calls in templates
    println("Upper case: ${name.uppercase()}")
    println("First char: ${name[0]}")

    // Conditional expressions
    val status = if (age > 18) "adult" else "minor"
    println("$name is an $status")

    // ═══════════════════════════════════════════════════════════════
    // 3. RAW STRINGS (triple-quoted """)
    // ═══════════════════════════════════════════════════════════════

    // Raw strings: no escaping needed, preserve formatting
    val json = """
        {
            "name": "$name",
            "age": $age
        }
    """.trimIndent()
    println(json)

    // Java 15+ equivalent:
    // String json = """
    //         {
    //             "name": "%s",
    //             "age": %d
    //         }
    //         """.formatted(name, age);

    // Raw strings with special characters (no escaping needed!)
    val path = """C:\Users\test\file.txt"""
    println(path)

    val regex = """\d+\.\d+"""
    println(regex)

    // ═══════════════════════════════════════════════════════════════
    // 4. ESCAPING DOLLAR SIGN
    // ═══════════════════════════════════════════════════════════════

    // If you need a literal $ in the string, use ${'$'}
    val price = "Price: ${'$'}${age}"
    println(price)  // Price: $10

    // Or use raw string (no escaping needed)
    val price2 = """Price: $${age}"""
    println(price2)  // Price: $10

    // ═══════════════════════════════════════════════════════════════
    // 5. MULTI-LINE RAW STRINGS
    // ═══════════════════════════════════════════════════════════════

    val multiLine = """
        This is a
        multi-line string
        with $name interpolation
        and ${age + 1} expressions
    """.trimIndent()
    println(multiLine)

    // trimMargin() for alignment
    val aligned = """
        |Name: $name
        |Age: $age
        |City: $city
    """.trimMargin()
    println(aligned)

    // ═══════════════════════════════════════════════════════════════
    // 6. COMPARISON WITH JAVA
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                          KOTLIN
    // ───────────────────────────── ─────────────────────────────
    // "Hello " + name               "Hello $name"
    // "Len: " + name.length()       "Len: ${name.length}"
    // String.format("Hi %s", name)  "Hi $name"
    // """
    //   line1
    //   line2
    // """                            """
    //                                  line1
    //                                  line2
    //                                """
    // No raw string equivalent       """C:\path\file"""
    //
    // ═══════════════════════════════════════════════════════════════

    // ═══════════════════════════════════════════════════════════════
    // 7. PRACTICAL EXAMPLES
    // ═══════════════════════════════════════════════════════════════

    // SQL query building
    val tableName = "users"
    val query = "SELECT * FROM $tableName WHERE age > $age"
    println(query)

    // Log message
    val userId = 123
    val action = "login"
    println("User $userId performed action: $action")

    // Template with calculation
    val items = listOf("apple", "banana", "cherry")
    println("We have ${items.size} items: ${items.joinToString(", ")}")

    // ═══════════════════════════════════════════════════════════════
    // 8. KEY TAKEAWAYS
    // ═══════════════════════════════════════════════════════════════
    //
    // 1. $name — simple variable (no braces needed for simple names)
    // 2. ${expr} — complex expressions (needed for property access, method calls)
    // 3. """ — raw strings (no escaping, preserve formatting)
    // 4. trimIndent() / trimMargin() — clean up indentation
    // 5. ${'$'} — escape dollar sign in regular strings
    // 6. Much more readable than Java's concatenation or String.format()
    //
    // ═══════════════════════════════════════════════════════════════
}
