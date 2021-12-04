const axios = require("axios");
const express = require("express")
const app = express()
const port = 8080;

const identityServer = "http://identityserver.com"
const financeServer = "http://financeserver.com"

app.use(express.json())
app.use(express.urlencoded({extended: false}))

/*
* ## 회원가입
http://rootapiserver.com/register
1. 회원 정보 hyperledger indy 네트워크에 저장
1-1. 고유 회원번호 발급
2. hyperledger fabric에 계좌 생성
파라메터 {
    userName : 'Alice',
    deposit : '0',
    userinfo: {
    ...
    },
    token : null,
    option: {
    ...
    },
}
* */
app.post("/register", async (req, res) => {
    // 회원 정보 hyperledger indy 네트워크에 저장
    /*const request_body = {
        username: req.body.username,
        deposit: req.body.deposit,
        userinfo: req.body.userinfo,
    };
    const headers =  {
        'Content-Type': 'application/json',
    };
    const result = await axios.post(identityServer + "/register",request_body,{
        headers:headers
    })

    console.log(result.data)*/

    //hyperledger fabric에 계좌 생성
    /*const request_body = {
        user: userid,
        deposit: req.body.deposit,
    };
    const headers =  {
        'Content-Type': 'application/json',
    };
    const result = await axios.post(financeServer + "/createaccount",request_body,{
        headers:headers
    })
    console.log(result.data)*/

    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 결제(= 송금)
http://rootapiserver.com/pay
1. hyperledger indy 네트워크의 고유 회원번호와 현재 토큰일치여부 판단
2. buyer가 seller에게 fee 원 송금
파라메터 {
    buyer : 'Alice',
    seller : 'Bob',
    fee : '10',
    token : null,
    option: {
    ...
    },
}
* */
app.post("/pay", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 송금
http://rootapiserver.com/remit
1. hyperledger indy 네트워크의 고유 회원번호와 현재 토큰일치여부 판단
2. user1가 user2에게 amount 원 송금
파라메터 {
    sender : 'Alice',
    receiver : 'Bob',
    amount : '10',
    token : null,
    option: {
    ...
    },
}
* */
app.post("/register", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 충전
http://rootapiserver.com/recharge
1. admin 계좌에 amount 만큼 화폐 발행
2. hyperledger indy 네트워크에서 고유 회원번호 획득
3. admin가 user에게 amount 원 송금
파라메터 {
    user : 'Alice',
    amount : '10',
    token : null,
    option: {
    ...
    },
}
* */
app.post("/register", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 환불
http://rootapiserver.com/refund
1. hyperledger indy 네트워크에서 고유 회원번호 획득
2. user 계좌에서 amount 만큼 화폐 삭제
파라메터 {
    user : 'Alice',
    amount : '10',
    token : null,
    option: {
    ...
    },
}
* */
app.post("/register", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 잔액조회
http://rootapiserver.com/balancecheck
1. hyperledger indy 네트워크에서 고유 회원번호 획득
2. user 계좌잔고 반환
파라메터 {
    user : 'Alice',
    token : null,
    option: {
    ...
    },
}
* */
app.post("/register", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

/*
* ## 회원탈퇴
http://rootapiserver.com/resign
1. hyperledger indy 네트워크에서 고유 회원번호 획득
2. user 계좌삭제
3. hyperledger indy 네트워크 에서 user 정보 삭제
파라메터 {
    user : 'Alice',
    token : null,
    option: {
    ...
    },
}
* */
app.delete("/register", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

app.listen(port, () => console.log("서버 실행됨"))

