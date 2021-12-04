const express = require("express")
const app = express()
const port = 8080;

app.set("port", port)
app.use(express.json())

app.get("/", (req, res) => {
    console.log(req.query)
    return res.json({hello : "hello world"})
})

app.get("/:id", (req, res) => {
    console.log(req.params)
    return res.json({hello : "hello id"})
})

app.post("/", (req, res) => {
    console.log(req.body)
    return res.json({hello : "hello post"})
})

app.patch("/", (req, res) => {
    console.log(req.body)
    return res.json({hello : "hello patch"})
})

app.delete("/", (req, res) => {
    console.log(req.body)
    return res.json({hello : "hello delete"})
})

app.listen(port, ()=>console.log("서버 실행됨"))

