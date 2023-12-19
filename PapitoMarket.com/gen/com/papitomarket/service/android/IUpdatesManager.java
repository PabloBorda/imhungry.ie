/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/pablotomasborda/Dropbox/Projects/android/imhungry.ie/PapitoMarket.com/src/com/papitomarket/service/android/IUpdatesManager.aidl
 */
package com.papitomarket.service.android;
public interface IUpdatesManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.papitomarket.service.android.IUpdatesManager
{
private static final java.lang.String DESCRIPTOR = "com.papitomarket.service.android.IUpdatesManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.papitomarket.service.android.IUpdatesManager interface,
 * generating a proxy if needed.
 */
public static com.papitomarket.service.android.IUpdatesManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.papitomarket.service.android.IUpdatesManager))) {
return ((com.papitomarket.service.android.IUpdatesManager)iin);
}
return new com.papitomarket.service.android.IUpdatesManager.Stub.Proxy(obj);
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
case TRANSACTION_sendNotification:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.sendNotification(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onReceivedNotification:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.onReceivedNotification(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getNetworkLocation:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getNetworkLocation();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getGPSLocation:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getGPSLocation();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.papitomarket.service.android.IUpdatesManager
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
@Override public boolean sendNotification(java.lang.String to, java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(to);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendNotification, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onReceivedNotification(java.lang.String from, java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(from);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_onReceivedNotification, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getNetworkLocation() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNetworkLocation, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getGPSLocation() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getGPSLocation, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_sendNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onReceivedNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getNetworkLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getGPSLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public boolean sendNotification(java.lang.String to, java.lang.String message) throws android.os.RemoteException;
public boolean onReceivedNotification(java.lang.String from, java.lang.String message) throws android.os.RemoteException;
public java.lang.String getNetworkLocation() throws android.os.RemoteException;
public java.lang.String getGPSLocation() throws android.os.RemoteException;
}
