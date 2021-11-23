# 사용법
## 설치
peer chaincode install -n mycc -v 1.0 -l java -p /opt/gopath/src/github.com/chaincode/chaincode_example02/MyCahinCode/
## 초기화
peer chaincode instantiate -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -l java -v 1.0 -c '{"Args":["init"]}' -P 'OR ('\''Org1MSP.peer'\'','\''Org2MSP.peer'\'')'
## 함수사용
### - 화페 발행
peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -c '{"Args":["money_issuance","500"]}'
### - 계좌 생성
peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -c '{"Args":["create_account","user1","100"]}'
### - 계좌 삭제
peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -c '{"Args":["delete_account","user1"]}'
### - 잔액 조회
peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -c '{"Args":["balance_check","admin"]}'
### - 송금
peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc2 -c '{"Args":["remit","admin","user1","100"]}'

# 참고자료
hyperledger@hyperledger:~/fabric-samples/first-network$ ./byfn.sh up -l java
Starting for channel 'mychannel' with CLI timeout of '10' seconds and CLI delay of '3' seconds
Continue? [Y/n] y
proceeding ...
LOCAL_VERSION=1.4.11
DOCKER_IMAGE_VERSION=1.4.12
=================== WARNING ===================
Local fabric binaries and docker images are
out of  sync. This may cause problems.
===============================================
/home/hyperledger/fabric-samples/first-network/../bin/cryptogen

##########################################################
##### Generate certificates using cryptogen tool #########
##########################################################
+ cryptogen generate --config=./crypto-config.yaml
  org1.example.com
  org2.example.com
+ res=0
+ set +x

Generate CCP files for Org1 and Org2
/home/hyperledger/fabric-samples/first-network/../bin/configtxgen
##########################################################
#########  Generating Orderer Genesis block ##############
##########################################################
CONSENSUS_TYPE=solo
+ '[' solo == solo ']'
+ configtxgen -profile TwoOrgsOrdererGenesis -channelID byfn-sys-channel -outputBlock ./channel-artifacts/genesis.block
  2021-11-23 01:23:49.411 UTC [common.tools.configtxgen] main -> INFO 001 Loading configuration
  2021-11-23 01:23:49.523 UTC [common.tools.configtxgen.localconfig] completeInitialization -> INFO 002 orderer type: solo
  2021-11-23 01:23:49.523 UTC [common.tools.configtxgen.localconfig] Load -> INFO 003 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:49.635 UTC [common.tools.configtxgen.localconfig] completeInitialization -> INFO 004 orderer type: solo
  2021-11-23 01:23:49.635 UTC [common.tools.configtxgen.localconfig] LoadTopLevel -> INFO 005 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:49.636 UTC [common.tools.configtxgen] doOutputBlock -> INFO 006 Generating genesis block
  2021-11-23 01:23:49.636 UTC [common.tools.configtxgen] doOutputBlock -> INFO 007 Writing genesis block
+ res=0
+ set +x

#################################################################
### Generating channel configuration transaction 'channel.tx' ###
#################################################################
+ configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID mychannel
  2021-11-23 01:23:49.667 UTC [common.tools.configtxgen] main -> INFO 001 Loading configuration
  2021-11-23 01:23:49.782 UTC [common.tools.configtxgen.localconfig] Load -> INFO 002 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:49.894 UTC [common.tools.configtxgen.localconfig] completeInitialization -> INFO 003 orderer type: solo
  2021-11-23 01:23:49.894 UTC [common.tools.configtxgen.localconfig] LoadTopLevel -> INFO 004 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:49.894 UTC [common.tools.configtxgen] doOutputChannelCreateTx -> INFO 005 Generating new channel configtx
  2021-11-23 01:23:49.898 UTC [common.tools.configtxgen] doOutputChannelCreateTx -> INFO 006 Writing new channel tx
+ res=0
+ set +x

#################################################################
#######    Generating anchor peer update for Org1MSP   ##########
#################################################################
+ configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/Org1MSPanchors.tx -channelID mychannel -asOrg Org1MSP
  2021-11-23 01:23:49.928 UTC [common.tools.configtxgen] main -> INFO 001 Loading configuration
  2021-11-23 01:23:50.040 UTC [common.tools.configtxgen.localconfig] Load -> INFO 002 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:50.151 UTC [common.tools.configtxgen.localconfig] completeInitialization -> INFO 003 orderer type: solo
  2021-11-23 01:23:50.151 UTC [common.tools.configtxgen.localconfig] LoadTopLevel -> INFO 004 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:50.151 UTC [common.tools.configtxgen] doOutputAnchorPeersUpdate -> INFO 005 Generating anchor peer update
  2021-11-23 01:23:50.151 UTC [common.tools.configtxgen] doOutputAnchorPeersUpdate -> INFO 006 Writing anchor peer update
+ res=0
+ set +x

