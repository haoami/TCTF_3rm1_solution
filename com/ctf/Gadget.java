/*    */ package com.ctf.threermi;
/*    */
/*    */

import sun.rmi.transport.TransportConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

/*    */
/*    */ public class Gadget implements Serializable {
/*    */   UserInter user;
/*    */
/*    */   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
/* 12 */     inputStream.defaultReadObject();
/*    */     try {
/* 14 */       Method method = findMethod(this.user.getGirlFriend().getClass(), this.mName);
/* 15 */       method.invoke(this.user.getGirlFriend(), new Object[0]);
/* 16 */     } catch (Exception e) {
/* 17 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   String mName;
/*    */   private Method findMethod(Class clazz, String methodName) throws NoSuchMethodException {
/* 22 */     Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
/* 23 */     method.setAccessible(true);
/* 24 */     return method;
/*    */   }
/*    */ }


/* Location:              F:\ctf比赛\2022\tctf\3rm1\rmiclient\threermiclient\!\com\ctf\com.ctf.threermi\Gadget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
