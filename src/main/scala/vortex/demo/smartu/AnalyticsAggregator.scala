package vortex.demo.smartu


import scala.languageFeature.implicitConversions._

import com.espertech.esper.client.{UpdateListener, EventBean}
import prelude._
import util.Config._
import dds.config.DefaultEntities.{defaultDomainParticipant,defaultPolicyFactory}
import dds.prelude._
import dds._
import vortex.demo.smartu.util.Esper
import vortex.demo.smartu.util.Esper._
import scala.collection.JavaConversions._

object AnalyticsAggregator {
  val usage = "USAGE:\n\tAnalyticsAggregator <utility [0=E|1=G|2=W]> <in-scope> <out-scope>"

  def main(args: Array[String]): Unit = {
    if (args.length >= 3) {
      val uk = UtilityKind.from_int(args(0).toInt)
      val inscope = args(1)
      val outscope = args(2)
      val at = Topic[UtilityAnalytics](utilityKind2Name(uk) +  analyticsTopicSuffix)

      val subQos = SubscriberQos().withPolicy(Partition(inscope))
      implicit val sub = Subscriber(subQos)

      val pubQos = PublisherQos().withPolicies(Partition(outscope))
      implicit val pub = Publisher(pubQos)

      val dr = DataReader[UtilityAnalytics](sub, at)
      val dw = DataWriter[UtilityAnalytics](pub, at)
      val idxs = Array[Index](new Index("agrAvg", 0), new Index("usrMax", 0), new Index("usrMin", 0))
      val ua = new UtilityAnalytics(outscope, uk, idxs)

      dr listen {
        case DataAvailable(_) => {

          val data = dr.read.toList.map(s => { val d = s.getData; (d.indexes(0).value, d.indexes(1).value, d.indexes(2).value)})
          idxs(0).value = data.map(_._1).foldRight(0F)(_ + _)
          idxs(1).value = data.map(_._2).max
          idxs(2).value = data.map(_._3).min
          dw.write(ua)
        }
      }
    }
    else
      println(usage)
  }
}

