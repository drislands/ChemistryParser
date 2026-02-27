class Expr extends Item {
  List<Atom> atoms
  Expr(Atom first) {
    atoms = [first]
  }
  
  def eval() {
    def results = [:]
    atoms*.eval()*.keySet().flatten().unique().each { key ->
      def count = 0
      atoms*.eval().each { atom ->
        if(atom[key]) count += atom[key]
      }
      results[key] = count
    }
    
    results
  }
}
