package vortex.demo.smartu

import prelude._
import util.Config._
import dds.config.DefaultEntities.{defaultDomainParticipant,defaultPolicyFactory}
import dds.prelude._
import dds._


object MeterSimulator {
  val usage = "USAGE:\n\tMeter <sn> <utility [0=E|1=G|2=W]> <usage-profile [low|med|high]> <scope> <period-msec>"

  def main(args: Array[String]): Unit = {
    if (args.length >= 5) {
      val sn = args(0)
      val uk = UtilityKind.from_int(args(1).toInt)
      val profile = args(2)
      val scope = args(3)
      val period = args(4).toInt
      val simulator = newSimulator(uk, profile)


      val topic = Topic[Meter](utilityKind2Name(uk) + meterTopicSuffix)
      val pubQos = PublisherQos().withPolicy(Partition(scope))
      implicit val pub = Publisher(pubQos)

      val dw = DataWriter[Meter](pub, topic)

      // produce readings, forever...
      val meter = new Meter(sn, uk, 0, 0);

      while (true) {
        meter.reading = simulator.consumption
        meter.error = simulator.error
        dw.write(meter)
        print("#")
        Thread.sleep(period)
      }
    }
    else {
      println(usage)
    }
  }
}
