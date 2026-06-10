/**
 * Day 2 - Infix Functions in Java vs Kotlin
 *
 * Java:
 *   - No infix functions!
 *   - Must use method call syntax: map.get(key)
 *   - Workaround: operator overloading (not the same)
 *
 * Kotlin:
 *   - Infix functions: call without dots and parentheses
 *   - Syntax: infix fun function(param) = value
 *   - Call: a function b  (instead of a.function(b))
 *   - Used in standard library: to, shr, shl, xor, etc.
 */
import java.util.Map;
import java.util.HashMap;

public class InfixExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: NO INFIX FUNCTIONS!
    // ═══════════════════════════════════════════════════════════════

    // Java: Must use method call syntax
    // map.get(key)
    // str.length()
    // list.add(item)

    // Java: No way to call without dots and parentheses
    // map get key  // ❌ Syntax error!

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: OPERATOR OVERLOADING (similar concept, different syntax)
    // ═══════════════════════════════════════════════════════════════

    // Java: Can overload +, -, *, /, etc. with operator keyword
    // But NOT for arbitrary function names!

    // Java: Cannot create custom operators
    // a merge b  // ❌ Not possible in Java

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: WORKAROUNDS
    // ═══════════════════════════════════════════════════════════════

    // Java: Static import for cleaner syntax (but still not infix)
    // import static Collections.sort;
    // sort(list);  // Cleaner, but not infix

    // Java: Builder pattern (similar to infix for configuration)
    // new RequestBuilder()
    //     .withName("test")
    //     .withAge(25)
    //     .build();

    // ═══════════════════════════════════════════════════════════════
    // 4. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java                              Kotlin
    //   ──────────────────── ────────────────────────────────  ────────────────────────────
    //   Method call          obj.method(arg)                   obj method arg
    //   Infix functions      ❌ Not possible                   ✅ infix fun f()
    //   Custom operators     ❌ Only +, -, *, /, etc.          ✅ Any name
    //   Builder pattern      new Builder().x().y()             builder x y
    //   Map creation         Map.of("k", "v")                  mapOf("k" to "v")
    //   Bit shifts           1 << 2                            1 shl 2
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Java: Map creation
        Map<String, Integer> map = Map.of("Alice", 25, "Bob", 30);
        System.out.println("Map: " + map);

        // Java: Must use .get()
        System.out.println("Alice's age: " + map.get("Alice"));

        // Java: Bit shifts
        int shifted = 1 << 2;
        System.out.println("Shifted: " + shifted);

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA:
        //   1. No infix functions
        //   2. Must use obj.method() syntax
        //   3. Can only overload standard operators (+, -, etc.)
        //   4. Builder pattern is verbose
        //
        // KOTLIN:
        //   1. infix fun for natural syntax
        //   2. a function b  (instead of a.function(b))
        //   3. Standard library uses infix: to, shr, shl, xor
        //   4. Much more readable for DSLs
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
