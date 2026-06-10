/**
 * Day 1 - when (Kotlin) vs switch (Java)
 *
 * Java switch:
 *   - Classic switch statement (Java 14+ with enhanced switch)
 *   - Switch expression (Java 14+)
 *   - Pattern matching for switch (Java 21+)
 *   - Limited: mostly for discrete values
 *
 * Kotlin when:
 *   - Replaces both switch AND if-else chains
 *   - Can match on ANY expression (not just constants)
 *   - Can use ranges, types, collections
 *   - Always exhaustive (compiler checks)
 *   - Returns values (expression form)
 */
import java.util.List;

public class WhenExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. CLASSIC JAVA SWITCH (pre-Java 14)
    // ═══════════════════════════════════════════════════════════════

    public static String classicSwitch(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
            case 7:
                return "Weekend";
            default:
                return "Invalid";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. ENHANCED SWITCH (Java 14+)
    // ═══════════════════════════════════════════════════════════════

    public static String enhancedSwitch(int day) {
        return switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6, 7 -> "Weekend";  // Multiple values
            default -> "Invalid";
        };
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA 21+: PATTERN MATCHING FOR SWITCH
    // ═══════════════════════════════════════════════════════════════

    public static String patternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String: " + s;
            case List<?> l -> "List of size " + l.size();
            case null -> "null";
            default -> "Unknown";
        };
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA: IF-ELSE CHAINS (when switch is not enough)
    // ═══════════════════════════════════════════════════════════════

    public static String classifyNumber(int num) {
        // Java: if-else chains for ranges (switch can't do this easily!)
        if (num < 0) {
            return "Negative";
        } else if (num == 0) {
            return "Zero";
        } else if (num % 2 == 0) {
            return "Positive Even";
        } else {
            return "Positive Odd";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 5. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java switch                      Kotlin when
    //   ──────────────────── ──────────────────────────────── ────────────────────────────
    //   Syntax               switch (x) { case 1: ... }      when (x) { 1 -> ... }
    //   Multiple values      case 1: case 2: ...             1, 2 -> ...
    //   Ranges               ❌ Not supported                 in 1..10 -> ...
    //   Type checks          Java 21: case String s ->        is String -> ...
    //   Returns value        Java 14: switch (x) { ... }     when (x) { ... } (expression)
    //   Exhaustiveness       Optional (default:)             Required (or else branch)
    //   Wildcard             default:                        else -> ...
    //   Fall-through         Yes (break needed!)             No fall-through!
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // Classic switch
        System.out.println("Day 1: " + classicSwitch(1));
        System.out.println("Day 6: " + classicSwitch(6));

        // Enhanced switch (Java 14+)
        System.out.println("Day 2: " + enhancedSwitch(2));

        // Pattern matching switch (Java 21+)
        System.out.println("Pattern: " + patternSwitch(42));
        System.out.println("Pattern: " + patternSwitch("Hello"));

        // Classification (if-else chains)
        System.out.println("Number: " + classifyNumber(5));
        System.out.println("Number: " + classifyNumber(-3));

        // ═══════════════════════════════════════════════════════════════
        // 6. KEY LIMITATIONS OF JAVA SWITCH
        // ═══════════════════════════════════════════════════════════════
        //
        // Java switch CANNOT:
        //   1. Match on ranges (1..10)
        //   2. Match on type checks (without pattern matching)
        //   3. Match on arbitrary conditions (x > 0)
        //   4. Match on collections
        //   5. Match on multiple variables
        //
        // Kotlin when CAN do all of these!
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
