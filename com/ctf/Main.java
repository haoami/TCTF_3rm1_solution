/*    */ package com.ctf.threermi;
/*    */
/*    */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;

/*    */
/*    */ public class Main
/*    */ {
/*    */   private static final int port = 8090;
/*    */
/*    */   public static void main(String[] args) throws Exception {
/* 18 */     System.out.println("server start");
/* 19 */     HttpServer server = HttpServer.create(new InetSocketAddress(8090), 0);
/* 20 */     server.createContext("/", new MyHandler());
/* 21 */     server.setExecutor(Executors.newCachedThreadPool());
/* 22 */     server.start();
/*    */   }
/*    */
/*    */   static class MyHandler implements HttpHandler {
/*    */     public void handle(HttpExchange t) throws IOException {
/* 27 */       String query = t.getRequestURI().getQuery();
/* 28 */       String response = "Welcome to 0ctf 2022";
/*    */       try {
/* 30 */         Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
/* 31 */         UserInter user = (UserInter)registry.lookup(query);
/* 32 */         user.sayHello("yxxx");
/* 33 */       } catch (Exception e) {
/* 34 */         response = "0ops, something wrong!";
/*    */       }
/* 36 */       t.sendResponseHeaders(200, response.length());
/* 37 */       OutputStream os = t.getResponseBody();
/* 38 */       os.write(response.getBytes());
/* 39 */       os.close();
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\ctf比赛\2022\tctf\3rm1\rmiclient\threermiclient\!\com\ctf\com.ctf.threermi\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
