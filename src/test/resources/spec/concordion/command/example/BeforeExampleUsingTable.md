# Before Example with Table

When using a [before example](Example.html#before) which contains a table, all rows of the table are executed for each example.

### [Before](- "before")

| [-](- "incrementCounterBy(#num)") [Increment by](- "#num") |
|----|
| 2 |
| 5 |

### ~~Before~~

Outside of any named examples, the commands run in an anonymous outer example.

The before example runs before the outer example.

The counter is now [7](- "?=getCounter()").

### [Example 1](-)
The before example also runs before each named example.

Since the counter uses the default field scope, it is reset per example.

The counter is now [7](- "?=getCounter()").

### [Example 2](-)
The counter is now [7](- "?=getCounter()").