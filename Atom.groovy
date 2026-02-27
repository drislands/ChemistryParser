class Atom extends Item {
  Group group
  int count
  
  Atom(Group group,count=1) {
    this.group = group
    this.count = count
  }
  
  def eval() {
    group.eval().collectEntries {
      [(it.key):it.value * count]
    }
  }
}
