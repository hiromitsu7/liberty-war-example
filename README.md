# liberty-war-example

https://github.com/OpenLiberty/demo-devmode

## dev モード

- dev モードで起動。ファイル変更がすぐにコンパイル、デプロイされる
- Enter キー入力でテスト実行できる
- 標準出力がターミナルに出力される
- 参考: https://openliberty.io/docs/21.0.0.3/development-mode.html

```bash
mvn liberty:dev
```

## HTTPS

```bash
# キーストアの内容を表示する
keytool -v -list -keystore target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12 -storetype pkcs12
# 秘密鍵を取り出す(キーストアのパスワードはserver.xmlに記載しているもの)
openssl pkcs12 -in target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12 -nocerts -nodes -out privatekey
# 証明書署名要求(default.csr)を作成する
openssl req -new -key privatekey -subj "/CN=myhost.example.com/OU=defaultServer/O=ibm/ST=Tokyo/C=JP" -out default.csr
# SAN(Subject Alternative Name)を指定するためのファイルを作成する
echo subjectAltName=DNS:myhost.example.com > default-san.ext
# 証明書(default.crt)を作成する
openssl x509 -days 3650 -sha256 -req -signkey privatekey -in default.csr -out default.crt -extfile default-san.ext
# キーストアに証明書を登録する
keytool -importcert -alias default -noprompt -trustcacerts -file default.crt -keystore target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12 -storetype pkcs12
# キーストアの内容を表示する
keytool -v -list -keystore target/liberty/wlp/usr/servers/defaultServer/resources/security/key.p12 -storetype pkcs12
```

## Db2

```bash
docker run -itd --rm --name mydb --privileged=true -p 50000:50000 -e LICENSE=accept -e DB2INST1_PASSWORD=passw0rd -e DBNAME=testdb ibmcom/db2
docker exec -it mydb bash
su - db2inst1
db2sampl
```

## MQ

https://github.com/ibm-messaging/mq-container/blob/master/docs/developer-config.md#details-of-the-default-configuration

https://192.168.99.100:9443/ibmmq/console
admin
passw0rd

https://www.ibm.com/docs/ja/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.liberty.autogen.nd.doc/ae/rwlp_config_jmsTopic.html

```bash
docker run --rm --name mymq --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --publish 1414:1414 --publish 9443:9443 --detach ibmcom/mq
docker exec -it mymq bash
echo "DISPLAY QMSTATUS" | runmqsc QM1
echo "DISPLAY LISTENER(*) ALL" | runmqsc QM1
echo "DISPLAY CHANNEL(*) ALL" | runmqsc QM1

echo "DISPLAY QUEUE(*) ALL" | runmqsc QM1 # DEV.QUEUE.1, DEV.QUEUE.2, DEV.QUEUE.3
echo "DISPLAY TOPIC(*) ALL" | runmqsc QM1 # DEV.BASE.TOPIC

echo "DISPLAY SBSTATUS(*) ALL" | runmqsc QM1
echo "DISPLAY SUB(*) ALL" | runmqsc QM1
```
