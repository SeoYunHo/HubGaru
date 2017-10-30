"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let fs = require('fs');
let random = require('../../support/random');

router.route('/hub/:garuId').post(function (req, res) {
    let hubId;
    let garuId = req.params.garuId;
    let file = req.body.file;
    let name = req.body.name;
    let img;
    let bool = true;
    if (!!req.body.img) img = req.body.img;
    else img = null;
    let date = req.body.date;

    while (bool) {
        hubId = random.randomInt();
        bool = manager.checkId(hubId);
    }

    manager.addHub(hubId, garuId, file, name, img, date, function (stateCode) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            res.end();
        }

    });

});

router.route('/hub').get(function (req, res) {

    manager.getHub(function (stateCode, response) {
        console.log(!req.headersSent);
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            console.log(res.statusCode);
            if (!!response.hub) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});

router.route('/garu/hub/:garuId').get(function (req, res) {
    let garuId = req.params.garuId;

    manager.getGaruHub(garuId, function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.hub) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});


router.route('/hub/detail/:garuid').get(function (req, res) {
    let id = req.params.garuId;

    manager.getHubDetail(garuId, function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.hub) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});

router.route('/hub/good/:hubId').post(function (req, res) {

    let hubId = req.params.hubId;
    manager.addGood(hubId, function (stateCode) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            res.end();
        }

    })
});

router.route('/hub/good/:hubId').get(function (req, res) {
    let hubId = req.params.hubId;
    manager.getGood(hubId, function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.good) res.write(JSON.stringify(response)).end();
            else res.end();
        }

    })
});

router.route('/hub/good/:hubId').delete(function (req, res) {
    let hubId = req.params.hubId;
    manager.deleteGood(hubId, function (stateCode) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            res.end();
        }

    })
});

router.route('/hub/comment/:hubId').post(function (req, res) {
    let hubId = req.params.hubId;
    let comment = req.body.comment;
    let id = req.body.id;
    let date = req.body.date

    manager.addComment(hubId, comment, id, date, function (stateCode) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            res.end();
        }

    });
});

router.route('/hub/comment/:hubId').get(function (req, res) {
    let hubId = req.params.hubId;

    manager.getComment(hubId, function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.comment) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});

router.route('/hub/rank/good').get(function (req, res) {
    manager.hubRankListGood(function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.hub) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});

router.route('/hub/rank/date').get(function (req, res) {
    manager.hubRankListDate(function (stateCode, response) {
        if (!res.headersSent) {
            res.writeHead(stateCode, {
                'Content-Type': 'application/json'
            });
            if (!!response.hub) {
                res.write(JSON.stringify(response));
                res.end();
            } else res.end();
        }

    });
});

router.route('/file/:file').get(function (req, res) {
    let file = req.params.file;

    fs.readFile(__dirname + '/../../public/' + file, function (err, data) {
        if (err) {
            res.writeHead(500, {
                "Content-Type": 'application/json'
            });
            res.end();
        }

        let fileKind;

        if (file.split('.')[file.split('.').length - 1] === 'jpeg') fileKind = 'image';
        else if (file.split('.')[file.split('.').length - 1] === 'mp3') fileKind = 'audio';
        res.writeHead(200, {
            "Content-Type": fileKind + '/' + file.split('.')[file.split('.').length - 1]
        });
        res.end(data);
    });
});

router.route('/file/:file').post(function (req, res) {
    let file = req.params.file;
    let fileName = req.params.fileName;
    let stateCode;
    
    console.log(file, fileName);
    fs.writeFile(fileName, file, function (err) {
        if (err) console.log(err);
        console.log('File saved.');
    })
})
module.exports = router;