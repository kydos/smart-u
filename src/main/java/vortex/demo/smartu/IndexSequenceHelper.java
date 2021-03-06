package vortex.demo.smartu;


/**
* vortex/demo/smartu/IndexSequenceHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../idl/smartu.idl
* Tuesday, November 4, 2014 9:49:18 PM CET
*/

abstract public class IndexSequenceHelper
{
  private static String  _id = "IDL:vortex/demo/smartu/IndexSequence:1.0";

  public static void insert (org.omg.CORBA.Any a, vortex.demo.smartu.Index[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static vortex.demo.smartu.Index[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = vortex.demo.smartu.IndexHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (vortex.demo.smartu.IndexSequenceHelper.id (), "IndexSequence", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static vortex.demo.smartu.Index[] read (org.omg.CORBA.portable.InputStream istream)
  {
    vortex.demo.smartu.Index value[] = null;
    int _len0 = istream.read_long ();
    value = new vortex.demo.smartu.Index[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = vortex.demo.smartu.IndexHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, vortex.demo.smartu.Index[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      vortex.demo.smartu.IndexHelper.write (ostream, value[_i0]);
  }

}
