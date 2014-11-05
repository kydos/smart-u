package vortex.demo.smartu

import dds._
import dds.prelude._
import dds.config.DefaultEntities.{defaultDomainParticipant,defaultPolicyFactory}

import vortex.demo.smartu.prelude._
import vortex.demo.smartu.util.Config._

import scala.collection.JavaConversions._
object AnalyticsLogger {
  val usage = "USAGE:\n\tAnalyticsLogger <utility [0=E|1=G|2=W]> <scope>"

  def main(args: Array[String]): Unit = {
    if (args.length >= 2) {
      val uk = UtilityKind.from_int(args(0).toInt)
      val scope = args(1)
      val at = Topic[UtilityAnalytics](utilityKind2Name(uk) +  analyticsTopicSuffix)

      val subQos = SubscriberQos().withPolicy(Partition(scope))
      implicit val sub = Subscriber(subQos)

      val dr = DataReader[UtilityAnalytics](sub, at)


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

