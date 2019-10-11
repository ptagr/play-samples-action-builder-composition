// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/punit/Downloads/play-samples-play-scala-hello-world-tutorial/conf/routes
// @DATE:Fri Oct 11 11:21:06 PDT 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
