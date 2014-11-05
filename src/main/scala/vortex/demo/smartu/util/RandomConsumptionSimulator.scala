package vortex.demo.smartu.util


object RandomConsumptionSimulator extends ConsumptionSimulator  {
  override def consumption: Float = Math.random().toFloat

  override def error: Float = (1/(1 + Math.random())).toFloat
}
