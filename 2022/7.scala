trait Node {
  val name: String
  val size: Int
  def prettyPrint(depth: Int): Unit
}

case class File(name: String, size: Int) extends Node {
  def prettyPrint(depth: Int = 0): Unit = println(s"${"  "*depth}- $name (file, size=$size)")
}

case class Directory(name: String, children: Seq[Node]) extends Node {
  val size = children.map(_.size).sum
  def prettyPrint(depth: Int = 0): Unit = {
    println(s"${"  "*depth}- $name (dir)")
    children.foreach(_.prettyPrint(depth + 1))
  }

  def directories: Seq[Directory] = {
    this +: children.flatMap {
      case child: Directory => child.directories
      case _ => Seq()
    }
  }

  def withChild(path: String, new_child: Node): Directory = {
    val head = path.split("/").head
    val tail = path.split("/").tail.mkString("/")
    val new_children = children.map { child =>
      if (child.name == head) child.asInstanceOf[Directory].withChild(tail, new_child)
      else child
    }
    Directory(name, if (head.isEmpty) new_children :+ new_child else new_children)
  }
}

@main
def main() = {

  val (filesystem, _) = io.Source.fromFile("7.txt").getLines().drop(1).foldLeft((Directory("/", Seq()), "/")) { case ((cfs, cwd), output) =>
    output match {
      case s"$$ cd $dir" => (cfs,
        if (dir.startsWith("/")) {
          dir
        } else if (dir == "..") {
          cwd.split("/").dropRight(1).mkString("/")
        } else {
          s"${cwd.stripSuffix("/")}/$dir"
        }
      )
      case "$ ls" => (cfs, cwd)
      case s"dir $name" => (cfs.withChild(cwd.stripPrefix("/"), Directory(name, Seq())), cwd)
      case s"$size $name" => (cfs.withChild(cwd.stripPrefix("/"), File(name, size.toInt)), cwd)
    }
  }

  // filesystem.prettyPrint()
  println(filesystem.directories.map(_.size).filter(_ <= 100000).sum)

  val total_space = 70000000
  val needed_space = 30000000
  println(filesystem.directories.sortBy(_.size).find(_.size >= needed_space + filesystem.size - total_space).get.size)
}
