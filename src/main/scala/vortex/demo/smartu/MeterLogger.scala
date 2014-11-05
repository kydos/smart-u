package vortex.demo.smartu

import prelude._
import util.Config._
import dds.config.DefaultEntities.{defaultDomainParticipant,defaultPolicyFactory}
import dds.prelude._
import dds._
import scala.collection.JavaConversions._

object MeterLogger {
  val usage = "USAGE:\n\tMeterLogger <utility [0=E|1=G|2=W]> <scope>"

  def main(args: Array[String]): Unit = {
    if (args.length >= 2) {
      val uk = UtilityKind.from_int(args(0).toInt)
      val scope = args(1)
      val topic = Topic[Meter](utilityKind2Name(uk) + meterTopicSuffix)

      val subQos = SubscriberQos().withPolicy(Partition(scope))
      implicit val sub = Subscriber(subQos)

      val dr = DataReader[Meter](sub, topic)

      dr listen {
        case DataAvailable(_) => {
          dr.read.foreach(s => println(show(s.getData)))
        }
      }
    }
    else
      println(usage)
  }
}
