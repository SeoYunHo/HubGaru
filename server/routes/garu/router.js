"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let random = require('../../support/random');

//가루 생성
router.route('/garu/:id').post(function (req, res) {
    let garuId;
    let leaderId = req.params.id;
    let name = req.body.name;
    let intro = req.body.intro;
    let bool = true;
    let file = req.body.file;
    let img;
    if(!!req.body.img) img=req.body.img;
    else img=null;

    while (bool) {
        garuId = random.randomInt();
        bool = manager.checkId(garuId);
    }
    manager.addGaru(garuId, leaderId, name, intro, file, img, function (stateCode, response) {
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    });
});

//가루 받아오기
router.route('/garu').get(function (req, res) {
    manager.getGarues(function (stateCode, response) {

        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        if (!!response.garu) res.write(JSON.stringify(response));
        res.end();
    });
});


module.exports = router;