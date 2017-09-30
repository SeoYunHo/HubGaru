"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let random = require('../../support/random');

router.route('/hub').get(function(req,res){

    manager.getHub(id, function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.user) res.write(JSON.stringify(response));
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