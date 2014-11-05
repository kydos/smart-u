package vortex.demo.smartu.util

import scala.languageFeature.implicitConversions._
import com.espertech.esper.client._

import org.omg.dds.topic.Topic

object Esper {
  implicit class CommandListenerHelper(val fun: (Array[EventBean], Array[EventBean]) => Any) extends UpdateListener {
    def update(ne: Array[EventBean], oe: Array[EventBean])  = fun(ne, oe)
  }

  def apply() = new Esper()
}

class Esper {
  lazy val  config = new Configuration
  lazy val ddsConf = createDDSConf()
  lazy val cep: EPServiceProvider = EPServiceProviderManager.getDefaultProvider(config)
  lazy val runtime = cep.getEPRuntime

  private def createDDSConf(): ConfigurationEventTypeLegacy = {
    val ddsConf = new ConfigurationEventTypeLegacy
    ddsConf.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC)
    ddsConf
  }

  def registerTopic[T](topic: Topic[T]): this.type = {
    val cls = topic.getTypeSupport.getType
    config.addEventType(cls.getName(), cls.getName(), ddsConf)
    println("Registered Topic: " + cls.getName())
    this
  }

  def statement(stm: String) = cep.getEPAdministrator.createEPL(stm)

  def push(e: Any)  = {
    runtime.sendEvent(e)
    runtime
  }
}
