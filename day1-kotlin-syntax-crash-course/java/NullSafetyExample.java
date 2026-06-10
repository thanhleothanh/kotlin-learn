/**
 * Day 1 - Null Safety in Java (compared to Kotlin)
 *
 * Java approach:
 *   - Any reference can be null (except primitives)
 *   - NullPointerException is the #1 runtime exception
 *   - No compile-time null checks
 *   - Workarounds: Optional, @Nullable/@NonNull annotations, manual checks
 *
 * Kotlin approach:
 *   - Null safety is BUILT INTO the type system
 *   - Types are non-null by default
 *   - Nullable types explicitly marked with ?
 *   - Safe operators: ?. !! ?: let
 */
import java.util.Optional;

public class NullSafetyExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: Any reference CAN be null
    // ═══════════════════════════════════════════════════════════════

    private static String name = null;     // ✅ OK in Java
    private static String name2 = "Hello"; // Also OK

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA NULL CHECKS (Manual — error-prone)
    // ═══════════════════════════════════════════════════════════════

    public static int getStringLength(String s) {
        // Java: You MUST manually check for null
        if (s != null) {
            return s.length();
        }
        return 0;  // or throw exception
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: Safe navigation is CLUMSY
    // ═══════════════════════════════════════════════════════════════

    // Nested null check (ugly!)
    public static String getCityName(Object address) {
        if (address instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) address;
            Object city = map.get("city");
            if (city instanceof String) {
                return (String) city;
            }
        }
        return "Unknown";
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA: Optional<T> (Java 8+ — like Kotlin's nullable type)
    // ═══════════════════════════════════════════════════════════════

    public static Optional<String> findName(int id) {
        if (id == 1) {
            return Optional.of("Alice");
        }
        return Optional.empty();  // Instead of null
    }

    // ═══════════════════════════════════════════════════════════════
    // 5. JAVA: @Nullable / @NonNull annotations (Static analysis)
    // ═══════════════════════════════════════════════════════════════

    // Annotations don't prevent null at runtime, only help IDE/static analysis
    // public @NonNull String getName() { ... }
    // public @Nullable String findUser(int id) { ... }

    // ═══════════════════════════════════════════════════════════════
    // 6. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Kotlin                      Java
    //   ─────────────────────────── ──────────────────────────────
    //   var name: String = "Hi"     String name = "Hi"
    //   var name: String? = null    String name = null
    //   name?.length                name != null ? name.length() : null
    //   name!!.length               name.length()  (crashes if null!)
    //   name ?: "default"           name != null ? name : "default"
    //   name?.let { ... }           if (name != null) { ... }
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // ═══════════════════════════════════════════════════════════════
        // 7. JAVA NULL HANDLING PATTERNS
        // ═══════════════════════════════════════════════════════════════

        // Pattern 1: Manual null check
        String name = null;
        if (name != null) {
            System.out.println("Name length: " + name.length());
        } else {
            System.out.println("Name is null");
        }

        // Pattern 2: Ternary operator
        int length = (name != null) ? name.length() : 0;

        // Pattern 3: Optional (Java 8+)
        Optional<String> optName = Optional.ofNullable(name);
        int len = optName.map(String::length).orElse(0);

        // Pattern 4: Objects utility class
        int len2 = java.util.Objects.requireNonNullElse(name, "").length();

        // Pattern 5: Try-catch for NPE (ugly!)
        try {
            int len3 = name.length();  // Will throw NPE!
        } catch (NullPointerException e) {
            System.out.println("Name was null!");
        }

        // ═══════════════════════════════════════════════════════════════
        // 8. JAVA STREAMS + Optional (closest to Kotlin's approach)
        // ═══════════════════════════════════════════════════════════════

        Optional.ofNullable(name)
            .filter(n -> !n.isEmpty())
            .ifPresent(n -> System.out.println("Name: " + n));

        // ═══════════════════════════════════════════════════════════════
        // 9. KEY TAKEAWAYS FOR JAVA → KOTLIN
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA PROBLEMS:
        //   1. NullPointerExceptions at runtime (not compile-time)
        //   2. Manual null checks everywhere (verbose, error-prone)
        //   3. No language-level distinction between nullable/non-nullable
        //   4. Optional is for return types only, not fields
        //
        // KOTLIN SOLUTIONS:
        //   1. Nullable types (String?) are explicit in the type
        //   2. Safe call operator (?.) eliminates manual checks
        //   3. The compiler ENFORCEs null checks
        //   4. !! operator: explicit "I know this is not null" (rare)
        //   5. ?: operator: Elvis operator for defaults
        //   6. let: scoped null checks
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
