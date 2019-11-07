//package com.taxidriver.santos.third_party.sql;
//
//import android.text.TextUtils;
//
//import com.taxidriver.santos.app.MyApp;
//import com.taxidriver.santos.utils.log.LogUtils;
//
//
///**
// * FileName :  ObjBoxHelper
// * Author   :  zhizhongbiao
// * Date     :  2018/11/26
// * Describe :
// */
//
//public class ObjBoxHelper {
//
//
//    //运行后默认的数据库位置在：/data/data/包名/files/objectbox/data.mdb下
//
//    private static ObjBoxHelper instance;
//
//    private ObjBoxHelper() {
//    }
//
//    public static ObjBoxHelper getInst() {
//        if (instance == null) {
//            synchronized (ObjBoxHelper.class) {
//                if (instance == null) {
//                    instance = new ObjBoxHelper();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public long getMacListSize() {
//        Box<BtMacEntity> macEntityBox = MyApp.getInstence().getBoxStore().boxFor(BtMacEntity.class);
//        long count = macEntityBox.count();
//        LogUtils.e("getMacListSize  MacListSize=" + count);
//        return count;
//    }
//
//    public BtMacEntity getMac(String macAddr) {
//        BtMacEntity macEntity = new BtMacEntity();
//        if (TextUtils.isEmpty(macAddr)) {
//            LogUtils.e("getMac    macAddr=" + macAddr);
//            return macEntity;
//        }
//
//
//        try {
//            Box<BtMacEntity> macEntityBox = MyApp.getInstence().getBoxStore().boxFor(BtMacEntity.class);
//            macEntity = macEntityBox.query().equal(BtMacEntity_.macAddr, macAddr).build().findUnique();
//        } catch (NonUniqueResultException e) {
//            LogUtils.e("getMac  macAddr=" + macAddr + "   NonUniqueResultException=" + e.getMessage());
//        }
//
//        LogUtils.e("getMac  macAddr=" + macAddr + "   macEntity=" + macEntity);
//        return macEntity;
//    }
//
//    public void addMac(BtMacEntity btMacEntity) {
//        Box<BtMacEntity> macEntityBox = MyApp.getInstence().getBoxStore().boxFor(BtMacEntity.class);
//        long putResult = macEntityBox.put(btMacEntity);
//        LogUtils.e("addMac  btMacEntity=" + btMacEntity + "   ----  putResult=" + putResult);
//    }
//
//    public void removeMac(String macAddr) {
//        LogUtils.e("removeMac  macAddr=" + macAddr);
//        BtMacEntity mac = getMac(macAddr);
//        removeMac(mac);
//    }
//
//    public void removeMac(BtMacEntity mac) {
//        LogUtils.e("removeMac  mac=" + mac);
//        if (mac == null) return;
//        Box<BtMacEntity> macEntityBox = MyApp.getInstence().getBoxStore().boxFor(BtMacEntity.class);
//        clearContact(mac.macAddr);
//        macEntityBox.remove(mac);
//    }
//
//
//    public void removeOldestMac() {
//        Box<BtMacEntity> macEntityBox = MyApp.getInstence().getBoxStore().boxFor(BtMacEntity.class);
//        List<BtMacEntity> all = macEntityBox.getAll();
//        long t = System.currentTimeMillis();
//        BtMacEntity temp = all.isEmpty() ? null : all.get(0);
//        for (BtMacEntity btMacEntity : all) {
//            if (t > btMacEntity.insertTimeStamp) {
//                t = btMacEntity.insertTimeStamp;
//                temp = btMacEntity;
//            }
//        }
//
//        removeMac(temp);
//        LogUtils.e("removeOldestMac");
//    }
//
//
//    public void addContact(String macAddr, ContactEntity contactEntity) {
//        BtMacEntity mac = getMac(macAddr);
//        LogUtils.e("addContact  macAddr=" + macAddr + "    mac=" + mac + "     contactEntity=" + contactEntity);
//        if (mac == null) return;
//        mac.contactEntities.add(contactEntity);
//        mac.contactEntities.applyChangesToDb();
//    }
//
//    public void addContacts(String macAddr, List<ContactEntity> contacts) {
//        BtMacEntity mac = getMac(macAddr);
//        LogUtils.e("addContacts  macAddr=" + macAddr + "    mac=" + mac + "      contacts=" + contacts);
//
//        if (mac == null || TextUtils.isEmpty(mac.macAddr)) return;
//        mac.contactEntities.reset();
//        mac.contactEntities.addAll(contacts);
//        mac.contactEntities.applyChangesToDb();
//    }
//
//    public void clearContact(String macAddr) {
//        BtMacEntity mac = getMac(macAddr);
//
//        if (mac == null) return;
//        mac.contactEntities.clear();
//        mac.contactEntities.applyChangesToDb();
////        addMac(mac);
//
//        LogUtils.e(" clearContact  macAddr=" + macAddr + "     mac=" + mac + "     mac.contactEntities=" + mac.contactEntities);
//        if (mac.contactEntities != null) {
//            LogUtils.e("clearContact mac.contactEntities.size=" + mac.contactEntities.size());
//        }
//
//    }
//
//
//    public List<ContactEntity> getContacts(String macAddr) {
//        BtMacEntity mac = getMac(macAddr);
//        LogUtils.e("getContacts  macAddr=" + macAddr + "     mac=" + mac);
//        if (mac != null) {
//            return mac.contactEntities;
//        }
//        return null;
//    }
//
//    public List<ContactEntity> getOftenUsedList(String macAddr) {
//
//        Box<ContactEntity> contactEntityBox = MyApp.getInstence().getBoxStore().boxFor(ContactEntity.class);
//        List<ContactEntity> contactEntityList = contactEntityBox.query().equal(ContactEntity_.addr, macAddr).filter(new QueryFilter<ContactEntity>() {
//            @Override
//            public boolean keep(ContactEntity entity) {
//                return entity.isOftenUsed;
//            }
//        }).build().find();
//
//        LogUtils.e("getOftenUsedList  contactEntityList=" + contactEntityList);
//        return contactEntityList;
//    }
//
//
//    public boolean isOftenUsedListFull(String macAddr) {
//
//        List<ContactEntity> oftenUsedList = getOftenUsedList(macAddr);
//        LogUtils.e("getOftenUsedList  oftenUsedList.size()=" + (oftenUsedList == null ? "oftenUsedList==null" : oftenUsedList.size()));
//        if (oftenUsedList != null && oftenUsedList.size() >= 10) {
//            return true;
//        }
//
//        return false;
//    }
//
//    public void updateContactList(String macAddr, List<ContactEntity> contactEntities) {
////        该方法有问题，慎用，目前不知道问题出在哪
//        BtMacEntity mac = getMac(macAddr);
//        LogUtils.e("updateContactList  macAddr=" + macAddr + "     mac=" + mac + "      contactEntities=" + (contactEntities == null ? "" : contactEntities.size()));
//        if (mac != null) {
////            mac.contactEntities.clear();
//            mac.contactEntities.addAll(contactEntities);
//            mac.contactEntities.applyChangesToDb();
//            addMac(mac);
//        }
//
//    }
//
//    public void updateContact(ContactEntity entity) {
//        LogUtils.e("before  updateContact  entity=" + entity);
//        long id = entity.id;
//        Box<ContactEntity> box = MyApp.getInstence().getBoxStore().boxFor(ContactEntity.class);
//        box.put(entity);
//        if (id != 0) {
//            LogUtils.e("after  updateContact  box.get(id)=" + box.get(id));
//        }
//
//    }
//
//}
