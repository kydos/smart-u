package vortex.demo.smartu;

/**
* vortex/demo/smartu/AnalyticsConfigHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../idl/smartu.idl
* Tuesday, November 4, 2014 9:49:18 PM CET
*/

public final class AnalyticsConfigHolder implements org.omg.CORBA.portable.Streamable
{
  public vortex.demo.smartu.AnalyticsConfig value = null;

  public AnalyticsConfigHolder ()
  {
  }

  public AnalyticsConfigHolder (vortex.demo.smartu.AnalyticsConfig initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = vortex.demo.smartu.AnalyticsConfigHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    vortex.demo.smartu.AnalyticsConfigHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return vortex.demo.smartu.AnalyticsConfigHelper.type ();
  }

}
