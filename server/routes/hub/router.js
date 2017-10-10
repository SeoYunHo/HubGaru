"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

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
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    });

});

router.route('/hub').get(function (req, res) {

    manager.getHub(function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/hub/detail/:garuid').get(function (req, res) {
    let id = req.params.garuId;

    manager.getHubDetail(garuId, function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/hub/good/:hubId').post(function (req, res) {
    let hubId = req.params.hubId;
    manager.addGood(hubId, function (stateCode) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    })
});

router.route('/hub/good/:hubId').get(function (req, res) {
    let hubId = req.params.hubId;
    manager.getGood(hubId, function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.good) res.write(JSON.stringify(response));
        res.end();
    })
});

router.route('/hub/good/:hubId').delete(function (req, res) {
    let hubId = req.params.hubId;
    manager.deleteGood(hubId, function (stateCode) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    })
});

router.route('/hub/comment/:hubId').post(function (req, res) {
    let hubId = req.params.hubId;
    let comment = req.body.comment;
    let id = req.body.id;
    let date = req.body.date

    manager.addComment(hubId, comment, id, date, function (stateCode) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    });
});

router.route('/hub/comment/:hubId').get(function (req, res) {
    let hubId = req.params.hubId;

    manager.getComment(hubId, function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.comment) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/hub/rank/good').get(function(req, res){
    manager.hubRankListGood(function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/hub/rank/date').get(function(req, res){
    manager.hubRankListDate(function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});
module.exports = router;