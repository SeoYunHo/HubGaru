"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let random = require('../../support/random');

//가루 생성
router.route('/garu').post(function (req, res) {
    if (!req.session.user) {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.write(JSON.stringify({
            session: false
        }));
        res.end();
        return;
    }

    let garuId;
    let leaderId=req.session.user.user_Id; 
    let name = req.body.name;
    let intro = req.body.intro;
    let bool = true;

    while (bool) {
        garuId = random.randomString(10);
        bool = manager.checkId(garuId);
    }

    manager.addGaru(garuId, leaderId, name, intro, function (response) {
        if (response.success) {
            res.writeHead(201, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(400, {
                'Content-Type': 'application/json'
            });
        }
        res.end();
    });
});

//가루 받아오기
router.route('/garu').get(function (req, res) {
    manager.getGaru(function (response) {
        if (!!response.garu) {
            res.writeHead(200, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }
        res.write(JSON.stringify(response));
        res.end();
    });
});


module.exports = router;