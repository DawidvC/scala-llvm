/* NEST (New Scala Test)
 * Copyright 2007-2011 LAMP/EPFL
 */

package scala.tools

import java.io.{ File => JFile }
import nsc.io.{ Path, Directory }
import util.{ PathResolver }
import nsc.Properties.{ propOrElse, propOrNone, propOrEmpty }
import scala.sys.process.javaVmArguments

package object partest { 
  import nest.NestUI
  
  implicit private[partest] def temporaryPath2File(x: Path): JFile = x.jfile
  implicit private[partest] def temporaryFile2Path(x: JFile): Path = Path(x)
  
  def basename(name: String): String = Path(name).stripExtension

  def resultsToStatistics(results: Iterable[(_, Int)]): (Int, Int) = {
    val (files, failures) = results map (_._2 == 0) partition (_ == true)
    (files.size, failures.size)
  }
  
  def vmArgString = javaVmArguments.mkString(
    "Java VM started with arguments: '",
    " ",
    "'"
  )
  
  def allPropertiesString = {
    import collection.JavaConversions._
    System.getProperties.toList.sorted map { case (k, v) => "%s -> %s\n".format(k, v) } mkString
  }
  
  def showAllJVMInfo {
    NestUI.verbose(vmArgString)
    NestUI.verbose(allPropertiesString)
  }

  def isPartestDebug: Boolean =
    propOrEmpty("partest.debug") == "true"
}
