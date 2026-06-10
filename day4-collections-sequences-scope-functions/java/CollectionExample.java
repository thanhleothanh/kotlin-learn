/**
 * Day 4 - Collections in Java vs Kotlin
 *
 * Java:
 *   - Collection interface hierarchy (Collection, List, Set, Map)
 *   - Mutable and immutable (unmodifiable) collections
 *   - Arrays (primitive and object)
 *   - Streams API (Java 8+)
 *
 * Kotlin:
 *   - Read-only and mutable collection types
 *   - Rich stdlib functions (map, filter, reduce, etc.)
 *   - No need for Streams API!
 *   - Array support with type-specific operations
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class CollectionExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA COLLECTIONS
    // ═══════════════════════════════════════════════════════════════

    public static void javaCollections() {

        // Java: Mutable by default
        List<String> mutableList = new ArrayList<>();
        mutableList.add("Alice");
        mutableList.add("Bob");
        System.out.println("Mutable: " + mutableList);

        // Java: Immutable (unmodifiable)
        List<String> immutableList = List.of("Alice", "Bob");
        List<String> unmodifiable = Collections.unmodifiableList(mutableList);
        // immutableList.add("Charlie");  // ❌ throws UnsupportedOperationException

        // Java: Map
        Map<String, Integer> map = new HashMap<>();
        map.put("Alice", 25);
        map.put("Bob", 30);
        System.out.println("Map: " + map);

        // Java: Set
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        System.out.println("Set: " + set);
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA: COLLECTION OPERATIONS (pre-Streams)
    // ═══════════════════════════════════════════════════════════════

    public static void preStreamOperations() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        // Java: Manual iteration
        List<String> uppercased = new ArrayList<>();
        for (String name : names) {
            uppercased.add(name.toUpperCase());
        }

        // Java: Manual filtering
        List<String> longNames = new ArrayList<>();
        for (String name : names) {
            if (name.length() > 4) {
                longNames.add(name);
            }
        }

        // Java: Manual transformation
        List<Integer> lengths = new ArrayList<>();
        for (String name : names) {
            lengths.add(name.length());
        }

        System.out.println("Uppercased: " + uppercased);
        System.out.println("Long names: " + longNames);
        System.out.println("Lengths: " + lengths);
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: STREAMS API (Java 8+)
    // ═══════════════════════════════════════════════════════════════

    public static void streamOperations() {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        // Java Streams: much better!
        List<String> uppercased = names.stream()
            .map(String::toUpperCase)
            .toList();

        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .toList();

        List<Integer> lengths = names.stream()
            .map(String::length)
            .toList();

        int sum = names.stream()
            .map(String::length)
            .reduce(0, Integer::sum);

        System.out.println("Uppercased: " + uppercased);
        System.out.println("Long names: " + longNames);
        System.out.println("Lengths: " + lengths);
        System.out.println("Sum of lengths: " + sum);
    }

    // ═══════════════════════════════════════════════════════════════
    // 4. JAVA: COLLECTION FACTORY METHODS
    // ═══════════════════════════════════════════════════════════════

    public static void factoryMethods() {
        // Java 9+: Factory methods for immutable collections
        List<String> list = List.of("Alice", "Bob", "Charlie");
        Set<Integer> set = Set.of(1, 2, 3);
        Map<String, Integer> map = Map.of("Alice", 25, "Bob", 30);

        // Java 10+: Copy of existing collection
        List<String> original = List.of("Alice", "Bob");
        List<String> copy = List.copyOf(original);

        // Java: Mutable collections still need new ArrayList<>()
        List<String> mutable = new ArrayList<>(List.of("Alice", "Bob"));

        System.out.println("List: " + list);
        System.out.println("Set: " + set);
        System.out.println("Map: " + map);
    }

    // ═══════════════════════════════════════════════════════════════
    // 5. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Operation            Java                              Kotlin
    //   ──────────────────── ────────────────────────────────  ────────────────────────────
    //   Create immutable     List.of("a", "b")                 listOf("a", "b")
    //   Create mutable       new ArrayList<>(List.of("a"))     mutableListOf("a", "b")
    //   Map                  Map.of("k", "v")                  mapOf("k" to "v")
    //   Set                  Set.of(1, 2, 3)                   setOf(1, 2, 3)
    //   Filter               stream().filter(...).toList()     filter { ... }
    //   Map                  stream().map(...).toList()        map { ... }
    //   Reduce               stream().reduce(...)              reduce { ... }
    //   ForEach              stream().forEach(...)             forEach { ... }
    //   Sort                 stream().sorted().toList()        sorted()
    //   Distinct             stream().distinct().toList()      distinct()
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        javaCollections();
        preStreamOperations();
        streamOperations();
        factoryMethods();

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA:
        //   - Mutable by default (new ArrayList<>())
        //   - Immutable needs List.of() or Collections.unmodifiableList()
        //   - Streams API for functional operations
        //   - Verbose syntax (.stream().map().filter().toList())
        //   - No built-in lazy evaluation (except Streams)
        //
        // KOTLIN:
        //   - Read-only by default (listOf, mapOf)
        //   - Mutable separate (mutableListOf, mutableMapOf)
        //   - Rich stdlib (map, filter, reduce, etc.)
        //   - No Streams API needed!
        //   - Lazy sequences available
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
