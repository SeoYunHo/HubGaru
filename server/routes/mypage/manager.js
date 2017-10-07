"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.getUserInfo = (id,callback) => {
    let response = {
        user: null
    };
    let stateCode

    conn.query('select * from account where id =? ', id, function (err, rows) {
        console.log(rows);
        if (err) stateCode=500;
        else if (rows.length == 1) {
            stateCode=200;
            response.user={
                part: rows[0].part,
                intro: rows[0].user_intro,
                picture: rows[0].picture_uri,
                name: rows[0].name,
                phone: rows[0].phone
            }
        }
        else stateCode=400;
        callback(stateCode,response);
    });
}

manager.getUserHub = (id) => {
    let response = {
        hub: null
    };

    let stateCode;
    conn.query('select * from hub where id=?', id, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 0) {
            stateCode=200;
            let hub = {
                hubId: rows[0].hub_id,
                garuId: rows[0],garu_id,
                name: rows[0].name,
                img: rows[0].img,
                file: rows[0].file_url
            }
            response.hub.push(hub);
        }
        callback(stateCode,response);
    });
}

manager.getUserGaru = (id) => {
    let response = {
        garu: null
    };
    let stateCode;

    conn.query('select * from garu where id=?', id, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 0) {
            stateCode=200;
            let garu = {
                garuId: rows[0].garu_id,
                leaderId: rows[0].leader_id,
                name: rows[0].name,
                intro: rows[0].intro,
                img: rows[0].img
            }
            response.garu.push(garu);
        }
        callback(stateCode,response);
    });
}  

module.exports = manager;
