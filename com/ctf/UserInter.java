package com.ctf.threermi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserInter extends Remote {
  String sayHello(String paramString) throws RemoteException;

  Friend getGirlFriend() throws RemoteException;
}


/* Location:              F:\ctf比赛\2022\tctf\3rm1\rmiclient\threermiclient\!\com\ctf\com.ctf.threermi\UserInter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
