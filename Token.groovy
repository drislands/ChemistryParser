class Token {
  String text
  int index
  Kind kind
  
  Token(text,index) {
    this.text = text
    this.index = index
    kind = Kind.values().find {
      it.pattern && this.text ==~ it.pattern
    }
  }
  
  enum Kind {
    PAREN(/[\[\](){}]/),
    COUNT(/[1-9][0-9]*/),ELEM(/[A-Z]([a-z][a-z]?)?/)
    
    final String pattern
    
    Kind(pattern=null) {
      this.pattern = pattern
    }
  }
}
