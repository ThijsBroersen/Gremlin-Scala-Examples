
object MavenCache extends App {
  import gremlin.scala._
  import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory
  implicit val graph = TinkerFactory.createModern.asScala

  case class SemVer(major: Int, minor: Int, patch: Int)

  object SemVer {
    def unsafeParse(semVer: String): SemVer = semVer match {
      case s"$major.$minor.$patch" =>
        SemVer(major.toInt, minor.toInt, patch.toInt)
    }
  }

  val Version = Key[SemVer]("version")
  val DependsOn = Key[SemVer]("dependsOn")

  val cats = graph.addVertex("org.typelevel:cats-core")
  val fs2 = graph.addVertex("co.fs2:fs2-core")

  private val relation = List(
    Version.of(SemVer.unsafeParse("3.10.1")),
    DependsOn.of(SemVer.unsafeParse("2.7.0"))
  )

  fs2 --- ("dependency", relation: _*) --> cats

  println(fs2.outE("release").select(StepLabel[Vertex]()).toList())

}
