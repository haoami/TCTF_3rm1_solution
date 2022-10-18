/*    */ package com.ctf.threermi;
/*    */
/*    */

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*    */
/*    */ public class MyInvocationHandler implements InvocationHandler, Serializable {
/*    */   FactoryInter object;
/*    */
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("kkkk");
//            System.out.println(method);
/* 11 */     return method.invoke(this.object.getObject(), args);
/*    */   }
/*    */ }


/* Location:              F:\ctf比赛\2022\tctf\3rm1\rmiclient\threermiclient\!\com\ctf\com.ctf.threermi\MyInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
