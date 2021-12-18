const express = require("express")
const registerUser = require('./registerUser')
const money_issuance = require('./money_issuance')
const create_account = require('./create_account')
const balance_check = require('./balance_check')
const app = express()
const port = 8081;

app.use(express.json())

app.post("/money_issuance", async (req, res) => {
    let msg;

    console.log(req.body)

    if("amount" in req.body){
        msg = await money_issuance(req.body.amount)
    }

    return res.json({msg: [msg]})
})

app.post("/createaccount", async (req, res) => {
    let msg1, msg2;

    console.log(req.body)

    if("name" in req.body && "deposit" in req.body){
        msg1 = await registerUser(req.body.name)
        msg2 = await create_account(req.body.name, req.body.deposit)
    }

    return res.json({msg: [msg1, msg2]})
})

app.post("/balance_check", async (req, res) => {
    let msg;

    if("name" in req.body){
        msg = await balance_check(req.body.name)
    }

    return res.json({msg: [msg]})
})

app.listen(port, () => console.log(`http://127.0.0.1:${port} start`))

