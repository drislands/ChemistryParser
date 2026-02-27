class Wrap extends Group {
  Expr expr
  
  Wrap(Expr expr) {
    this.expr = expr
  }
  
  def eval() {
    expr.eval()
  }
}
