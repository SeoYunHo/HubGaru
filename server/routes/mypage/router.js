"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');

let random = require('../../support/random');

router.route('/user/info/:id').get(function(req,res){
    let id=req.params.id;

    manager.getUserInfo(id, function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.user) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/user/hub/:id').get(function(req,res){
    let id=req.params.id;

    manager.getUserHub(id, function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.hub) res.write(JSON.stringify(response));
        res.end();
    });
});

router.route('/user/garu/:id').get(function(req,res){
    let id=req.params.id;

    manager.getUserGaru(id, function(stateCode, response){
        res.writeHead(stateCode, {
                'Content-Type': 'application/json'
        });
        if(!!response.garu) res.write(JSON.stringify(response));
        res.end();
    });
});

module.exports = router;