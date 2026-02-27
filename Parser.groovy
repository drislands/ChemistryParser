class Parser {
  def nextTokenIndex = 0
  def tokenList = []
  
  Parser(text) {
    validate(text)
    
    def reToken = Token.Kind.values()*.pattern.join('|')
    def match = text =~ reToken
    
    def idx = 0
    match.each { t ->
      tokenList << new Token(t[0],idx++)
    }
  }
  
  void validate(text) {
    if(text =~ /(?<![A-Z][a-z]?)[a-z]/)   throw new IllegalArgumentException()
    def parens = []
    def count = 0
    for(char c : text) {
      try {
      switch(c) {
        case '(':
        case '[':
        case '{':
          count++
          parens.push(c)
          break
        case ')':
          if(parens.pop() != '(') throw new IllegalArgumentException()
          count--
          break
        case ']':
          if(parens.pop() != '[') throw new IllegalArgumentException()
          count--
          break
        case '}':
          if(parens.pop() != '{') throw new IllegalArgumentException()
          count--
          break
        default:
          break
      }
      } catch(NoSuchElementException ignored) {
        throw new IllegalArgumentException()
      }
    }
    if(count) throw new IllegalArgumentException()
  }
  
  def parse() {
    parseExpr()
  }
  
  boolean nextIsOpen() {
    if(tokenList[nextTokenIndex]?.kind == Token.Kind.PAREN &&
        tokenList[nextTokenIndex].text =~ ~/[\[({]/) {
      nextTokenIndex++
      return true
    } else {
      return false
    }
  }
  
  boolean nextIsCount() {
    return tokenList[nextTokenIndex]?.kind == Token.Kind.COUNT
  }
  
  boolean nextIsAtom() {
    return tokenList[nextTokenIndex] && !(tokenList[nextTokenIndex].kind == Token.Kind.PAREN &&
        tokenList[nextTokenIndex].text =~ ~/[\])}]/)
  }
  
  Expr parseExpr() {
    // expr ::= atom { atom }
    def expr = new Expr(parseAtom())
    
    while(nextIsAtom()) {
      expr.atoms << parseAtom()
    }
    
    expr
  }
  
  Group parseGroup() {
    // group ::= wrap | elem
    if(nextIsOpen()) {
      parseWrap()
    } else {
      parseElem()
    }
  }
  
  Wrap parseWrap() {
    // wrap ::= "(" expr ")" | "[" expr "]" | "{" expr "}"
    def wrap = new Wrap(parseExpr())
    nextTokenIndex++
    wrap
  }
  
  Atom parseAtom() {
    // atom ::= group [ count ]
    def group = parseGroup()
    int count = 1
    if(nextIsCount()) {
      count = parseCount()
    }
    new Atom(group,count)
  }
  
  int parseCount() {
    // count ::= /[1-9][0-9]*/
    tokenList[nextTokenIndex++].text as int
  }
  
  Elem parseElem() {
    // elem ::= /[A-Z]([a-z][a-z]?)?/
    new Elem(tokenList[nextTokenIndex++].text)
  }
}
