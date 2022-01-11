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

app.listen(9090);