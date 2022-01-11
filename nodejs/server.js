const fastify = require("fastify");
const app = fastify();

const _accountNumberMap = {
    "1": {
        "card": {
            "cardNumber": "1234-1234-1234-1234",
        },
        "balance": 3000.35,
        "status": {
            "code": "C01",
            "description": "Canceled"
        },
        "fullname": "Coffin Joe",
        "age": 99,
    },
    "2": {
        "card": {
            "cardNumber": "4567-4567-4567-4567",
        },
        "balance": 0.99,
        "status": {
            "code": "V99",
            "description": "Valid",
        },
        "fullname": "Bill Power",
        "age": 12,
    },    
};

app.addHook("onSend", async (req, res) => {
    res.header("Content-Type", "application/json");
});

app.get("/:accountNumber/card", (req, res) => {
    const _data = _accountNumberMap[req.params.accountNumber];
    if (_data && _data.card) {
        setTimeout(() => {
            res.send(JSON.stringify(_data.card));
        }, 100);
    }
});

app.get("/:accountNumber/status", (req, res) => {
    const _data = _accountNumberMap[req.params.accountNumber];
    if (_data && _data.status) {
        setTimeout(() => {
            res.send(JSON.stringify(_data.status));
        }, 100);
    }
});

app.get("/:accountNumber/name", (req, res) => {
    const _data = _accountNumberMap[req.params.accountNumber];
    if (_data && _data.fullname) {
        setTimeout(() => {
            res.send({name: _data.fullname});
        }, 100);
    }
});

app.get("/:accountNumber/age", (req, res) => {
    const _data = _accountNumberMap[req.params.accountNumber];
    if (_data && _data.age) {
        setTimeout(() => {
            res.send({age: _data.age});
        }, 100);
    }
});


app.listen(9090);