/**
 * Day 1 - val/var Equivalent in Java
 * 
 * Java uses:
 *   - final → immutable (like Kotlin's val)
 *   - non-final → mutable (like Kotlin's var)
 *   - No local variable type inference before Java 10
 */
public class ValVarExample {

    // Java class-level fields: ALWAYS need explicit type (no inference at class level)
    private static final String NAME = "Kotlin";    // final = immutable (like val)
    private static int counter = 0;                  // non-final = mutable (like var)

    public static void main(String[] args) {

        // ═══════════════════════════════════════════════════════════════
        // 1. FINAL vs NON-FINAL (val vs var equivalent)
        // ═══════════════════════════════════════════════════════════════

        // final variable (like Kotlin's val) — cannot be reassigned
        final String language = "Kotlin";
        // language = "Java";  // ❌ COMPILE ERROR: cannot assign final variable

        // non-final variable (like Kotlin's var) — can be reassigned
        int count = 0;
        count = 1;  // ✅ OK
        count = 2;  // ✅ OK

        // ═══════════════════════════════════════════════════════════════
        // 2. TYPE INFERENCE with var (Java 10+)
        // ═══════════════════════════════════════════════════════════════

        // Java 10+ local variable type inference (like Kotlin's type inference)
        var message = "Hello";          // inferred as String
        var number = 42;                // inferred as int
        var decimal = 3.14;             // inferred as double
        var list = java.util.List.of(1, 2, 3);  // inferred as List<Integer>

        System.out.println("message type: " + message.getClass().getSimpleName());
        System.out.println("number type: " + Integer.TYPE.getSimpleName());

        // ═══════════════════════════════════════════════════════════════
        // 3. COMPARISON TABLE
        // ═══════════════════════════════════════════════════════════════
        //
        //   Kotlin          Java 17+              Notes
        //   ──────────────  ────────────────────  ─────────────────────────
        //   val x = 5       final int x = 5       Immutable (reassignment forbidden)
        //   var y = 5       int y = 5              Mutable (reassignment allowed)
        //   val z: Int      final int z            Explicit type
        //   var a: Int = 5  int a = 5              Explicit type
        //   var b = 5       var b = 5 (Java 10+)  Type inference (local only in Java)
        //
        // ═══════════════════════════════════════════════════════════════

        System.out.println("language (final): " + language);
        System.out.println("count (mutable): " + count);
        System.out.println("message (var inferred): " + message);

        // ═══════════════════════════════════════════════════════════════
        // 4. KEY DIFFERENCES
        // ═══════════════════════════════════════════════════════════════
        //
        // DIFFERENCE 1: Java var is LOCAL-ONLY, Kotlin var works everywhere
        //   - Java: var only works inside methods, NOT for fields
        //   - Kotlin: val/var works for both properties and local variables
        //
        // DIFFERENCE 2: Java requires explicit types for class fields
        //   - Java: private String name = "test";  // MUST specify String
        //   - Kotlin: val name = "test"            // Inferred as String at property level
        //
        // DIFFERENCE 3: Kotlin val means REFERENCE immutability, not deep immutability
        //   - val list = mutableListOf(1, 2, 3)
        //   - list.add(4)  // ✅ OK! List content changed, reference didn't
        //
        // DIFFERENCE 4: Java final on对象引用 = same behavior as Kotlin val
        //   - final var list = new ArrayList<>();
        //   - list.add(1);  // ✅ OK! Same as Kotlin val
        //
        // ═══════════════════════════════════════════════════════════════

        // Example: val does NOT prevent content mutation
        final var mutableList = new java.util.ArrayList<>(java.util.List.of(1, 2, 3));
        mutableList.add(4);  // ✅ OK — reference is final, but content is mutable
        System.out.println("mutableList after add: " + mutableList);

        // Example: const val equivalent
        // Kotlin: const val MAX_SIZE = 100  (compile-time constant)
        // Java: static final int MAX_SIZE = 100;  (also compile-time constant)
        // Both must be primitives or String, both inlined at compile time

        // ═══════════════════════════════════════════════════════════════
        // 5. SUMMARY FOR JAVA → KOTLIN MIGRATION
        // ═══════════════════════════════════════════════════════════════
        //
        // Java                          →  Kotlin
        // ─────────────────────────────────────────────────
        // final int x = 5;              →  val x = 5
        // int x = 5;                    →  var x = 5
        // private final String s = "";  →  private val s = ""
        // private String s = "";        →  private var s = ""
        // var x = 5; (Java 10+)        →  var x = 5 (same!)
        // static final int C = 10;     →  const val C = 10
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
