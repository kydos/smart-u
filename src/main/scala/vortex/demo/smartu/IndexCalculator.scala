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

object IndexCalculator {
  val usage = "USAGE:\n\tIndexCalculator <utility [0=E|1=G|2=W]> <in-scope> <out-scope>"

  def main(args: Array[String]): Unit = {
    if (args.length >= 3) {
      val uk = UtilityKind.from_int(args(0).toInt)
      val inscope = args(1)
      val outscope = args(2)
      val mt = Topic[Meter](utilityKind2Name(uk) +  meterTopicSuffix)
      val at = Topic[UtilityAnalytics](utilityKind2Name(uk) +  analyticsTopicSuffix)

      val subQos = SubscriberQos().withPolicy(Partition(inscope))
      implicit val sub = Subscriber(subQos)

      val pubQos = PublisherQos().withPolicies(Partition(outscope))
      implicit val pub = Publisher(pubQos)

      val dr = DataReader[Meter](sub, mt)
      val dw = DataWriter[UtilityAnalytics](pub, at)

      val esper = Esper()
      esper.registerTopic(mt)

      val avgeql = "select max(reading) as usrMax, sum(reading)/10 as agrAvg, min(reading) as usrMin  from vortex.demo.smartu.Meter.win:time(10 sec)"
      val avgcmd = esper.statement(avgeql)

      val idxs = Array[Index](new Index("agrAvg", 0), new Index("usrMax", 0), new Index("usrMin", 0))
      val ua = new UtilityAnalytics(outscope, uk, idxs)

      avgcmd.addListener {
        (ne: Array[EventBean], oe: Array[EventBean]) => {
          try {
            (0 to (idxs.length - 1)).foreach(i => idxs(i).value = (ne(0).get(idxs(i).key).toString.toFloat))
          } catch {
            case t: Throwable => {
              t.printStackTrace()
            }
          }
          dw.write(ua)
        }
      }

      dr listen {
        case DataAvailable(_) => {
          dr.take.foreach(s => esper.push(s.getData))
        }
      }
    }
    else
      println(usage)
  }
}
