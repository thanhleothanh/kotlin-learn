/**
 * Day 4 - Scope Functions in Kotlin
 *
 * Kotlin has 5 scope functions, each with different use cases:
 *
 * 1. let    — null checks + transformations (receiver: it, returns: lambda result)
 * 2. run    — object configuration + result (receiver: this, returns: lambda result)
 * 3. apply  — object configuration (receiver: this, returns: this)
 * 4. also   — side effects + logging (receiver: it, returns: this)
 * 5. with   — multiple operations (receiver: this, returns: lambda result)
 */

data class Person(var name: String, var age: Int, var email: String?)

// ═══════════════════════════════════════════════════════════════
// 1. LET — null checks + transformations
// ═══════════════════════════════════════════════════════════════

// let: receiver is "it", returns lambda result
// Use for: null checks, transformations, scoped operations

fun letExample() {
    // Null check
    val name: String? = "Kotlin"
    val length = name?.let {
        println("Name is: $it")  // "it" is the non-null value
        it.length  // Return value
    } ?: 0
    println("Length: $length")

    // Transformation
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = numbers.let {
        println("Processing: $it")
        it.sum()  // Return value
    }
    println("Sum: $sum")

    // Chained transformations
    val result = "Hello World".let {
        it.uppercase()
    }.let {
        it.reversed()
    }.let {
        "Result: $it"
    }
    println(result)

    // Java equivalent:
    // if (name != null) {
    //     System.out.println("Name is: " + name);
    //     return name.length();
    // }
    // return 0;
}

// ═══════════════════════════════════════════════════════════════
// 2. RUN — object configuration + result
// ═══════════════════════════════════════════════════════════════

// run: receiver is "this", returns lambda result
// Use for: object configuration + computing result

fun runExample() {
    // Object configuration with result
    val person = Person("Alice", 25, null)
    val description = person.run {
        name = name.uppercase()  // "this" is person
        age += 10
        email = "$name@company.com"
        "Person: $name, Age: $age"  // Return value
    }
    println(description)

    // Run on nullable
    val nullablePerson: Person? = null
    val result = nullablePerson?.run {
        "Name: ${name.uppercase()}"
    } ?: "No person"
    println(result)

    // Java equivalent:
    // person.name = person.name.toUpperCase();
    // person.age += 10;
    // person.email = person.name + "@company.com";
    // String description = "Person: " + person.name + ", Age: " + person.age;

    // String result = nullablePerson != null ?
    //     "Name: " + nullablePerson.name.toUpperCase() :
    //     "No person";
}

// ═══════════════════════════════════════════════════════════════
// 3. APPLY — object configuration (returns this)
// ═══════════════════════════════════════════════════════════════

// apply: receiver is "this", returns the object itself
// Use for: configuring objects (build pattern)

fun applyExample() {
    // Configure and return the same object
    val person = Person("", 0, null).apply {
        name = "Bob"        // "this" is the person
        age = 30
        email = "bob@email.com"
    }  // Returns the configured person!
    println(person)

    // Chain configuration
    val list = mutableListOf<Int>().apply {
        add(1)
        add(2)
        add(3)
        add(4)
    }
    println("List: $list")

    // Configure with validation
    val config = mutableMapOf<String, Any>().apply {
        put("host", "localhost")
        put("port", 8080)
        put("debug", true)
    }
    println("Config: $config")

    // Java equivalent:
    // Person person = new Person("", 0, null);
    // person.name = "Bob";
    // person.age = 30;
    // person.email = "bob@email.com";
    // // Must use separate statements, no chaining!
}

// ═══════════════════════════════════════════════════════════════
// 4. ALSO — side effects + logging
// ═══════════════════════════════════════════════════════════════

// also: receiver is "it", returns the object itself
// Use for: logging, side effects, debugging

fun alsoExample() {
    // Log while processing
    val numbers = mutableListOf(1, 2, 3).also {
        println("Original: $it")  // Log before modification
    }.apply {
        add(4)
        add(5)
    }.also {
        println("After add: $it")  // Log after modification
    }
    println("Final: $numbers")

    // Validate while building
    val person = Person("", 0, null).also {
        require(it.name.isNotBlank()) { "Name cannot be blank" }
        require(it.age >= 0) { "Age cannot be negative" }
    }

    // Debugging
    val result = "Hello World".also {
        println("Processing: '$it'")
    }.uppercase().also {
        println("After uppercase: '$it'")
    }
    println(result)

    // Java equivalent:
    // System.out.println("Original: " + numbers);
    // numbers.add(4);
    // numbers.add(5);
    // System.out.println("After add: " + numbers);
}

