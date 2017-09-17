# annotation-processor
Java 注解处理器示例, 利用注解处理器在编译期间获取泛型信息, 并生成类文件存储泛型信息。

执行命令 **mvn clean install**，安装注解处理器 api 到本地仓库。

执行命令 **mvn clean package**，进行编译。

然后根据关键字 AutoParseFieldInfo 查找，即可找到由注解处理器生成的类文件。
```
$ find . -name "AutoParseFieldInfo*"
./annotation-process-provider/target/classes/com/albon/arith/annotation/service/AutoParseFieldInfo.class
./annotation-process-provider/target/generated-sources/annotations/com/albon/arith/annotation/service/AutoParseFieldInfo.java
```
