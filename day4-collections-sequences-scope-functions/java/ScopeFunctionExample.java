/**
 * Day 4 - Scope Functions in Java vs Kotlin
 *
 * Java:
 *   - No scope functions
 *   - Use anonymous classes or lambdas
 *   - Manual null checks
 *   - Use "with" statement only in some languages
 *
 * Kotlin:
 *   - let, run, apply, also, with
 *   - Each has different use cases
 *   - Much more concise code
 *   - Perfect for object configuration and null checks
 */

// ═══════════════════════════════════════════════════════════════
// 1. JAVA: NO SCOPE FUNCTIONS
// ═══════════════════════════════════════════════════════════════

// Java: Must use verbose patterns
class Person {
    String name;
    int age;
    String email;

    Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Java: Configuration pattern
    static Person createAndConfigure(String name) {
        Person p = new Person(name, 0, "");
        p.age = 25;
        p.email = name.toLowerCase() + "@example.com";
        return p;
    }

    // Java: Null check pattern
    static String processName(String name) {
        if (name != null) {
            return name.toUpperCase();
        }
        return "UNKNOWN";
    }
}

// ═══════════════════════════════════════════════════════════════
// 2. JAVA: WORKAROUNDS FOR SCOPE
// ═══════════════════════════════════════════════════════════════

// Java: Try-with-resources (similar to apply/let for AutoCloseable)
// try (var resource = new BufferedReader(...)) {
//     // use resource
// }

// Java: Instance initializer block
class Config {
    String host;
    int port;

    {
        // Instance initializer (similar to apply)
        host = "localhost";
        port = 8080;
    }
}

// ═══════════════════════════════════════════════════════════════
// 3. COMPARISON TABLE
// ═══════════════════════════════════════════════════════════════

//   Function  Receiver  Returns  Use Case
//   ────────  ────────  ───────  ──────────────────────────────
//   let       it        result  Null checks, transformations
//   run       this      result  Object configuration + result
//   apply     this      this    Object configuration
//   also      it        this    Side effects, logging
//   with      this      result  Multiple operations on object

// ═══════════════════════════════════════════════════════════════
// 4. KEY TAKEAWAYS
// ═══════════════════════════════════════════════════════════════

// JAVA:
//   - No scope functions
//   - Must use verbose if-else or try-catch
//   - Configuration requires builder pattern or manual assignment
//   - Null checks are manual
//
// KOTLIN:
//   - 5 scope functions for different use cases
//   - let: null checks and transformations
//   - run: object configuration with result
//   - apply: object configuration (returns object)
//   - also: side effects and logging
//   - with: multiple operations on object
//   - Much more concise and readable!

public class ScopeFunctionExample {
    public static void main(String[] args) {
        System.out.println("See Kotlin examples for scope functions!");
        System.out.println("Java doesn't have equivalent features.");
    }
}
