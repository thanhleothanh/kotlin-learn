/**
 * Day 1 - when Expression in Kotlin
 *
 * Kotlin's when is MUCH more powerful than Java's switch:
 *   - Can match on ANY expression (not just constants)
   - Can use ranges, types, collections, arbitrary conditions
 *   - Always exhaustive (compiler checks)
 *   - Returns values (expression form)
 *   - Replaces both switch AND if-else chains
 */

// ═══════════════════════════════════════════════════════════════
// 1. BASIC when (like switch)
// ═══════════════════════════════════════════════════════════════

fun classicWhen(day: Int): String {
    return when (day) {
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        6, 7 -> "Weekend"    // Multiple values (like Java's case 6, 7:)
        else -> "Invalid"    // Like Java's default:
    }
}

// Java equivalent:
// public static String classicSwitch(int day) {
//     switch (day) {
//         case 1: return "Monday";
//         case 2: return "Tuesday";
//         // ...
//         case 6: case 7: return "Weekend";
//         default: return "Invalid";
//     }
// }

// ═══════════════════════════════════════════════════════════════
// 2. when AS EXPRESSION (returns value directly)
// ═══════════════════════════════════════════════════════════════

fun classifyNumber(num: Int): String {
    val result = when {
        num < 0 -> "Negative"           // No subject — use conditions directly
        num == 0 -> "Zero"
        num % 2 == 0 -> "Positive Even"
        else -> "Positive Odd"
    }
    return result
}

// Java equivalent:
// public static String classifyNumber(int num) {
//     if (num < 0) return "Negative";
//     else if (num == 0) return "Zero";
//     else if (num % 2 == 0) return "Positive Even";
//     else return "Positive Odd";
// }

// ═══════════════════════════════════════════════════════════════
// 3. when WITH RANGES (Java switch CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

fun scoreGrade(score: Int): String {
    return when (score) {
        in 90..100 -> "Excellent"    // Range check
        in 80..89 -> "Good"
        in 70..79 -> "Average"
        in 60..69 -> "Pass"
        else -> "Fail"
    }
}

// Java equivalent: if-else chains (no range matching in switch!)
// if (score >= 90 && score <= 100) return "Excellent";
// else if (score >= 80 && score <= 89) return "Good";
// ...

// ═══════════════════════════════════════════════════════════════
// 4. when WITH TYPE CHECKS (like Java 21 pattern matching)
// ═══════════════════════════════════════════════════════════════

fun describe(obj: Any): String {
    return when (obj) {
        is String -> "String of length ${obj.length}"    // Smart cast!
        is Int -> "Int: ${obj * 2}"                      // Smart cast!
        is List<*> -> "List of size ${obj.size}"          // Smart cast!
        is Map<*, *> -> "Map with ${obj.size} entries"    // Smart cast!
        else -> "Unknown type: ${obj::class.simpleName}"
    }
}

// Java equivalent (Java 21+):
// return switch (obj) {
//     case String s -> "String of length " + s.length();
//     case Integer i -> "Int: " + (i * 2);
//     case List<?> l -> "List of size " + l.size();
//     default -> "Unknown";
// };

// ═══════════════════════════════════════════════════════════════
// 5. when WITH ARBITRARY CONDITIONS (Java switch CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

fun temperatureDescription(temp: Double): String {
    return when {
        temp < -40 -> "Extremely cold"
        temp < 0 -> "Freezing"
        temp < 10 -> "Cold"
        temp < 20 -> "Cool"
        temp < 30 -> "Warm"
        temp < 40 -> "Hot"
        else -> "Extremely hot"
    }
}

// Java: Can only do this with if-else chains

// ═══════════════════════════════════════════════════════════════
// 6. when WITH COLLECTIONS (Java switch CANNOT do this!)
// ═══════════════════════════════════════════════════════════════

fun <T> describeCollection(collection: Collection<T>): String {
    return when {
        collection.isEmpty() -> "Empty collection"
        collection is List && collection.size == 1 -> "Single element list"
        collection is List && collection.size <= 5 -> "Small list (${collection.size} elements)"
        collection is List -> "Large list (${collection.size} elements)"
        collection is Set -> "Set with ${collection.size} elements"
        collection is Map -> "Map with ${collection.size} entries"
        else -> "Unknown collection type"
    }
}

