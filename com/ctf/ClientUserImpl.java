/*    */ package com.ctf.threermi;
/*    */
/*    */ import java.rmi.RemoteException;

/*    */
/*    */ public class ClientUserImpl
/*    */   implements UserInter {
/*    */   public String sayHello(String name) throws RemoteException {
/*  8 */     return String.format("hello %s from client", new Object[] { name });
/*    */   }
/*    */
/*    */
/*    */   public Friend getGirlFriend() throws RemoteException {
/* 13 */     return null;
/*    */   }
/*    */ }


/* Location:              F:\ctf比赛\2022\tctf\3rm1\rmiclient\threermiclient\!\com\ctf\com.ctf.threermi\ClientUserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