#################################################################
#######    Generating anchor peer update for Org2MSP   ##########
#################################################################
+ configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/Org2MSPanchors.tx -channelID mychannel -asOrg Org2MSP
  2021-11-23 01:23:50.181 UTC [common.tools.configtxgen] main -> INFO 001 Loading configuration
  2021-11-23 01:23:50.294 UTC [common.tools.configtxgen.localconfig] Load -> INFO 002 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:50.406 UTC [common.tools.configtxgen.localconfig] completeInitialization -> INFO 003 orderer type: solo
  2021-11-23 01:23:50.406 UTC [common.tools.configtxgen.localconfig] LoadTopLevel -> INFO 004 Loaded configuration: /home/hyperledger/fabric-samples/first-network/configtx.yaml
  2021-11-23 01:23:50.406 UTC [common.tools.configtxgen] doOutputAnchorPeersUpdate -> INFO 005 Generating anchor peer update
  2021-11-23 01:23:50.407 UTC [common.tools.configtxgen] doOutputAnchorPeersUpdate -> INFO 006 Writing anchor peer update
+ res=0
+ set +x

Creating network "net_byfn" with the default driver
Creating volume "net_orderer.example.com" with default driver
Creating volume "net_peer0.org1.example.com" with default driver
Creating volume "net_peer1.org1.example.com" with default driver
Creating volume "net_peer0.org2.example.com" with default driver
Creating volume "net_peer1.org2.example.com" with default driver
Creating orderer.example.com    ... done
Creating peer0.org2.example.com ... done
Creating peer0.org1.example.com ... done
Creating peer1.org2.example.com ... done
Creating peer1.org1.example.com ... done
Creating cli                    ... done
CONTAINER ID   IMAGE                               COMMAND             CREATED                  STATUS                  PORTS                                           NAMES
8d0f1bdb302d   hyperledger/fabric-tools:latest     "/bin/bash"         Less than a second ago   Up Less than a second                                                   cli
3801895e524d   hyperledger/fabric-peer:latest      "peer node start"   1 second ago             Up Less than a second   0.0.0.0:10051->10051/tcp, :::10051->10051/tcp   peer1.org2.example.com
8eb36accfc43   hyperledger/fabric-peer:latest      "peer node start"   1 second ago             Up Less than a second   0.0.0.0:8051->8051/tcp, :::8051->8051/tcp       peer1.org1.example.com
b483edb1dd42   hyperledger/fabric-peer:latest      "peer node start"   1 second ago             Up Less than a second   0.0.0.0:7051->7051/tcp, :::7051->7051/tcp       peer0.org1.example.com
5514bcc472f9   hyperledger/fabric-orderer:latest   "orderer"           2 seconds ago            Up Less than a second   0.0.0.0:7050->7050/tcp, :::7050->7050/tcp       orderer.example.com
30db7b9f37b3   hyperledger/fabric-peer:latest      "peer node start"   2 seconds ago            Up Less than a second   0.0.0.0:9051->9051/tcp, :::9051->9051/tcp       peer0.org2.example.com

 ____    _____      _      ____    _____
/ ___|  |_   _|    / \    |  _ \  |_   _|
\___ \    | |     / _ \   | |_) |   | |
___) |   | |    / ___ \  |  _ <    | |
|____/    |_|   /_/   \_\ |_| \_\   |_|

Build your first network (BYFN) end-to-end test

Channel name : mychannel
Creating channel...
+ peer channel create -o orderer.example.com:7050 -c mychannel -f ./channel-artifacts/channel.tx --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
+ res=0
+ set +x
  2021-11-23 01:23:52.832 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:23:52.854 UTC [cli.common] readBlock -> INFO 002 Received block: 0
  ===================== Channel 'mychannel' created =====================

Having all peers join the channel...
+ peer channel join -b mychannel.block
+ res=0
+ set +x
  2021-11-23 01:23:52.908 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:23:52.931 UTC [channelCmd] executeJoin -> INFO 002 Successfully submitted proposal to join channel
  ===================== peer0.org1 joined channel 'mychannel' =====================

+ peer channel join -b mychannel.block
+ res=0
+ set +x
  2021-11-23 01:23:55.989 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:23:56.012 UTC [channelCmd] executeJoin -> INFO 002 Successfully submitted proposal to join channel
  ===================== peer1.org1 joined channel 'mychannel' =====================

+ peer channel join -b mychannel.block
+ res=0
+ set +x
  2021-11-23 01:23:59.071 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:23:59.095 UTC [channelCmd] executeJoin -> INFO 002 Successfully submitted proposal to join channel
  ===================== peer0.org2 joined channel 'mychannel' =====================

+ peer channel join -b mychannel.block
+ res=0
+ set +x
  2021-11-23 01:24:02.154 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:24:02.176 UTC [channelCmd] executeJoin -> INFO 002 Successfully submitted proposal to join channel
  ===================== peer1.org2 joined channel 'mychannel' =====================

