# Chemistry Parser

This is a project I created in 2020 as a proof of concept when I learned about BNF grammar and general language parsing. There was an article I had read about making a language parser in I think Javascript, but I can't remember the details.

What I do remember is working on this feverishly, without putting it to version control (idiotically), and being immensely proud of the result.

Here it is, split from its original single-file form into multiple class files as it should have been.

## The Grammer

expr   ::= atom { atom }
group  ::= wrap | elem
wrap   ::= "(" expr ")" | "[" expr "]" | "{" expr "}"
atom   ::= group [ count ]
count  ::= /[1-9][0-9]*/
elem   ::= /[A-Z]([a-z][a-z]?)?/

### Translation
- An `expr` is made up of one or more `atom`s in a row.
- A `group` represents either a `wrap` or an `elem`.
- A `wrap` is an `expr` wrapped in literal parens, brackets, or braces.
- An `atom` is a `group` with an optional `count`.
- A `count` is a number.
- An `elem` is an uppercase letter followed by up to 2 lowercase letters.

For example:

<img width="486" height="225" alt="image" src="https://github.com/user-attachments/assets/218c75dd-16de-4102-874c-77427cf1eeb3" />

- The "Mg", "O" and "H" parts are individual instances of `elem`.
- Each is then contained in a `group` without modification.
- Each is then contained in an `atom` without the optional `count`.
- The `atom`s of "OH" are then contained in an `expr`.
- That `expr` is then contained in a `wrap` with the surrounding "()".
- That `wrap` is then recontained in a `group` without modification.
- That `group` is then contained in an `atom` with the optional `count` of "2".
- Finally, the "Mg" `atom` and the "(OH)2" `atom` are contained together in the overall `expr`.

## TODO
- copy in the more detailed descriptions that I originally wrote.
