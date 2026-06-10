# Day 1: Kotlin Syntax Crash Course

> For Java 17+ developers learning Kotlin

---

## 1. val/var vs final/non-final

| Feature | Java | Kotlin |
|---------|------|--------|
| Immutable | `final int x = 5;` | `val x = 5` |
| Mutable | `int x = 5;` | `var x = 5` |
| Compile-time constant | `static final int C = 10;` | `const val C = 10` |
| Type inference (local) | `var x = 5;` (Java 10+) | `var x = 5` |
| Type inference (field) | ❌ Never | `val x = 5` ✅ |

### Key Differences
- **Java `var`** is local-only; **Kotlin `var`** works everywhere (fields, locals)
- **Kotlin type** goes AFTER variable: `val x: Int = 5` vs Java `int x = 5`
- **`val` ≠ immutability** — it's reference immutability. `val list = mutableListOf(1,2,3)` can still `list.add(4)`
- **`lateinit var`** in Kotlin ≈ uninitialized field in Java

---

## 2. Null Safety

| Operation | Java | Kotlin |
|-----------|------|--------|
| Nullable type | `String s = null;` | `var s: String? = null` |
| Safe access | `if (s != null) s.length()` | `s?.length` |
| Default value | `s != null ? s : "default"` | `s ?: "default"` |
| Force unwrap | `s.length()` (NPE!) | `s!!.length` (NPE!) |
| Type check + cast | `if (s instanceof String) (String) s` | `s as? String` |
| Scoped operation | `if (s != null) { ... }` | `s?.let { ... }` |

### Key Rules
1. **Non-null by default** — `String` cannot be null
2. **Explicit nullable** — `String?` can be null
3. **Prefer `?.` over `!!`** — safe over unsafe
4. **Use `?:`** for defaults (Elvis operator)
5. **Use `let`** for scoped null checks
6. **Platform types** — Java interop can bypass null safety!

---

## 3. Type Inference

| Feature | Java 17 | Kotlin |
|---------|---------|--------|
| Local variables | `var x = 5` (Java 10+) | `val x = 5` |
| Class fields | ❌ `private int x = 5` | ✅ `val x = 5` |
| Return types | ❌ Must specify | ✅ `fun f() = 5` |
| Smart casts | ⚠️ Java 16+ only | ✅ Automatic |
| Null types | ❌ No concept | ✅ `String?` vs `String` |

### Smart Casts
```kotlin
// Kotlin: automatic after type check
if (obj is String) {
    println(obj.length)  // Smart cast: obj is String
}

// Java 16+: pattern matching
if (obj instanceof String s) {
    println(s.length());
}
```

---

## 4. String Templates

| Feature | Java | Kotlin |
|---------|------|--------|
| Variable | `"Hello " + name` | `"Hello $name"` |
| Expression | `"Len: " + s.length()` | `"Len: ${s.length}"` |
| Multi-line | `"""..."""` (Java 15+) | `"""..."""` |
| Formatting | `String.format("%s", name)` | `"Hello $name"` |
| Raw strings | ❌ | `"""C:\path"""` |

### Examples
```kotlin
val name = "Kotlin"
val greeting = "Hello, $name!"                    // Simple
val info = "Name: $name, Length: ${name.length}"  // Expression
val raw = """
    This is a
    raw string with $name
""".trimIndent()
```

---

## 5. when Expression

| Feature | Java switch | Kotlin when |
|---------|-------------|-------------|
| Syntax | `switch (x) { case 1: ... }` | `when (x) { 1 -> ... }` |
| Multiple values | `case 1: case 2:` | `1, 2 ->` |
| Ranges | ❌ | `in 1..10 ->` |
| Type checks | Java 21+ | `is String ->` |
| Conditions | ❌ | `{ x > 0 } ->` |
| Returns value | Java 14+ | ✅ Always |
| Fall-through | Yes (break!) | No fall-through |

### when Without Argument
```kotlin
// Replaces if-else chains
val result = when {
    x < 0 -> "Negative"
    x == 0 -> "Zero"
    else -> "Positive"
}
```

---

## Quick Reference: Java → Kotlin

```java
// Java
final String name = "Kotlin";
int count = 0;
if (name != null) {
    System.out.println(name.length());
}
String greeting = "Hello, " + name + "!";
switch (day) {
    case 1: return "Monday";
    default: return "Other";
}
```

```kotlin
// Kotlin
val name = "Kotlin"
var count = 0
println(name?.length)  // or name?.let { println(it.length) }
val greeting = "Hello, $name!"
val dayName = when (day) {
    1 -> "Monday"
    else -> "Other"
}
```

---

## Key Things to Remember

1. **val is default** — use `var` only when reassignment is needed
2. **Types go after** — `val x: Type = value`
3. **Null safety is compile-time** — not runtime
4. **String templates are everywhere** — no more `+` concatenation
5. **when is an expression** — use it instead of if-else chains
6. **No fall-through in when** — use commas for multiple values
7. **Smart casts eliminate** explicit casting
8. **Platform types** from Java can be null — be careful!
