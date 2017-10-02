"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let random = require('../../support/random');

router.route('/hub/:garuId').post(function(req, res){
    let hubId;
    let garuId=req.params.garuId;
    let file=req.body.file;
    let name=req.body.name;
    let img;
    let bool=true;
    if(!!req.body.img) img=req.body.img;
    else img=null;

    while (bool) {
        hubId = random.randomInt();
        bool = manager.checkId(hubId);
    }

    manager.addHub(hubId, garuId, file, name, img, function(stateCode){
        res.writeHead(stateCode, {
            'Content-Type': 'application/json'
        });
        res.end();
    });

});

router.route('/hub').get(function(req,res){

    manager.getHub(function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/hub/detail/:garuid').get(function(req,res){
    let id=req.params.garuId;

    manager.getHubDetail(garuId, function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});
module.exports = router;