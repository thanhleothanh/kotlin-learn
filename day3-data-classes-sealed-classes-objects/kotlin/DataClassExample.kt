/**
 * Day 3 - Data Classes in Kotlin
 *
 * Kotlin's data class is like Java Records, but MORE powerful:
 *   - Auto-generates: equals(), hashCode(), toString(), copy(), componentN()
 *   - Can be immutable (val) or mutable (var)
 *   - Can extend other classes
 *   - Can have custom methods
 *   - Much less boilerplate than Java POJOs
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC DATA CLASS
// ═══════════════════════════════════════════════════════════════

// Kotlin: data class auto-generates everything!
data class User(val name: String, val age: Int)

// Java equivalent:
// public record User(String name, int age) {}
// OR (pre-Java 16):
// public class User {
//     private final String name;
//     private final int age;
//     // constructor, getters, equals, hashCode, toString...
// }

// ═══════════════════════════════════════════════════════════════
// 2. DATA CLASS WITH VALIDATION
// ═══════════════════════════════════════════════════════════════

// Kotlin: Use init block for validation
data class Point(val x: Int, val y: Int) {
    init {
        require(x >= 0) { "x must be non-negative" }
        require(y >= 0) { "y must be non-negative" }
    }

    // Custom methods
    fun distanceTo(other: Point): Double {
        return Math.sqrt(((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y)).toDouble())
    }
}

// Java equivalent:
// public record Point(int x, int y) {
//     public Point {
//         if (x < 0 || y < 0) throw new IllegalArgumentException("...");
//     }
//     public double distanceTo(Point other) { ... }
// }

// ═══════════════════════════════════════════════════════════════
// 3. DATA CLASS WITH DEFAULT VALUES
// ═══════════════════════════════════════════════════════════════

// Kotlin: Default values (Java Records cannot do this!)
data class Config(
    val host: String = "localhost",
    val port: Int = 8080,
    val secure: Boolean = false,
    val timeout: Int = 30000
)

// Java equivalent: Need builder pattern or multiple constructors

// ═══════════════════════════════════════════════════════════════
// 4. DATA CLASS WITH MUTABLE PROPERTIES
// ═══════════════════════════════════════════════════════════════

// Kotlin: Can have var properties (Java Records cannot!)
data class MutableUser(var name: String, var age: Int)

// Java Records are ALWAYS immutable!
// Kotlin data class can be mutable if needed

// ═══════════════════════════════════════════════════════════════
// 5. DATA CLASS COPY METHOD (auto-generated!)
// ═══════════════════════════════════════════════════════════════

// Kotlin: copy() method for creating modified copies
fun demonstrateCopy() {
    val user = User("Alice", 25)

    // Create copy with different age
    val olderUser = user.copy(age = 26)

    // Create copy with different name
    val renamedUser = user.copy(name = "Bob")

    println("Original: $user")
    println("Older: $olderUser")
    println("Renamed: $renamedUser")

    // Java equivalent:
    // User olderUser = new User(user.name(), 26);  // Must use constructor
}

// ═══════════════════════════════════════════════════════════════
// 6. DATA CLASS DESTRUCTURING
// ═══════════════════════════════════════════════════════════════

// Kotlin: Destructuring declarations
fun demonstrateDestructuring() {
    val user = User("Alice", 25)

    // Destructure into variables
    val (name, age) = user
    println("Name: $name, Age: $age")

    // Java equivalent:
    // var (name, age) = user;  // Java 16+ with records
    // String name = user.name();
    // int age = user.age();
}

// ═══════════════════════════════════════════════════════════════
// 7. DATA CLASS WITH CUSTOM PROPERTIES
// ═══════════════════════════════════════════════════════════════

// Kotlin: Can have computed properties
data class Rectangle(val width: Int, val height: Int) {
    // Computed property (not stored, calculated on access)
    val area: Int get() = width * height
    val perimeter: Int get() = 2 * (width + height)
    val isSquare: Boolean get() = width == height
}

// Java Records: Can have methods, but computed properties are just methods

// ═══════════════════════════════════════════════════════════════
// 8. DATA CLASS WITH COLLECTIONS
// ═══════════════════════════════════════════════════════════════

// Kotlin: Data classes work great with collections
data class Team(
    val name: String,
    val members: List<String>,
    val score: Int
)

fun demonstrateCollections() {
    val team = Team("Kotlin Learners", listOf("Alice", "Bob", "Charlie"), 95)

    println("Team: ${team.name}")
    println("Members: ${team.members.joinToString(", ")}")
    println("Score: ${team.score}")

    // Copy with modified collection
    val updatedTeam = team.copy(members = team.members + "David")
    println("Updated members: ${updatedTeam.members}")
}

// ═══════════════════════════════════════════════════════════════
// 9. DATA CLASS EQUALS AND HASHCODE
// ═══════════════════════════════════════════════════════════════

fun demonstrateEquals() {
    val user1 = User("Alice", 25)
    val user2 = User("Alice", 25)
    val user3 = User("Bob", 30)

    println("user1 == user2: ${user1 == user2}")  // true (structural equality)
    println("user1 == user3: ${user1 == user3}")  // false
    println("user1 equals user2: ${user1.equals(user2)}")  // true

    // HashCode is consistent with equals
    val set = setOf(user1, user2, user3)
    println("Set size: ${set.size}")  // 2 (user1 and user2 are equal)
}

// ═══════════════════════════════════════════════════════════════
// 10. DATA CLASS TOSTRING
// ═══════════════════════════════════════════════════════════════

fun demonstrateToString() {
    val user = User("Alice", 25)
    println("toString: $user")  // User(name=Alice, age=25)

    // Java equivalent:
    // System.out.println("toString: " + user.toString());
    // User{name='Alice', age=25}
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic data class
    val user = User("Alice", 25)
    println("User: $user")
    println("Name: ${user.name}")
    println("Age: ${user.age}")

    // Data class with validation
    val point = Point(3, 4)
    println("Point: $point")
    println("Distance: ${point.distanceTo(Point(6, 8))}")

    // Default values
    val defaultConfig = Config()
    val customConfig = Config(host = "example.com", port = 9090, secure = true)
    println("Default config: $defaultConfig")
    println("Custom config: $customConfig")

    // Copy method
    demonstrateCopy()

    // Destructuring
    demonstrateDestructuring()

    // Computed properties
    val rect = Rectangle(5, 10)
    println("Rectangle: $rect")
    println("Area: ${rect.area}")
    println("Perimeter: ${rect.perimeter}")
    println("Is square? ${rect.isSquare}")

    // Collections
    demonstrateCollections()

    // Equals and hashCode
    demonstrateEquals()

    // ToString
    demonstrateToString()

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA POJO             JAVA RECORD         KOTLIN DATA CLASS
    // ───────────────────── ──────────────────── ────────────────────
    // Lots of boilerplate   Less boilerplate    Zero boilerplate
    // Mutable               Immutable           Immutable (val) or Mutable (var)
    // Manual equals/hash    Auto                Auto
    // Manual toString       Auto                Auto
    // Manual copy           Constructor only    Auto copy()
    // No destructuring      componentN()        componentN()
    // Can extend classes    Cannot (final)      Can extend (open)
    // No default values     No default values   Default values
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. val properties = immutable data class
    // 2. var properties = mutable data class (use sparingly!)
    // 3. copy() for creating modified copies
    // 4. componentN() for destructuring
    // 5. All primary constructor properties are in equals/hashCode
    // 6. init block for validation
    // 7. Can have computed properties (val x get() = ...)
    //
    // ═══════════════════════════════════════════════════════════════
}
