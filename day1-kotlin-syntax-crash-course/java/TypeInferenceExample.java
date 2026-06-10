/**
 * Day 1 - Type Inference in Java vs Kotlin
 *
 * Java:
 *   - Before Java 10: EXPLICIT types everywhere
 *   - Java 10+: var for local variable type inference
 *   - NEVER works for class fields, method parameters, or return types
 *
 * Kotlin:
 *   - Type inference works EVERYWHERE
 *   - Local variables, properties, function return types
 *   - Smart casts after null checks and type checks
 */
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class TypeInferenceExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: EXPLICIT TYPES for class fields (always)
    // ═══════════════════════════════════════════════════════════════

    // Java: Class fields ALWAYS need explicit type (no var for fields!)
    private static String name = "Java";           // Must say "String"
    private static int count = 0;                  // Must say "int"
    private static List<String> list = new ArrayList<>();  // Must say "List<String>"
    private static Map<String, Integer> map = new HashMap<>();  // Must say "Map<String, Integer>"

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA 10+ var (LOCAL variables only)
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Java 10+ var — type inferred from initializer
        var message = "Hello";              // String
        var number = 42;                    // int
        var decimal = 3.14;                 // double
        var flag = true;                    // boolean
        var list2 = List.of(1, 2, 3);       // List<Integer>
        var map2 = Map.of("a", 1, "b", 2);  // Map<String, Integer>

        // ❌ Java var CANNOT be used for:
        // var x;  // COMPILE ERROR: no initializer
        // var y = null;  // COMPILE ERROR: cannot infer type from null

        System.out.println("message: " + message);
        System.out.println("list2: " + list2);

        // ═══════════════════════════════════════════════════════════════
        // 3. JAVA: Smart casts? NOPE — need instanceof + explicit cast
        // ═══════════════════════════════════════════════════════════════

        Object obj = "Hello";

        // Java: instanceof check + separate cast (verbose!)
        if (obj instanceof String) {
            String str = (String) obj;  // Must cast explicitly
            System.out.println("String length: " + str.length());
        }

        // Java 16+: pattern matching for instanceof (better!)
        if (obj instanceof String str) {  // Pattern variable "str" created
            System.out.println("String length (Java 16): " + str.length());
        }

        // ═══════════════════════════════════════════════════════════════
        // 4. JAVA: Generic type inference with diamond <>
        // ═══════════════════════════════════════════════════════════════

        // Java 7+: Diamond operator <>
        var arrayList = new ArrayList<String>();   // Type inferred as ArrayList<String>
        var hashMap = new HashMap<String, Integer>();  // Type inferred

        // ═══════════════════════════════════════════════════════════════
        // 5. JAVA: Type inference for method return types? NOPE
        // ═══════════════════════════════════════════════════════════════

        // Java: Method return types MUST be explicit
        // No equivalent of Kotlin's "fun greet(): String = ..."

        // ═══════════════════════════════════════════════════════════════
        // 6. COMPARISON TABLE
        // ═══════════════════════════════════════════════════════════════
        //
        //   Feature                  Java 17        Kotlin
        //   ──────────────────────── ────────────── ──────────────────────
        //   Local var inference      var (Java 10+) val/var (always)
        //   Class field inference    ❌ Never        ✅ Always
        //   Method return type       ❌ Never        ✅ Yes (auto)
        //   Method param type        ❌ Never        ⚠️ Sometimes (lambdas)
        //   Smart casts              ⚠️ Java 16+    ✅ Automatic
        //   Generic inference        ✅ Diamond <>   ✅ Diamond <>
        //   Null type inference      ❌ Never        ✅ String? vs String
        //
        // ═══════════════════════════════════════════════════════════════

        // Example: Java var limitations
        // var x;           // ❌ Error: no initializer
        // var y = null;    // ❌ Error: cannot infer
        // var z = { 1, 2 }; // ❌ Error: ambiguous array
        // var a = (String) null; // ❌ Error: cannot infer

        // Workaround in Java: explicit type
        String y = null;  // OK with explicit type

        // ═══════════════════════════════════════════════════════════════
        // 7. KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // Java var is LIMITED:
        //   - Only local variables
        //   - Must have initializer
        //   - Cannot be null
        //   - Cannot be method params or return types
        //
        // Kotlin inference is FULL:
        //   - Works for local vars AND properties (class fields)
        //   - Works for function return types
        //   - Works with nullable types (val x: String? = null)
        //   - Smart casts eliminate explicit casting
        //
        // ═══════════════════════════════════════════════════════════════
    }

    // ═══════════════════════════════════════════════════════════════
    // 8. JAVA: NO type inference for return types
    // ═══════════════════════════════════════════════════════════════

    // Java: Must specify return type explicitly
    public static String greet(String name) {
        return "Hello, " + name;
    }

    // Java: Generic type inference in method calls
    public static <T> List<T> listOf(T... items) {
        return List.of(items);
    }
    // Call: List<String> list = listOf("a", "b");  // Type inferred
}
