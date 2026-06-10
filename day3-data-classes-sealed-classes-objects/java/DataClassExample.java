/**
 * Day 3 - Data Classes in Java vs Kotlin
 *
 * Java:
 *   - POJOs / JavaBeans (boilerplate heaven!)
 *   - Records (Java 16+) — immutable data carriers
 *   - Must manually implement equals(), hashCode(), toString()
 *   - Lombok @Data annotation (third-party)
 *
 * Kotlin:
 *   - data class — auto-generates equals(), hashCode(), toString(), copy()
 *   - Much less boilerplate
 *   - First-class support for immutable data
 */
import java.util.Objects;

public class DataClassExample {

    // ═══════════════════════════════════════════════════════════════
    // 1. JAVA: TRADITIONAL POJO (boilerplate!)
    // ═══════════════════════════════════════════════════════════════

    // Java: Traditional POJO with all boilerplate
    public static class UserPOJO {
        private final String name;
        private final int age;

        public UserPOJO(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }

        // Must manually implement equals, hashCode, toString
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserPOJO userPOJO = (UserPOJO) o;
            return age == userPOJO.age && Objects.equals(name, userPOJO.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return "UserPOJO{name='" + name + "', age=" + age + "}";
        }

        // Need to manually create copy method if needed
        public UserPOJO withName(String newName) {
            return new UserPOJO(newName, this.age);
        }

        public UserPOJO withAge(int newAge) {
            return new UserPOJO(this.name, newAge);
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 2. JAVA RECORDS (Java 16+ — better!)
    // ═══════════════════════════════════════════════════════════════

    // Java 16+: Records — immutable data carriers
    // Auto-generates: constructor, getters, equals(), hashCode(), toString()
    public record UserRecord(String name, int age) {}

    // Java record with custom methods
    public record Point(int x, int y) {
        // Compact constructor (validation)
        public Point {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Point cannot have negative coordinates");
            }
        }

        // Custom method
        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // 3. JAVA: LOMBOK (third-party)
    // ═══════════════════════════════════════════════════════════════

    // @Data  — generates getters, setters, equals, hashCode, toString
    // @Value — immutable version
    // @Builder — builder pattern
    // @AllArgsConstructor, @NoArgsConstructor

    // ═══════════════════════════════════════════════════════════════
    // 4. COMPARISON TABLE
    // ═══════════════════════════════════════════════════════════════
    //
    //   Feature              Java POJO             Java Record           Kotlin data class
    //   ──────────────────── ───────────────────── ───────────────────── ─────────────────────
    //   Boilerplate          High                  Low                   Zero
    //   Immutability         Manual                Built-in              Built-in (val)
    //   equals/hashCode      Manual                Auto                  Auto
    //   toString             Manual                Auto                  Auto
    //   copy                 Manual                No (use constructor)  Auto (copy())
    //   destructuring        No                    No                    Yes (componentN)
    //   inheritance          Yes                   No (final)            Yes (open)
    //   validation           Constructor           Compact constructor   init block
    //
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        // POJO
        UserPOJO pojo1 = new UserPOJO("Alice", 25);
        UserPOJO pojo2 = new UserPOJO("Alice", 25);
        System.out.println("POJO: " + pojo1);
        System.out.println("POJO equals: " + pojo1.equals(pojo2));  // true

        // Java Record
        UserRecord record1 = new UserRecord("Bob", 30);
        UserRecord record2 = new UserRecord("Bob", 30);
        System.out.println("Record: " + record1);
        System.out.println("Record equals: " + record1.equals(record2));  // true
        System.out.println("Record name: " + record1.name());  // getter method

        // Record with validation
        Point p1 = new Point(3, 4);
        Point p2 = new Point(6, 8);
        System.out.println("Distance: " + p1.distanceTo(p2));

        // Record with destructuring (Java 16+)
        var (x, y) = p1;
        System.out.println("Destructured: x=$x, y=$y");

        // ═══════════════════════════════════════════════════════════════
        // KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        //
        // JAVA POJO:
        //   - Lots of boilerplate
        //   - Must manually implement equals, hashCode, toString
        //   - Mutable by default
        //   - Need Lombok for convenience
        //
        // JAVA RECORD:
        //   - Immutable by default
        //   - Auto-generates constructor, getters, equals, hashCode, toString
        //   - Cannot be mutated (no setters!)
        //   - Cannot extend classes (final)
        //   - Has componentN() methods for destructuring
        //
        // KOTLIN DATA CLASS:
        //   - Immutable by default (val properties)
        //   - Auto-generates everything Java Record does
        //   - PLUS auto-generates copy() method!
        //   - Can be extended (open data class)
        //   - Can have var properties (mutable)
        //
        // ═══════════════════════════════════════════════════════════════
    }
}
