"""
# BNF Grammar -- element parsing
# "Mg(OH)2"
# "K4[ON(SO3)2]2"

expr   ::= atom { atom }
group  ::= wrap | elem
wrap   ::= "(" expr ")" | "[" expr "]" | "{" expr "}"
atom   ::= group [ count ]
count  ::= /[1-9][0-9]*/
elem   ::= /[A-Z]([a-z][a-z]?)?/
"""

// Sample inputs
def inputs = [
  "Mg(OH)2"       : ['Mg':1,'O':2,'H':2],
  "K4[ON(SO3)2]2" : ['K':4,'O':14,'N':2,'S':4],
  "H2O"           : ['H':2,'O':1],
  "Mg(OH]2"       : null,
  "MgOH)2"        : null,
  "pie"           : null
]





// Run the parser logic on the input
inputs.each { formula,result ->
  if(result) { new Parser(formula).parse().eval() == result }
  else {
    try {
      new Parser(formula).parse().eval()
      assert false
    } catch(IllegalArgumentException ignored) {
      assert true
    }
  }
}
