const fastify = require("fastify");
const undici = require("undici");
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

const fnSendRequest = async (path) => {
    const { body } = await undici.request(`http://localhost:9090${path}`);
    for await (const data of body) {
        return data.toString("utf-8");
    }
};

const _fnProcessGetBalance = async (_cardData, _statusData, _accountBalance) => {
    return {
        cardNumber: JSON.parse(_cardData).cardNumber,
        balance: _accountBalance,
        status: JSON.parse(_statusData).code,
    }
};

const _fnSendResponse = (_data, res) => {
    res.send(_data);
};

app.addHook("onSend", async (req, res) => {
    res.header("Content-Type", "application/json");
});

app.get("/:accountNumber/balance", (req, res) => {
    const _accountNumber = req.params.accountNumber;
    const _data = _accountNumberMap[_accountNumber];
    if (_data) {
        const _accountBalance = _data.balance;
        Promise.all([fnSendRequest(`/${_accountNumber}/card`), fnSendRequest(`/${_accountNumber}/status`)])
            .then((_data) => _fnProcessGetBalance(_data[0], _data[1], _accountBalance))
            .then((_data) => _fnSendResponse(_data, res))
            .catch((error) => console.log(error));
    }
});

app.listen(8080, "0.0.0.0", (err, address) => {
    if (err){
        app.log.error(err);
        process.exit(1);
    }
    console.log("Started..." + address)
});
