package vortex.demo.smartu

import vortex.demo.smartu.util.{RandomConsumptionSimulator, ConsumptionSimulator}

package object prelude {
  val utilityName = Array[String] ("ELECTRICITY", "WATER", "GAS")
  def utilityKind2Name (kind: UtilityKind) = utilityName(kind.value)
  def newSimulator (kind: UtilityKind, profile: String): ConsumptionSimulator = RandomConsumptionSimulator
  def show (m: Meter): String = "(" + m.sn + ", " + utilityKind2Name (m.utility) + ", " + m.reading + ", " + m.error + ")"
  def show (idx: Index): String = "(" + idx.key + "," + idx.value +")"
  def show (ua: UtilityAnalytics): String = "(" + utilityKind2Name (ua.utility) + "," + ua.scope + ", (" + (ua.indexes.tail.foldRight(show(ua.indexes.head))((i: Index, s: String) => s + ", " + show(i))) + ")"
}
