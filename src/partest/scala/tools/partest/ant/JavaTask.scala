/*                     __                                               *\
**     ________ ___   / /  ___     Scala Parallel Testing               **
**    / __/ __// _ | / /  / _ |    (c) 2007-2010, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.tools
package partest
package ant

import org.apache.tools.ant.Task
import org.apache.tools.ant.taskdefs.Java
import org.apache.tools.ant.types.{ EnumeratedAttribute, Commandline, Environment, PropertySet }

import scala.tools.nsc.io._
import scala.tools.nsc.util.{ ClassPath, CommandLineSpec }
import CommandLineSpec._

class JavaTask extends Java {
  override def getTaskName()      = "partest"
  private val scalaRunnerClass    = "scala.tools.nsc.MainGenericRunner"  

  protected def rootDir           = prop("partest.rootdir") getOrElse (baseDir / "test").path
  protected def partestJVMArgs    = prop("partest.jvm.args") getOrElse "-Xms64M -Xmx768M -Xss768K -XX:MaxPermSize=96M"
  protected def runnerArgs        = List("-usejavacp", "scala.tools.partest.Runner", "--javaopts", partestJVMArgs)

  private def baseDir             = Directory(getProject.getBaseDir)
  private def prop(s: String)     = Option(getProject getProperty s)
  private def jvmline(s: String)  = returning(createJvmarg())(_ setLine s)
  private def addArg(s: String)   = returning(createArg())(_ setValue s)
  
  private def newKeyValue(key: String, value: String) =
    returning(new Environment.Variable)(x => { x setKey key ; x setValue value })

  def setDefaults() {
    setFork(true)
    getProject.setSystemProperties()
    setClassname(scalaRunnerClass)
    // setDir(Path(rootDir).jfile)
    // addSyspropertyset(partestPropSet)
    addSysproperty(newKeyValue("partest.is-in-ant", "true"))
    jvmline(partestJVMArgs)
    runnerArgs foreach addArg
  }
  
  override def execute() {
    setDefaults()
    super.execute()
  }
}
