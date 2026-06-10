/**
 * Day 2 - Extension Functions in Kotlin
 *
 * Kotlin's extension functions let you add methods to existing classes
 * WITHOUT modifying the source code. This is HUGE!
 *
 * Syntax: fun ClassName.functionName(): ReturnType { ... }
 * Call:   instance.functionName()
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC EXTENSION FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Add "isEmail" check to String
fun String.isEmail(): Boolean {
    return this.contains("@") && this.contains(".")
}

// Add "capitalize" to String
fun String.capitalizeFirst(): String {
    return if (this.isEmpty()) this
    else this[0].uppercaseChar() + this.substring(1)
}

// Add "wordCount" to String
fun String.wordCount(): Int {
    return this.trim().split("\\s+".toRegex()).size
}

// Java equivalent:
// StringUtils.isEmail(str)  — static method, not str.isEmail()

// ═══════════════════════════════════════════════════════════════
// 2. EXTENSION ON NULLABLE TYPES
// ═══════════════════════════════════════════════════════════════

// Add safe operations on nullable String
fun String?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

fun String?.orDefault(default: String = "N/A"): String {
    return this ?: default
}

// ═══════════════════════════════════════════════════════════════
// 3. EXTENSION PROPERTIES (Java CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

// Add "isEmail" as a property (instead of function)
val String.isEmailProperty: Boolean
    get() = this.contains("@") && this.contains(".")

// Add "wordCount" property
val String.wordCountProperty: Int
    get() = this.trim().split("\\s+".toRegex()).size

// Add "titleCase" property
val String.titleCase: String
    get() = this.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }

// ═══════════════════════════════════════════════════════════════
// 4. EXTENSION ON COLLECTIONS
// ═══════════════════════════════════════════════════════════════

// Add "randomOrNull" to List
fun <T> List<T>.randomOrNull(): T? {
    return if (this.isEmpty()) null else this.random()
}

// Add "secondOrNull" to List
fun <T> List<T>.secondOrNull(): T? {
    return if (this.size < 2) null else this[1]
}

// Add "chunk" to List (like Python)
fun <T> List<T>.chunk(size: Int): List<List<T>> {
    return this.chunked(size)
}

// ═══════════════════════════════════════════════════════════════
// 5. EXTENSION ON ANY CLASS (even your own!)
// ═══════════════════════════════════════════════════════════════

data class User(val name: String, val age: Int, val email: String?)

// Add methods to your own class
fun User.toDisplayString(): String {
    return "$name ($age) - ${email ?: "No email"}"
}

fun User.isAdult(): Boolean = age >= 18

// ═══════════════════════════════════════════════════════════════
// 6. EXTENSION ON GENERIC TYPES
// ═══════════════════════════════════════════════════════════════

// Add "applyIf" to any type
fun <T> T.applyIf(condition: Boolean, block: T.() -> T): T {
    return if (condition) this.block() else this
}

// Add "also" variant with condition
fun <T> T.alsoIf(condition: Boolean, block: (T) -> Unit): T {
    if (condition) block(this)
    return this
}

// ═══════════════════════════════════════════════════════════════
// 7. EXTENSION FUNCTIONS AS PARAMETERS (Higher-Order)
// ═══════════════════════════════════════════════════════════════

fun String?.ifNotNull(block: String.() -> Unit) {
    if (this != null) block()
}

// Usage: name.ifNotNull { println(this.length) }

// ═══════════════════════════════════════════════════════════════
// 8. INHERITANCE OF EXTENSIONS (Resolution rules)
// ═══════════════════════════════════════════════════════════════

// Extensions are resolved STATICALLY (at compile time)
// Member functions always win over extensions
// More specific extension wins over less specific

open class Animal
class Dog : Animal()

fun Animal.makeSound() = "Some sound"
fun Dog.makeSound() = "Woof!"

fun animalSound(animal: Animal) = animal.makeSound()  // Always "Some sound"

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic extension functions
    val email = "user@example.com"
    val notEmail = "hello world"
    println("'$email' is email? ${email.isEmail()}")       // true
    println("'$notEmail' is email? ${notEmail.isEmail()}") // false

    println("capitalize: ${"hello".capitalizeFirst()}")    // Hello
    println("word count: ${"Hello World Kotlin".wordCount()}")  // 3

    // Nullable extensions
    val nullStr: String? = null
    println("null isNullOrEmpty: ${nullStr.isNullOrEmpty()}")  // true
    println("null orDefault: ${nullStr.orDefault("N/A")}")    // N/A

    // Extension properties
    println("isEmailProperty: ${email.isEmailProperty}")    // true
    println("title case: ${"hello world".titleCase}")       // Hello World

    // Collection extensions
    val list = listOf(1, 2, 3, 4, 5)
    println("random: ${list.randomOrNull()}")
    println("second: ${list.secondOrNull()}")

    // Own class extensions
    val user = User("Alice", 25, "alice@email.com")
    println("User: ${user.toDisplayString()}")
    println("Is adult? ${user.isAdult()}")

    // Generic extensions
    val name = "Kotlin"
    name.applyIf(true) { uppercase() }.also { println("Applied: $it") }

    // Extension function resolution
    val dog = Dog()
    println("Dog sound: ${dog.makeSound()}")       // Woof! (more specific)
    println("Animal sound: ${animalSound(dog)}")   // Some sound (static resolution)

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA                              KOTLIN
    // ────────────────────────────────  ──────────────────────────────
    // StringUtils.isEmail(str)          str.isEmail()
    // Collections.sort(list)            list.sort()
    // Objects.requireNonNull(s)         s ?: throw ...
    // str.toUpperCase()                 str.uppercase()
    // Collections.unmodifiableList(l)   l.toList()
    // str.isEmpty()                     str.isEmpty()
    // str.length()                      str.length
    // Arrays.toString(arr)              arr.contentToString()
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. Extension functions are resolved STATICALLY
    // 2. Member functions always win over extensions
    // 3. More specific extension wins
    // 4. Extensions can be nullable: fun String?.f()
    // 5. Extensions can be properties: val String.f get() = ...
    // 6. Extensions can have generic receivers: fun <T> T.f()
    //
    // ═══════════════════════════════════════════════════════════════
}
