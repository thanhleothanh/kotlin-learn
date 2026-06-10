/**
 * Day 4 - Destructuring in Java vs Kotlin
 *
 * Java:
 *   - Records have componentN() methods (Java 16+)
 *   - Pattern matching for switch (Java 21+)
 *   - No destructuring declarations
 *
 * Kotlin:
 *   - Destructuring declarations from data classes
 *   - componentN() functions
 *   - Destructuring in lambdas
 *   - Maps and collections destructuring
 */

// ═══════════════════════════════════════════════════════════════
// 1. JAVA: NO DESTRUCTURING
// ═══════════════════════════════════════════════════════════════

// Java: Must access fields individually
class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
}

// Java 16+: Records have componentN() but no destructuring declarations!
record PersonRecord(String name, int age) {}

// ═══════════════════════════════════════════════════════════════
// 2. JAVA: PATTERN MATCHING (Java 21+)
// ═══════════════════════════════════════════════════════════════

// Java 21+: Pattern variables (similar to destructuring in some ways)
// switch (obj) {
//     case PersonRecord(String name, int age) -> ...
// }

// ═══════════════════════════════════════════════════════════════
// 3. JAVA: LOOP DESTRUCTURING (Java 16+)
// ═══════════════════════════════════════════════════════════════

// Java 16+: Can destructure records in loops
// for (var (name, age) : List.of(new PersonRecord("Alice", 25))) {
//     System.out.println(name + ": " + age);
// }

// ═══════════════════════════════════════════════════════════════
// 4. COMPARISON TABLE
// ═══════════════════════════════════════════════════════════════

//   Feature              Java 17+                         Kotlin
//   ──────────────────── ──────────────────────────────── ────────────────────────────
//   Destructuring        No (only in patterns)            val (x, y) = point
//   componentN()         Records only                     data class auto-generates
//   In lambdas           No                               .map { (name, age) -> ... }
//   Maps                 No                               for ((k, v) in map)
//   Collections          No                               for ((i, v) in list.withIndex())
//
// ═══════════════════════════════════════════════════════════════

public class DestructuringExample {
    public static void main(String[] args) {
        System.out.println("Java doesn't have destructuring like Kotlin!");
        System.out.println("Java 16+ records have componentN() but no destructuring declarations.");
    }
}
