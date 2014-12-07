/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Users\\Administrator\\workspace\\远程服务\\src\\com\\zhangbz\\remoteservice\\IMiddlePerson.aidl
 */
package com.zhangbz.remoteservice;
public interface IMiddlePerson extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zhangbz.remoteservice.IMiddlePerson
{
private static final java.lang.String DESCRIPTOR = "com.zhangbz.remoteservice.IMiddlePerson";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zhangbz.remoteservice.IMiddlePerson interface,
 * generating a proxy if needed.
 */
public static com.zhangbz.remoteservice.IMiddlePerson asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zhangbz.remoteservice.IMiddlePerson))) {
return ((com.zhangbz.remoteservice.IMiddlePerson)iin);
}
return new com.zhangbz.remoteservice.IMiddlePerson.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_callMethodInService:
{
data.enforceInterface(DESCRIPTOR);
this.callMethodInService();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.zhangbz.remoteservice.IMiddlePerson
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	 * 调用服务里的方法
	 */
@Override public void callMethodInService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_callMethodInService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_callMethodInService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
	 * 调用服务里的方法
	 */
public void callMethodInService() throws android.os.RemoteException;
}