Updating anchor peers for org1...
+ peer channel update -o orderer.example.com:7050 -c mychannel -f ./channel-artifacts/Org1MSPanchors.tx --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
+ res=0
+ set +x
  2021-11-23 01:24:05.231 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:24:05.241 UTC [channelCmd] update -> INFO 002 Successfully submitted channel update
  ===================== Anchor peers updated for org 'Org1MSP' on channel 'mychannel' =====================

Updating anchor peers for org2...
+ peer channel update -o orderer.example.com:7050 -c mychannel -f ./channel-artifacts/Org2MSPanchors.tx --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
+ res=0
+ set +x
  2021-11-23 01:24:08.301 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
  2021-11-23 01:24:08.310 UTC [channelCmd] update -> INFO 002 Successfully submitted channel update
  ===================== Anchor peers updated for org 'Org2MSP' on channel 'mychannel' =====================

+ peer chaincode install -n mycc -v 1.0 -l java -p /opt/gopath/src/github.com/chaincode/chaincode_example02/java/
  Installing chaincode on peer0.org1...
+ res=0
+ set +x
  2021-11-23 01:24:11.382 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 001 Using default escc
  2021-11-23 01:24:11.382 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 002 Using default vscc
  2021-11-23 01:24:11.387 UTC [chaincodeCmd] install -> INFO 003 Installed remotely response:<status:200 payload:"OK" >
  ===================== Chaincode is installed on peer0.org1 =====================

Install chaincode on peer0.org2...
+ peer chaincode install -n mycc -v 1.0 -l java -p /opt/gopath/src/github.com/chaincode/chaincode_example02/java/
+ res=0
+ set +x
  2021-11-23 01:24:11.445 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 001 Using default escc
  2021-11-23 01:24:11.445 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 002 Using default vscc
  2021-11-23 01:24:11.449 UTC [chaincodeCmd] install -> INFO 003 Installed remotely response:<status:200 payload:"OK" >
  ===================== Chaincode is installed on peer0.org2 =====================

Instantiating chaincode on peer0.org2...
+ peer chaincode instantiate -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc -l java -v 1.0 -c '{"Args":["init","a","100","b","200"]}' -P 'AND ('\''Org1MSP.peer'\'','\''Org2MSP.peer'\'')'
+ res=0
+ set +x
  2021-11-23 01:24:11.511 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 001 Using default escc
  2021-11-23 01:24:11.511 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 002 Using default vscc
  ===================== Chaincode is instantiated on peer0.org2 on channel 'mychannel' =====================

Querying chaincode on peer0.org1...
===================== Querying on peer0.org1 on channel 'mychannel'... =====================
+ peer chaincode query -C mychannel -n mycc -c '{"Args":["query","a"]}'
  Attempting to Query peer0.org1 ...3 secs
+ res=0
+ set +x

100
===================== Query successful on peer0.org1 on channel 'mychannel' =====================
Sending invoke transaction on peer0.org1 peer0.org2...
+ peer chaincode invoke -o orderer.example.com:7050 --tls true --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel -n mycc --peerAddresses peer0.org1.example.com:7051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt --peerAddresses peer0.org2.example.com:9051 --tlsRootCertFiles /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt -c '{"Args":["invoke","a","b","10"]}'
+ res=0
+ set +x
  2021-11-23 01:25:12.613 UTC [chaincodeCmd] chaincodeInvokeOrQuery -> INFO 001 Chaincode invoke successful. result: status:200 message:"invoke finished successfully" payload:"a: 90 b: 210"
  ===================== Invoke transaction successful on peer0.org1 peer0.org2 on channel 'mychannel' =====================

Installing chaincode on peer1.org2...
+ peer chaincode install -n mycc -v 1.0 -l java -p /opt/gopath/src/github.com/chaincode/chaincode_example02/java/
+ res=0
+ set +x
  2021-11-23 01:25:12.677 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 001 Using default escc
  2021-11-23 01:25:12.677 UTC [chaincodeCmd] checkChaincodeCmdParams -> INFO 002 Using default vscc
  2021-11-23 01:25:12.681 UTC [chaincodeCmd] install -> INFO 003 Installed remotely response:<status:200 payload:"OK" >
  ===================== Chaincode is installed on peer1.org2 =====================

Querying chaincode on peer1.org2...
===================== Querying on peer1.org2 on channel 'mychannel'... =====================
Attempting to Query peer1.org2 ...3 secs
+ peer chaincode query -C mychannel -n mycc -c '{"Args":["query","a"]}'
+ res=0
+ set +x

90
===================== Query successful on peer1.org2 on channel 'mychannel' =====================

========= All GOOD, BYFN execution completed ===========


 _____   _   _   ____
| ____| | \ | | |  _ \
|  _|   |  \| | | | | |
| |___  | |\  | | |_| |
|_____| |_| \_| |____/