// ═══════════════════════════════════════════════════════════════
// 7. when WITHOUT ARGUMENT (if-else chain replacement)
// ═══════════════════════════════════════════════════════════════

fun processUser(name: String?, age: Int?, email: String?): String {
    return when {
        name == null -> "Name is required"
        age == null -> "Age is required"
        age < 0 -> "Age cannot be negative"
        age > 150 -> "Age seems unrealistic"
        email == null -> "Email is required"
        !email.contains("@") -> "Invalid email format"
        else -> "User $name ($age) - $email"
    }
}

// ═══════════════════════════════════════════════════════════════
// 8. when WITH ENUM CLASSES
// ═══════════════════════════════════════════════════════════════

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

fun move(direction: Direction): String {
    return when (direction) {
        Direction.NORTH -> "Moving up"
        Direction.SOUTH -> "Moving down"
        Direction.EAST -> "Moving right"
        Direction.WEST -> "Moving left"
        // No else needed — enum is exhaustive!
    }
}

// ═══════════════════════════════════════════════════════════════
// 9. when WITH sealed classes (exhaustive checking)
// ═══════════════════════════════════════════════════════════════

sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
    data object Loading : Result()
}

fun handleResult(result: Result): String {
    return when (result) {
        is Result.Success -> "Success: ${result.data}"
        is Result.Error -> "Error: ${result.message}"
        is Result.Loading -> "Loading..."
        // No else needed — sealed class is exhaustive!
    }
}

// ═══════════════════════════════════════════════════════════════
// 10. when WITH IN/COLLECTION MEMBERSHIP
// ═══════════════════════════════════════════════════════════════

fun isVowel(char: Char): Boolean {
    return char.lowercaseChar() in setOf('a', 'e', 'i', 'o', 'u')
}

fun isInRange(num: Int): String {
    return when (num) {
        in 1..10 -> "Low"
        in 11..50 -> "Medium"
        in 51..100 -> "High"
        else -> "Out of range"
    }
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {

    // Basic when
    println("Day 1: ${classicWhen(1)}")
    println("Day 6: ${classicWhen(6)}")

    // when as expression
    println("Number 5: ${classifyNumber(5)}")
    println("Number -3: ${classifyNumber(-3)}")

    // when with ranges
    println("Score 95: ${scoreGrade(95)}")
    println("Score 75: ${scoreGrade(75)}")

    // when with type checks
    println("describe(\"Hello\"): ${describe("Hello")}")
    println("describe(42): ${describe(42)}")
    println("describe(listOf(1,2)): ${describe(listOf(1, 2))}")

    // when without argument
    println("User: ${processUser("Alice", 25, "alice@email.com")}")
    println("User: ${processUser(null, 25, "alice@email.com")}")

    // when with enum
    println("Direction: ${move(Direction.NORTH)}")

    // when with sealed class
    println("Result: ${handleResult(Result.Success("Data loaded"))}")
    println("Result: ${handleResult(Result.Loading)}")

    // Temperature
    println("Temp 25: ${temperatureDescription(25.0)}")

    // ═══════════════════════════════════════════════════════════════
    // COMPARISON SUMMARY
    // ═══════════════════════════════════════════════════════════════
    //
    // JAVA switch                    KOTLIN when
    // ────────────────────────────── ──────────────────────────────
    // switch (x) {                   when (x) {
    //   case 1: ...                    1 -> ...
    //   case 1: case 2: ...            1, 2 -> ...
    //   default: ...                    else -> ...
    // }                               }
    //
    // JAVA switch CANNOT:            KOTLIN when CAN:
    // ────────────────────────────── ──────────────────────────────
    // ❌ Match on ranges              ✅ in 1..10 -> ...
    // ❌ Match on types               ✅ is String -> ...
    // ❌ Match on conditions          ✅ { x > 0 } -> ...
    // ❌ Match on collections         ✅ { it.isEmpty() } -> ...
    // ❌ Return value (Java 14+)      ✅ val x = when { ... }
    // ❌ No fall-through              ✅ No fall-through!
    //
    // KEY RULES:
    // ─────────────────────────────────────────────────
    // 1. when is an EXPRESSION (returns a value)
    // 2. Use -> instead of : (no fall-through!)
    // 3. else replaces default
    // 4. when without argument = if-else chain
    // 5. Exhaustive for sealed classes and enums
    //
    // ═══════════════════════════════════════════════════════════════
}
