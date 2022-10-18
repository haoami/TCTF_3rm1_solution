package com.ctf.threermi;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",2000);
            UserInter hello = (UserInter) registry.lookup( "rmi://127.0.0.1:8080");
              System. out.println( hello.getGirlFriend());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
