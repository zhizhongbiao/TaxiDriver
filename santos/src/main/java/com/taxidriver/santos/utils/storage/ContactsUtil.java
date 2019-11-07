//package com.taxidriver.santos.utils;
//
//import android.content.ContentProviderOperation;
//import android.content.ContentProviderResult;
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.OperationApplicationException;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.RemoteException;
//import android.os.SystemClock;
//import android.provider.ContactsContract;
//import android.provider.ContactsContract.CommonDataKinds.Phone;
//import android.provider.ContactsContract.CommonDataKinds.StructuredName;
//import android.provider.ContactsContract.Contacts;
//import android.provider.ContactsContract.Data;
//import android.provider.ContactsContract.RawContacts;
//import android.text.TextUtils;
//import android.util.Log;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class ContactsUtil {
//
//    public final static boolean enable = true;
//    public static boolean addingContacts = false;
//    private static java.util.concurrent.locks.Lock mSqlLock = new java.util.concurrent.locks.ReentrantLock();
//
//    /**************************************************
//     * @Title : {@linkplain addPhoneNumber2ContactsProvider}
//     * @TODO  : {@linkplain 批量增添联系人}
//     * @eg :
//     * @param list
//     * @param ctx {@linkplain : @}
//     **************************************************/
//    public synchronized static void addPhoneNumber2ContactsProvider(final List<People> list, final Context ctx) {
//        if(!enable){
//            return;
//        }
//
//        try {
//            mSqlLock.lock();
//
//            Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider begin..." + list.size());
//            long startTime = SystemClock.elapsedRealtime();
//
//            if (null == list || list.size() == 0) {
//                Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider null == list || list.size() == 0, return");
//                return;
//            }
//
//            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//            int rawContactInsertIndex = 0;
//
//            for (int i = 0; i < list.size(); i++) {
//            	addingContacts = true;
//                rawContactInsertIndex = ops.size();// 这句好很重要，有了它才能给真正的实现批量添加。
//                //Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider, rawContactInsertIndex:" + rawContactInsertIndex);
//                //rawContactInsertIndex += 3;
//
//                ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
//                        .withValue(RawContacts.ACCOUNT_TYPE, null)
//                        .withValue(RawContacts.ACCOUNT_NAME, null)
//                        .withYieldAllowed(true).build());
//
//                ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
//                        .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
//                        .withValue(StructuredName.DISPLAY_NAME, list.get(i).getName())
//                        .withYieldAllowed(true).build());
//
//                ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
//                        .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
//                        .withValue(Phone.NUMBER, list.get(i).getNumber())
//                        .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
//                        .withYieldAllowed(true).build());
//
//                /*if (ops.size() >= 497) {
//                    try {
//                        Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider applyBatch" + ops.size());
//                        // 这里才调用的批量添加
//                        ctx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//
//                        ops.clear();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    } catch (OperationApplicationException e) {
//                        e.printStackTrace();
//                    }
//                }*/
//            }
//
//            try {
//                Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider applyBatch" + ops.size());
//                // 这里才调用的批量添加
//                ctx.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            } catch (OperationApplicationException e) {
//                e.printStackTrace();
//            }
//
//            long stopTime = SystemClock.elapsedRealtime();
//            Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider, lost:" + (stopTime - startTime));
//            Log.d("ContactsHelper", "addPhoneNumber2ContactsProvider, list.size():" + list.size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // TODO: handle finally clause
//            mSqlLock.unlock();
//            addingContacts = false;
//        }
//    }
//
//
//    /**
//     * 删除全部联系人
//     * @return
//     */
//    public synchronized static void delAllContacts(final Context context) {
//        if(!enable){
//            return;
//        }
//
//        Log.d("ContactsHelper", "delAllContacts begin...");
//        long startTime = SystemClock.elapsedRealtime();
//
//        try {
//            mSqlLock.lock();
//
//            Uri uri = null;
//            ContentProviderOperation op = null;
//
//            ContentResolver resolver = context.getContentResolver();
//            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//            int num = 0;// 删除影响的行数
//            resolver.delete(Uri.parse(RawContacts.CONTENT_URI.toString() + "?"
//                    + ContactsContract.CALLER_IS_SYNCADAPTER + "=true"),
//                    RawContacts._ID + ">0", null);
//
//            // 删除Data表的数据
//            uri = Uri.parse(Data.CONTENT_URI.toString() + "?" + ContactsContract.CALLER_IS_SYNCADAPTER + "=true");
//            op = ContentProviderOperation.newDelete(uri)
//                    .withSelection(Data.RAW_CONTACT_ID + ">0", null)
//                    .withYieldAllowed(true)
//                    .build();
//            ops.add(op);
//
//            // 删除RawContacts表的数据
//            uri = Uri.parse(RawContacts.CONTENT_URI.toString() + "?" + ContactsContract.CALLER_IS_SYNCADAPTER + "=true");
//            op = ContentProviderOperation.newDelete(RawContacts.CONTENT_URI)
//                    .withSelection(RawContacts._ID + ">0", null)
//                    .withYieldAllowed(true)
//                    .build();
//            ops.add(op);
//
//            // 删除Contacts表的数据
//            uri = Uri.parse(Contacts.CONTENT_URI.toString() + "?" + ContactsContract.CALLER_IS_SYNCADAPTER + "=true");
//            op = ContentProviderOperation.newDelete(uri)
//                    .withSelection(Contacts._ID + ">0", null)
//                    .withYieldAllowed(true)
//                    .build();
//            ops.add(op);
//
//            try {// 执行批量删除
//                ContentProviderResult[] results = resolver.applyBatch(ContactsContract.AUTHORITY, ops);
//                for (ContentProviderResult result : results) {
//                    num += result.count;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            Log.d("ContactsHelper", "delAllContacts end, num:" + num);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mSqlLock.unlock();
//
//            long stopTime = SystemClock.elapsedRealtime();
//            Log.d("ContactsHelper", "delAllContacts, lost:" + (stopTime - startTime));
//        }
//
//    }
//
//
//    /**
//     * 获取通讯录中联系人
//     */
//    public static int getContactCount(final Context context) {
//        if (!enable) {
//            return -1;
//        }
//
//        Log.d("ContactsHelper", "testGetContact begin...");
//        Cursor cursor = null;
//        int count = 0;
//
//        try {
//            mSqlLock.lock();
//
//            long startTime = SystemClock.elapsedRealtime();
//
//            ContentResolver contentResolver = context.getContentResolver();
//            Uri uri = Uri.parse("content://com.android.contacts/contacts");
//            cursor = contentResolver.query(uri, null, null, null, null);
//
//            int number = cursor.getCount();
//            Log.d("ContactsHelper", "testGetContact end, number:" + number);
//            return number;
//
//            /*while (cursor.moveToNext()) {
//                count++;
//
//                // 获取联系人姓名
//                StringBuilder sb = new StringBuilder();
//                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                sb.append("contactId=").append(contactId).append(",name=").append(name);
//
//                // 获取联系人手机号码
//                Cursor phones = contentResolver.query(
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
//
//                while (phones.moveToNext()) {
//                    String phone = phones.getString(phones.getColumnIndex("data1"));
//                    sb.append(",phone=").append(phone);
//                }
//
//                // 获取联系人email
//                Cursor emails = contentResolver.query(
//                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
//                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
//
//                while (emails.moveToNext()) {
//                    String email = emails.getString(emails.getColumnIndex("data1"));
//                    sb.append(",email=").append(email);
//                }
//            }
//
//            Log.d("ContactsHelper", "testGetContact end, count:" + count);
//
//            long stopTime = SystemClock.elapsedRealtime();
//            Log.d("ContactsHelper", "testGetContact, lost:" + (stopTime - startTime));*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != cursor) {
//                cursor.close();
//                cursor = null;
//            }
//            mSqlLock.unlock();
//        }
//
//        return count;
//    }
//
//
//
//
//    public static void queryContact(final Context context, String peopleName) {
//        if (!enable) {
//            return;
//        }
//
//        Log.d("ContactsHelper", "queryContact begin...");
//        Cursor cursor = null;
//        long startTime = SystemClock.elapsedRealtime();
//
//        try {
//            mSqlLock.lock();
//
//            ContentResolver contentResolver = context.getContentResolver();
//            Uri uri = Uri.parse("content://com.android.contacts/contacts");
//            cursor = contentResolver.query(uri, null, null, null, null);
//
//
//            while (cursor.moveToNext()) {
//                // 获取联系人姓名
//                StringBuilder sb = new StringBuilder();
//                String contactId = cursor.getString(cursor.getColumnIndex(Contacts._ID));
//                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
//
//                if(!TextUtils.isEmpty(name) && name.equals(peopleName)){
//                    sb.append("contactId=").append(contactId).append(",name=").append(name);
//
//                    // 获取联系人手机号码
//                    Cursor phones = contentResolver.query(
//                            Phone.CONTENT_URI, null,
//                            Phone.CONTACT_ID + " = " + contactId, null, null);
//
//                    while (phones.moveToNext()) {
//                        String phone = phones.getString(phones.getColumnIndex("data1"));
//                        sb.append(",phone=").append(phone);
//                    }
//
//                    phones.close();
//
//                    Log.d("ContactsHelper", "queryContact, txt:" + sb.toString());
//
//                    break;
//
//                    // 获取联系人email
//                    /*Cursor emails = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
//
//                    while (emails.moveToNext()) {
//                        String email = emails.getString(emails.getColumnIndex("data1"));
//                        sb.append(",email=").append(email);
//                    }*/
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != cursor) {
//                cursor.close();
//                cursor = null;
//            }
//            mSqlLock.unlock();
//
//            long stopTime = SystemClock.elapsedRealtime();
//            Log.d("ContactsHelper", "queryContact, lost:" + (stopTime - startTime));
//        }
//
//    }
//
//
//
//    /** 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
//     *  这是后面插入data表的数据，只有执行空值插入，才能使插入的联系人在通讯录里可见
//     */
//    public static void testInsert(Context context) {
//        ContentValues values = new ContentValues();
//        // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
//        Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values);
//        long rawContactId = ContentUris.parseId(rawContactUri);
//
//        // 往data表入姓名数据 values.clear();
//        values.put(Data.RAW_CONTACT_ID, rawContactId);
//        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
//        values.put(StructuredName.GIVEN_NAME, "zhangsan");
//        context.getContentResolver().insert(Data.CONTENT_URI, values);
//
//        // 往data表入电话数据
//        values.clear();
//        values.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
//        values.put(Phone.NUMBER, "5554");
//        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
//        context.getContentResolver().insert(Data.CONTENT_URI, values);
//
//        // 往data表入Email数据
//        /*values.clear();
//        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
//        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
//        values.put(Email.DATA, "ljq218@126.com");
//        values.put(Email.TYPE, Email.TYPE_WORK);*/
//
//        context.getContentResolver().insert(Data.CONTENT_URI, values);
//    }
//
//
//
//
//
//
//}
