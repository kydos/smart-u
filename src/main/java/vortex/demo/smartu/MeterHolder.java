package vortex.demo.smartu;

/**
* vortex/demo/smartu/MeterHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../idl/smartu.idl
* Tuesday, November 4, 2014 9:49:18 PM CET
*/

public final class MeterHolder implements org.omg.CORBA.portable.Streamable
{
  public vortex.demo.smartu.Meter value = null;

  public MeterHolder ()
  {
  }

  public MeterHolder (vortex.demo.smartu.Meter initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = vortex.demo.smartu.MeterHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    vortex.demo.smartu.MeterHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return vortex.demo.smartu.MeterHelper.type ();
  }

}
