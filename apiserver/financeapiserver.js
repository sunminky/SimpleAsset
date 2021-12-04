const express = require("express")
const app = express()
const port = 8081;

app.use(express.json())

/*
* ## 계좌 생성
http://financeapiserver.com/createaccount
파라메터 {
    username : 'Alice',
    deposit : '0',
    option: {
    ...
    },
}
* */
app.post("/createaccount", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello post"})
})

app.delete("/", (req, res) => {
    console.log(req.body)
    return res.json({hello: "hello delete"})
})

app.listen(port, () => console.log("서버 실행됨"))

