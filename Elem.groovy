class Elem extends Group {
  String elem
  
  Elem(String elem) {
    this.elem = elem
  }
  
  def eval() {
    [(elem):1]
  }
}