// ═══════════════════════════════════════════════════════════════
// 5. WITH — multiple operations on object
// ═══════════════════════════════════════════════════════════════

// with: receiver is "this", returns lambda result
// Use for: multiple operations on non-nullable object

fun withExample() {
    // Multiple operations without repeating object name
    val person = Person("Charlie", 25, "charlie@email.com")
    val info = with(person) {
        """
        Name: ${name.uppercase()}
        Age: $age
        Email: ${email?.lowercase()}
        Is adult: ${age >= 18}
        """.trimIndent()
    }
    println(info)

    // With on list
    val numbers = listOf(1, 2, 3, 4, 5)
    val summary = with(numbers) {
        """
        Size: $size
        First: ${first()}
        Last: ${last()}
        Sum: ${sum()}
        Average: ${average()}
        """.trimIndent()
    }
    println(summary)

    // Java equivalent:
    // String info = "Name: " + person.name.toUpperCase() +
    //     "\nAge: " + person.age +
    //     "\nEmail: " + person.email.toLowerCase();
}

// ═══════════════════════════════════════════════════════════════
// 6. COMPARISON TABLE
// ═══════════════════════════════════════════════════════════════

//   Function  Receiver  Returns    Use Case
//   ────────  ────────  ─────────  ──────────────────────────────
//   let       it        result    Null checks, transformations
//   run       this      result    Object config + result
//   apply     this      this      Object configuration (build)
//   also      it        this      Side effects, logging
//   with      this      result    Multiple operations

// ═══════════════════════════════════════════════════════════════
// 7. PRACTICAL EXAMPLES
// ═══════════════════════════════════════════════════════════════

fun practicalExamples() {
    // Example 1: Configure Android View (common pattern)
    // button.apply {
    //     text = "Click me"
    //     setOnClickListener { onClick() }
    //     setTextColor(Color.WHITE)
    // }

    // Example 2: Parse JSON with null check
    val jsonString: String? = """{"name": "Alice", "age": 25}"""
    val name = jsonString?.let {
        // Parse JSON (simplified)
        it.substringAfter("\"name\": \"").substringBefore("\"")
    } ?: "Unknown"
    println("Parsed name: $name")

    // Example 3: Chain operations
    val result = "  Hello World  "
        .also { println("Before trim: '$it'") }
        .trim()
        .also { println("After trim: '$it'") }
        .uppercase()
        .also { println("After uppercase: '$it'") }
        .split(" ")
        .joinToString("-")
        .also { println("Final: '$it'") }

    // Example 4: Build map
    val map = mutableMapOf<String, Any>().apply {
        put("name", "Bob")
        put("age", 30)
        put("active", true)
    }.also {
        println("Built map: $it")
    }

    // Example 5: Run on scope
    val length = "Kotlin".run {
        println("Processing: $this")
        length * 2  // Return value
    }
    println("Doubled length: $length")
}

// ═══════════════════════════════════════════════════════════════
// MAIN FUNCTION
// ═══════════════════════════════════════════════════════════════

fun main() {
    println("=== LET ===")
    letExample()

    println("\n=== RUN ===")
    runExample()

    println("\n=== APPLY ===")
    applyExample()

    println("\n=== ALSO ===")
    alsoExample()

    println("\n=== WITH ===")
    withExample()

    println("\n=== PRACTICAL ===")
    practicalExamples()

    // ═══════════════════════════════════════════════════════════════
    // QUICK REFERENCE
    // ═══════════════════════════════════════════════════════════════
    //
    // let:   obj?.let { println(it); it.length }
    // run:   obj.run { println(this); this.length }
    // apply: obj.apply { this.field = value }
    // also:  obj.also { println(it) }
    // with:  with(obj) { this.field1; this.field2 }
    //
    // ═══════════════════════════════════════════════════════════════
}
